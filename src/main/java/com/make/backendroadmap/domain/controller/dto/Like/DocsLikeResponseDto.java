package com.make.backendroadmap.domain.controller.dto.Like;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DocsLikeResponseDto {
    @NotBlank
    private Long memberId;

    @NotBlank
    private Long subDocsId;

    private DocsLikeResponseDto(Long memberId, Long subDocsId) {
        this.memberId = memberId;
        this.subDocsId = subDocsId;
    }

    public static DocsLikeResponseDto createSubCategoryDto(Long memberId, Long subDocsId) {
        return new DocsLikeResponseDto(memberId, subDocsId);
    }
}
