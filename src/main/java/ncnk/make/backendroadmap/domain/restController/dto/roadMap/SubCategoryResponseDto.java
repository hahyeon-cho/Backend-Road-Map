package ncnk.make.backendroadmap.domain.restController.dto.roadMap;

import lombok.Getter;
import lombok.Setter;

/**
 * 소분류 Dto
 */
@Getter
@Setter
public class SubCategoryResponseDto {

    private String subDocsTitle; // 소분류 제목
    private String subDescription; // 한줄 설명
    private Long likeCount; // 누적 좋아요 개수

    private SubCategoryResponseDto(String subDocsTitle, String subDescription, Long likeCount) {
        this.subDocsTitle = subDocsTitle;
        this.subDescription = subDescription;
        this.likeCount = likeCount;
    }

    public static SubCategoryResponseDto createSubCategoryResponseDto(String subDocsTitle,
        String subDescription, Long likeCount) {
        return new SubCategoryResponseDto(subDocsTitle, subDescription, likeCount);
    }
}