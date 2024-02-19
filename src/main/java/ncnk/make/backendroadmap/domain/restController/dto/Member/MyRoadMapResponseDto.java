package ncnk.make.backendroadmap.domain.restController.dto.Member;

import lombok.Getter;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.SubCategory;

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
