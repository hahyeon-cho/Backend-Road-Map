package ncnk.make.backendroadmap.domain.restController.dto.member;

import lombok.Getter;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.SubCategory;

/**
 * 마이 페이지 (MyRoadMap)
 */
@Getter
public class MyRoadMapResponseDto {

    private String subDocsTitle; // 소분류 제목
    private Long likeCount; // 누적 좋아요 개수
    private String subDescription; // 한줄 설명
    private String mainDocsTitle; // 해당 소뷴류가 속한 대분류
    private String url; // URL

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