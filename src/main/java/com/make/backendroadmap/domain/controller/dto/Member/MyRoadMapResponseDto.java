package com.make.backendroadmap.domain.controller.dto.Member;

import com.make.backendroadmap.domain.entity.MainCategory;
import com.make.backendroadmap.domain.entity.SubCategory;
import lombok.Getter;

@Getter
public class MyRoadMapResponseDto {
    private SubCategory subCategory;
    private String url;

    private MyRoadMapResponseDto(SubCategory subCategory, MainCategory mainCategory) {
        this.subCategory = subCategory;
        this.url = mainCategory.getMainDocsUrl();
    }

    public static MyRoadMapResponseDto createSubCategoryResponseDto(SubCategory subCategory,
                                                                    MainCategory mainCategory) {
        return new MyRoadMapResponseDto(subCategory, mainCategory);
    }
}
