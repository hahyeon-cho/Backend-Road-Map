package ncnk.make.backendroadmap.domain.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.exception.SessionNullPointException;
import ncnk.make.backendroadmap.domain.security.auth.LoginUser;
import ncnk.make.backendroadmap.domain.security.auth.dto.SessionUser;
import ncnk.make.backendroadmap.domain.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@Slf4j
public class uploadProfilePicController {
    private final MemberService memberService;

    // 1. 파일 저장 경로 설정 : 실제 서비스되는 위치(프로젝트 외부에 저장)
    @Value("${img.path}")
    String uploadPath;

    @GetMapping("/fileUploadForm")
    public String fileUploadFormView(@LoginUser SessionUser user, Model model) {
        loginValidate(user);

        Member member = memberService.findMemberByEmail(user.getEmail()); //로그인한 사용자 정보 얻기
        model.addAttribute("member", member); // 회원 정보를 model에 담고
        return "myPage/updateForm";
    }

    // 파일 업로드
    @PostMapping("/fileUpload")
    public String fileUpLoadView(@RequestParam("uploadFile") MultipartFile file, Model model)
            throws IOException {
        // 2. 원본 파일 이름 알아오기
        String originalFileName = file.getOriginalFilename();

        // 3. 파일 이름이 중복되지 않도록 파일 이름 변경 : 서버에 저장할 이름
        // UUID 클래스 사용
        UUID uuid = UUID.randomUUID();
        String savedFileName = uuid.toString() + "_" + originalFileName;
        log.info("savedFileName: {}", savedFileName);
        // 4. 파일 생성
        File newFile = new File(uploadPath + savedFileName);

        // 5. 서버로 전송
        file.transferTo(newFile);

        // Model 설정 : 뷰 페이지에서 원본 파일 이름 출력
        model.addAttribute("originalFileName", originalFileName);

        return "redirect:/fileUploadForm";
    }

    private static void loginValidate(SessionUser user) {
        if (user == null) {
            throw new SessionNullPointException("[ERROR] SessionUser is null");
        }
    }
}

