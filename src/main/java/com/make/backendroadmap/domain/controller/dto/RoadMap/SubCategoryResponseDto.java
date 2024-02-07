package com.make.backendroadmap.domain.controller.dto.RoadMap;

import com.make.backendroadmap.domain.entity.Sub;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubCategoryResponseDto {
    private Sub subDocsTitle;
    private Long likeCount;
    private String subDocsUrl;

    private SubCategoryResponseDto(Sub subDocsTitle, Long likeCount, String subDocsUrl) {
        this.subDocsTitle = subDocsTitle;
        this.likeCount = likeCount;
        this.subDocsUrl = subDocsUrl;
    }

    public static SubCategoryResponseDto createSubCategoryResponseDto(Sub subDocsTitle, Long likeCount,
                                                                      String subDocsUrl) {
        return new SubCategoryResponseDto(subDocsTitle, likeCount, subDocsUrl);
    }

    private SubCategoryResponseDto(Sub subDocsTitle, Long likeCount) {
        this.subDocsTitle = subDocsTitle;
        this.likeCount = likeCount;
    }

    public static SubCategoryResponseDto createSubCategoryResponseDto(Sub subDocsTitle, Long likeCount) {
        return new SubCategoryResponseDto(subDocsTitle, likeCount);
    }
}
