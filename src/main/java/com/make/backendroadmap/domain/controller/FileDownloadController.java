package com.make.backendroadmap.domain.controller;

import com.make.backendroadmap.domain.entity.Member;
import com.make.backendroadmap.domain.service.MemberService;
import com.make.backendroadmap.domain.service.PracticeCodeService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FileDownloadController {
    private final PracticeCodeService practiceCodeService;
    private final MemberService memberService;

    @Value("${file.dir}")
    private String fileDir;

    @PostMapping("/upload/{id}")
    public String uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file,
                             @RequestParam("fileName") String fileName, RedirectAttributes attributes) {
        // 파일이 비어있는지 확인
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "파일을 선택해주세요");
            return "redirect:/practice";
        }

        Member member = memberService.findMemberById(id);
        String nickName = member.getNickName();

        try {
            byte[] bytes = file.getBytes();
            Path dirPath = Paths.get(fileDir, nickName);
            Path filePath = dirPath.resolve(file.getOriginalFilename());

            Files.createDirectories(dirPath); //유저 닉네임으로 폴더 생성
            Files.write(filePath, bytes);

            log.info("Success To Upload Web-Compiler!!");

            practiceCodeService.save(fileName, String.valueOf(filePath), member);
        } catch (IOException e) {
            log.error("Error uploading web-compiler: {}", e.getMessage());
        }
        return "redirect:/practice";
    }
}
