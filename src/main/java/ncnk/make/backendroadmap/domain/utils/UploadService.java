package ncnk.make.backendroadmap.domain.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.exception.ProfileNotFoundException;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 반복되는 코드 리팩토링 필요
 */
@Component
@Slf4j
public class UploadService {

    @Value("${img.path}")
    private String userImage;

    public AttachImage upload(String path, MultipartFile uploadFile, String folderName) {
        if (imageCheck(uploadFile)) {
            return null;
        }

        /* 폴더 생성 */
        File uploadPath = new File(path, folderName);

        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        /* 이미저 정보 담는 객체 */
        AttachImage attachImage = new AttachImage();
        attachImage.setUploadPath(folderName);

        /* 파일 이름 */
        String uploadFileName = uploadFile.getOriginalFilename();
        attachImage.setFileName(uploadFileName);
        /* uuid 적용 파일 이름 */
        String uuid = UUID.randomUUID().toString();
        uploadFileName = uuid + "_" + uploadFileName;
        attachImage.setSavedName("s_" + uploadFileName);

        /* 파일 위치, 파일 이름을 합친 File 객체 */
        File saveFile = new File(uploadPath, uploadFileName);

        /* 파일 저장 */
        try {
            uploadFile.transferTo(saveFile);

            /* 방법 2 */
            // 라이브러리 사용
            File thumbnailFile = new File(uploadPath, attachImage.getSavedName());
            BufferedImage bo_image = ImageIO.read(saveFile);

            // 비율
            double ratio = 3;
            // 넓이 높이
            int width = (int) (bo_image.getWidth() / ratio);
            int height = (int) (bo_image.getHeight() / ratio);

            Thumbnails.of(saveFile)
                    .size(width, height)
                    .toFile(thumbnailFile);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (saveFile.exists()) {
                saveFile.delete();
            }
        }
        return attachImage;
    }

    private static boolean imageCheck(final MultipartFile uploadFile) {
        /* 이미지 파일 체크 */
        String type = uploadFile.getContentType();

        if (type == null || !type.startsWith("image")) {
            return true;
        }
        return false;
    }


    public byte[] getUserProfile(String folderName, String fileName) {
        File file = new File(userImage + "/" + folderName + "/" + fileName);

        byte[] result = null;
        try {
            HttpHeaders header = new HttpHeaders();

            header.add("Content-type", Files.probeContentType(file.toPath()));
            result = FileCopyUtils.copyToByteArray(file);

        } catch (IOException e) {
            throw new ProfileNotFoundException();
        }
        return result;
    }
}
