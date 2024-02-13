package com.make.backendroadmap.domain.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
public class FileDownloadController {

    @Value("${file.dir}")
    private String fileDir;

    @PostMapping("/generate/file")
    public ResponseEntity<String> generateFile(@RequestBody Map<String, String> body) throws Exception {
        String sourceCode = body.get("sourceCode");
        String fileName = body.get("fileName");

        // sourceCode를 이용하여 파일 생성 로직...

        // 생성된 파일의 위치
        Path path = Paths.get(fileDir, fileName);

        // 생성된 파일의 다운로드 URL. 실제 상황에 맞게 변경해야 합니다.
        String downloadUrl = "http://localhost:8080/download/" + fileName;

        return ResponseEntity.ok(downloadUrl);
    }
}
