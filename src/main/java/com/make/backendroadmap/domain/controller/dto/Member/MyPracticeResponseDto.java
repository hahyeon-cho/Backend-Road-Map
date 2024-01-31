package com.make.backendroadmap.domain.controller.dto.Member;

import lombok.Getter;

@Getter
public class MyPracticeResponseDto {
    private String title;
    private String language;

    private MyPracticeResponseDto(String title, String language) {
        this.title = title;
        this.language = language;
    }

    public static MyPracticeResponseDto createMyPracticeResponseDto(String title, String language) {
        return new MyPracticeResponseDto(title, language);
    }
}
