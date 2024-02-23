package ncnk.make.backendroadmap.domain.restController.dto.Member;

import java.time.format.DateTimeFormatter;
import lombok.Getter;
import ncnk.make.backendroadmap.domain.entity.PracticeCode;

@Getter
public class MyPracticeResponseDto {
    private String fileName;
    private String language;
    private String modifiedDate;

    private MyPracticeResponseDto(PracticeCode practiceCode) {
        this.fileName = practiceCode.getFileName();
        this.language = practiceCode.getLanguage();
        this.modifiedDate = practiceCode.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    public static MyPracticeResponseDto createMyPracticeResponseDto(PracticeCode practiceCode) {
        return new MyPracticeResponseDto(practiceCode);
    }
}