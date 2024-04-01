package ncnk.make.backendroadmap.domain.restController.dto.Member;

import java.time.format.DateTimeFormatter;
import lombok.Getter;
import ncnk.make.backendroadmap.domain.entity.PracticeCode;

/**
 * 마이 페이지 (MyPractice)
 */

@Getter
public class MyPracticeResponseDto {
    private String fileName; //파일 이름
    private String language; //언어
    private String modifiedDate; //최근 수정일

    private MyPracticeResponseDto(PracticeCode practiceCode) {
        this.fileName = practiceCode.getFileName();
        this.language = practiceCode.getLanguage();
        this.modifiedDate = practiceCode.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    public static MyPracticeResponseDto createMyPracticeResponseDto(PracticeCode practiceCode) {
        return new MyPracticeResponseDto(practiceCode);
    }
}