package com.make.backendroadmap.domain.controller.dto.Member;

import com.make.backendroadmap.domain.entity.Sub;
import lombok.Getter;

@Getter
public class MyRoadMapResponseDto {
    private Sub subCategoryTitle;
    private String subDocsUrl;

    private MyRoadMapResponseDto(Sub subCategoryTitle, String subDocsUrl) {
        this.subCategoryTitle = subCategoryTitle;
        this.subDocsUrl = subDocsUrl;
    }

    public static MyRoadMapResponseDto createSubCategoryResponseDto(Sub subCategoryTitle, String subDocsUrl) {
        return new MyRoadMapResponseDto(subCategoryTitle, subDocsUrl);
    }
}
