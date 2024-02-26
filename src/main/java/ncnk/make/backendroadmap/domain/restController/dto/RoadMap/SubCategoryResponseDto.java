package ncnk.make.backendroadmap.domain.restController.dto.RoadMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubCategoryResponseDto {
    private String subDocsTitle;
    private String subDescription;
    private Long likeCount;

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