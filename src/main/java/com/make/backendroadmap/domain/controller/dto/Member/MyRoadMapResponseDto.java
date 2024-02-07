package com.make.backendroadmap.domain.controller.dto.Member;

import com.make.backendroadmap.domain.entity.Sub;
import lombok.Getter;

@Getter
public class MyRoadMapResponseDto {
    private Sub subCategoryTitle;
    private String url;

    private MyRoadMapResponseDto(Sub subCategoryTitle, String url) {
        this.subCategoryTitle = subCategoryTitle;
        this.url = url;
    }

    public static MyRoadMapResponseDto createSubCategoryResponseDto(Sub subCategoryTitle, String url) {
        return new MyRoadMapResponseDto(subCategoryTitle, url);
    }
}
