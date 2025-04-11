package ncnk.make.backendroadmap.domain.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.aop.time.callback.TraceTemplate;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.exception.SessionNullPointException;
import ncnk.make.backendroadmap.domain.security.auth.LoginUser;
import ncnk.make.backendroadmap.domain.security.auth.dto.SessionUser;
import ncnk.make.backendroadmap.domain.service.MemberService;
import ncnk.make.backendroadmap.domain.service.PracticeCodeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * 웹 컴파일러 다운로드 Controller
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class FileDownloadController {

    private final PracticeCodeService practiceCodeService;
    private final MemberService memberService;
    private final TraceTemplate template;

    @Value("${file.dir}")
    private String fileDir;

    @PostMapping("/upload/{id}")
    public ResponseEntity<?> uploadFile(
        @PathVariable Long id,
        @LoginUser SessionUser user,
        @RequestParam("file") MultipartFile file,
        @RequestParam("fileName") String fileName,
        @RequestParam("extension") String extension
    ) {
        loginValidate(user);

        // 파일이 비어있는지 확인
        if (file.isEmpty()) {
            return new ResponseEntity<>("파일을 선택해주세요", HttpStatus.BAD_REQUEST);
        }

        Member member = memberService.findMemberById(id);
        String nickName = member.getNickName(); //회원의 닉네임 조회

        try {
            byte[] bytes = file.getBytes(); //저장하려는 파일 내용
            Path dirPath = Paths.get(fileDir, nickName);
            Path filePath = dirPath.resolve(fileName);

            // 파일이 이미 존재하는지 확인
            if (Files.exists(filePath)) {
                return new ResponseEntity<>("같은 이름의 파일이 이미 존재합니다.", HttpStatus.BAD_REQUEST);
            }

            Files.createDirectories(dirPath); //유저 닉네임으로 폴더 생성
            Files.write(filePath, bytes); //파일 저장

            return template.execute("PracticeCodeController.uploadFile()", () -> {
                practiceCodeService.save(fileName, String.valueOf(filePath), extension, member);
                return new ResponseEntity<>("저장되었습니다!", HttpStatus.OK); //TimeTrace Log와 함께 DB에 저장
            });
        } catch (IOException e) {
            log.error("Error uploading web-compiler: {}", e.getMessage());
        }
        return null;
    }

    private static void loginValidate(SessionUser user) {
        if (user == null) {
            throw new SessionNullPointException("[ERROR] SessionUser is null");
        }
    }
}
