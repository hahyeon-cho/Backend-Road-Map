package com.make.backendroadmap.domain.controller.dto.RoadMap;

import com.make.backendroadmap.domain.entity.Sub;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubCategoryResponseDto {
    private Sub subDocsTitle;
    private Long likeCount;

    private SubCategoryResponseDto(Sub subDocsTitle, Long likeCount) {
        this.subDocsTitle = subDocsTitle;
        this.likeCount = likeCount;
    }

    public static SubCategoryResponseDto createSubCategoryResponseDto(Sub subDocsTitle, Long likeCount) {
        return new SubCategoryResponseDto(subDocsTitle, likeCount);
    }
}
