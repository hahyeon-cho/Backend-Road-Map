package ncnk.make.backendroadmap.domain.restController;


import io.micrometer.core.annotation.Timed;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Main;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.RecommendBook;
import ncnk.make.backendroadmap.domain.entity.Sub;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import ncnk.make.backendroadmap.domain.restController.dto.RoadMap.RecommendBookDto;
import ncnk.make.backendroadmap.domain.restController.dto.RoadMap.RoadMapResponseDto;
import ncnk.make.backendroadmap.domain.restController.dto.RoadMap.SubCategoryResponseDto;
import ncnk.make.backendroadmap.domain.service.MainCategoryService;
import ncnk.make.backendroadmap.domain.service.RecommendBookService;
import ncnk.make.backendroadmap.domain.service.SubCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 로드맵 RestController (json)
 */

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/roadmap")
public class RoadMapApiController {
    private final MainCategoryService mainCategoryService;
    private final SubCategoryService subCategoryService;
    private final RecommendBookService recommendBookService;

    //로드맵 페이지
    @Timed("RoadMapApiController.mainCategory")
    @GetMapping("/category")
    public RoadMap mainCategory() {
        List<RoadMapResponseDto> roadMapResponseDtos = new ArrayList<>();
        for (int i = 0; i < Main.getMaximumOrder(); i++) { //대분류를 순서대로 반복시켜서 dto에 담기
            Main mainDocsTitle = Main.getEnumByMainDocsOrder(i + 1); //대분류 정보
            List<Sub> subDocs = Sub.getOrderedSubDocsInCategory(mainDocsTitle.getMainDocsOrder()); //대분류에 포함된 소분류
            String url = mainDocsTitle.getUrl(); //대분류 URL

            RoadMapResponseDto roadMapResponseDto = RoadMapResponseDto.createRoadMapResponseDto(mainDocsTitle, subDocs,
                    url);
            roadMapResponseDtos.add(roadMapResponseDto);
        }

        return new RoadMap(roadMapResponseDtos);
    }

    //소분류 페이지
    @Timed("RoadMapApiController.subCategory")
    @GetMapping("/sub/{mainCategoryId}")
    public Detail subCategory(@PathVariable Long mainCategoryId) {
        MainCategory mainCategory = mainCategoryService.findMainCategoryById(mainCategoryId); //대분류 PK값을 통해 대분류 찾기
        List<Sub> subCategoriesByMainCategory = subCategoryService.getSubCategoriesByMainCategory(
                mainCategory); //대분류에 속한 소분류 데이터를 List로 얻기

        List<RecommendBookDto> recommendBookDtos = new ArrayList<>();
        List<RecommendBook> recommendBooks = recommendBookService.getRecommendBookList(mainCategory); //대분류에 속한 추천 책 얻기
        for (RecommendBook recommendBook : recommendBooks) {
            RecommendBookDto recommendBookDto = RecommendBookDto.createRecommendBookDto(recommendBook);
            recommendBookDtos.add(recommendBookDto); //추천 책을 dto로 set
        }

        List<SubCategoryResponseDto> categoryResponseDtos = new ArrayList<>();
        String mainDocsUrl = mainCategory.getMainDocsUrl(); //대분류 URL
        for (Sub sub : subCategoriesByMainCategory) {
            SubCategory subCategory = subCategoryService.findSubCategoryBySubDocsTitle(sub);
            categoryResponseDtos.add(
                    SubCategoryResponseDto.createSubCategoryResponseDto(
                            sub.getSubCategory(), sub.getSubDescription(),
                            subCategory.getLikeCount())); //소분류 데이터 중 필요한 Fit한 데이터를 dto로 Set
        }

        return new Detail(categoryResponseDtos, mainDocsUrl, recommendBookDtos);
    }


    @AllArgsConstructor
    @Getter
    static class RoadMap<T> {
        private T mainDocsTitle;
    }


    @AllArgsConstructor
    @Getter
    static class Detail<T> {
        private List<SubCategoryResponseDto> subCategory;
        private String url;
        private List<RecommendBookDto> recommendBookDtos;
    }
}