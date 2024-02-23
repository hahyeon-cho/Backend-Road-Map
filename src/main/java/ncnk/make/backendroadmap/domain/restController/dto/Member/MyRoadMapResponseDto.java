package ncnk.make.backendroadmap.domain.restController.dto.Member;

import lombok.Getter;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.SubCategory;

@Getter
public class MyRoadMapResponseDto {
    private String subDocsTitle;
    private Long likeCount;
    private String subDescription;
    private String mainDocsTitle;
    private String url;

    public MyRoadMapResponseDto(SubCategory subCategory, MainCategory mainCategory) {
        this.subDocsTitle = subCategory.getSubDocsTitle().getSubCategory();
        this.likeCount = subCategory.getLikeCount();
        this.subDescription = subCategory.getSubDescription();
        this.mainDocsTitle = mainCategory.getMainDocsTitle().getMainCategory();
        this.url = mainCategory.getMainDocsUrl();
    }

    public static MyRoadMapResponseDto createSubCategoryResponseDto(SubCategory subCategory,
                                                                    MainCategory mainCategory) {
        return new MyRoadMapResponseDto(subCategory, mainCategory);
    }
}