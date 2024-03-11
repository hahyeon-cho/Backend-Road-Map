package ncnk.make.backendroadmap.domain.restController.dto.RoadMap;

import lombok.Getter;
import lombok.Setter;
import ncnk.make.backendroadmap.domain.entity.Sub;

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