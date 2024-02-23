package com.make.backendroadmap.domain.controller.dto.Member;

import com.make.backendroadmap.domain.entity.PracticeCode;
import lombok.Getter;

@Getter
public class MyPracticeResponseDto {
    private String fileName;
    private String language;

    private MyPracticeResponseDto(PracticeCode practiceCode) {
        this.fileName = practiceCode.getFileName();
        this.language = practiceCode.getLanguage();
    }

    public static MyPracticeResponseDto createMyPracticeResponseDto(PracticeCode practiceCode) {
        return new MyPracticeResponseDto(practiceCode);
    }
}
