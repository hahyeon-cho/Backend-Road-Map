package ncnk.make.backendroadmap.domain.restController;


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

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/roadmap")
public class RoadMapController {
    private final MainCategoryService mainCategoryService;
    private final SubCategoryService subCategoryService;
    private final RecommendBookService recommendBookService;


    @GetMapping("/category")
    public RoadMap mainCategory() {

        List<RoadMapResponseDto> roadMapResponseDtos = new ArrayList<>();
        for (int i = 0; i < Main.getMaximumOrder(); i++) {
            Main mainDocsTitle = Main.getEnumByMainDocsOrder(i + 1);
            List<Sub> subDocs = Sub.getOrderedSubDocsInCategory(mainDocsTitle.getMainDocsOrder());
            String url = mainDocsTitle.getUrl();

            RoadMapResponseDto roadMapResponseDto = RoadMapResponseDto.createRoadMapResponseDto(mainDocsTitle, subDocs,
                    url);
            roadMapResponseDtos.add(roadMapResponseDto);
        }

        return new RoadMap(roadMapResponseDtos);
    }

    @GetMapping("/sub/{mainCategoryId}")
    public Detail subCategory(@PathVariable Long mainCategoryId) {
        MainCategory mainCategory = mainCategoryService.findMainCategoryById(mainCategoryId);
        List<Sub> subCategoriesByMainCategory = subCategoryService.getSubCategoriesByMainCategory(
                mainCategory.getMainDocsOrder());

        List<RecommendBookDto> recommendBookDtos = new ArrayList<>();
        List<RecommendBook> recommendBooks = recommendBookService.getRecommendBookList(mainCategory);
        for (RecommendBook recommendBook : recommendBooks) {
            RecommendBookDto recommendBookDto = RecommendBookDto.createRecommendBookDto(recommendBook);
            recommendBookDtos.add(recommendBookDto);
        }

        List<SubCategoryResponseDto> categoryResponseDtos = new ArrayList<>();
        String mainDocsUrl = mainCategory.getMainDocsUrl();
        log.info("RoadMap Detail Page");
        for (Sub sub : subCategoriesByMainCategory) {
            categoryResponseDtos.add(SubCategoryResponseDto.createSubCategoryResponseDto(sub, 0L));
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