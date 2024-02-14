package com.make.backendroadmap.domain.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
public class FileDownloadController {

    @Value("${file.dir}")
    private String fileDir;

    @PostMapping("/uploadFormAction")
    public void uploadFormAction(MultipartFile[] uploadFile, Model model) {

        String uploadFolder = "/Users/hayoung_p/Downloads/web-compiler";

        for (MultipartFile multipartFile : uploadFile) {
            log.info("==========");
            log.info("업로드 파일이름:  " + multipartFile.getOriginalFilename());


            File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
            try {
                multipartFile.transferTo(saveFile);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

}
