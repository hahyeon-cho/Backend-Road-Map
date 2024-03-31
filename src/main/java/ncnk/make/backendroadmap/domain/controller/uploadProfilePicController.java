package ncnk.make.backendroadmap.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class uploadProfilePicController {
    @GetMapping("/fileUploadForm")
    public String fileUploadFormView() {
        return "myPage/updateForm";
    }

    // 파일 업로드
    @PostMapping("/fileUpload")
    public String fileUpLoadView(@RequestParam("uploadFile") MultipartFile file, Model model)
            throws IOException {

        System.out.println("fileUpload IN");
        // 1. 파일 저장 경로 설정 : 실제 서비스되는 위치(프로젝트 외부에 저장)
        String uploadPath = "/Users/hayoung_p/Desktop/2차 프로젝트/img/";햣

        // 2. 원본 파일 이름 알아오기
        String originalFileName = file.getOriginalFilename();

        // 3. 파일 이름이 중복되지 않도록 파일 이름 변경 : 서버에 저장할 이름
        // UUID 클래스 사용
        UUID uuid = UUID.randomUUID();
        String savedFileName = uuid.toString() + "_" + originalFileName;

        // 4. 파일 생성
        File newFile = new File(uploadPath + savedFileName);

        // 5. 서버로 전송
        file.transferTo(newFile);

        // Model 설정 : 뷰 페이지에서 원본 파일 이름 출력

        model.addAttribute("originalFileName", originalFileName);

        return "myPage/updateForm";
    }
}

