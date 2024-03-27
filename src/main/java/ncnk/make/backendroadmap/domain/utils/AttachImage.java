package ncnk.make.backendroadmap.domain.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AttachImage {
    private Long id;
    private String fileName;
    private String uploadPath;
    private String savedName;
    // 메소드 추가하자.


    public AttachImage(Long id, String fileName, String uploadPath, String savedName) {
        this.id = id;
        this.fileName = fileName;
        this.uploadPath = uploadPath;
        this.savedName = savedName;
    }
}
