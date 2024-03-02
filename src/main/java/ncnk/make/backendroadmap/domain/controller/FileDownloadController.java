package ncnk.make.backendroadmap.domain.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.aop.time.callback.TraceTemplate;
import ncnk.make.backendroadmap.domain.entity.Member;
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
    public ResponseEntity<?> uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file,
                                        @RequestParam("fileName") String fileName,
                                        @RequestParam("extension") String extension) {
        // 파일이 비어있는지 확인
        if (file.isEmpty()) {
            return new ResponseEntity<>("파일을 선택해주세요", HttpStatus.BAD_REQUEST);
        }

        Member member = memberService.findMemberById(id);
        String nickName = member.getNickName();

        try {
            byte[] bytes = file.getBytes();
            Path dirPath = Paths.get(fileDir, nickName);
            Path filePath = dirPath.resolve(fileName);

            // 파일이 이미 존재하는지 확인
            if (Files.exists(filePath)) {
                return new ResponseEntity<>("같은 이름의 파일이 이미 존재합니다.", HttpStatus.BAD_REQUEST);
            }

            Files.createDirectories(dirPath); //유저 닉네임으로 폴더 생성
            Files.write(filePath, bytes);

            return template.execute("PracticeCodeController.uploadFile()", () -> {
                practiceCodeService.save(fileName, String.valueOf(filePath), extension, member);
                return new ResponseEntity<>("저장되었습니다!", HttpStatus.OK);
            });
        } catch (IOException e) {
            log.error("Error uploading web-compiler: {}", e.getMessage());
        }
        return null;
    }
}
