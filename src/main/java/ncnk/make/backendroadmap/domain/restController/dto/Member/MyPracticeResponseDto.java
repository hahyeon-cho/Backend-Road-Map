package ncnk.make.backendroadmap.domain.restController.dto.Member;

import lombok.Getter;
import ncnk.make.backendroadmap.domain.entity.PracticeCode;

@Getter
public class MyPracticeResponseDto {
    private String title;
    private String language;

    private MyPracticeResponseDto(PracticeCode practiceCode) {
        this.title = practiceCode.getTitle();
        this.language = practiceCode.getLanguage();
    }

    public static MyPracticeResponseDto createMyPracticeResponseDto(PracticeCode practiceCode) {
        return new MyPracticeResponseDto(practiceCode);
    }
}