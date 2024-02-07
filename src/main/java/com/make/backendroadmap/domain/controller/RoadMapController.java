package com.make.backendroadmap.domain.controller;


import com.make.backendroadmap.domain.controller.dto.RoadMap.RoadMapResponseDto;
import com.make.backendroadmap.domain.controller.dto.RoadMap.SubCategoryResponseDto;
import com.make.backendroadmap.domain.entity.Main;
import com.make.backendroadmap.domain.entity.MainCategory;
import com.make.backendroadmap.domain.entity.Sub;
import com.make.backendroadmap.domain.service.MainCategoryService;
import com.make.backendroadmap.domain.service.SubCategoryService;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/roadmap")
public class RoadMapController {
    private final MainCategoryService mainCategoryService;
    private final SubCategoryService subCategoryService;

    @GetMapping("/category")
    public RoadMap mainCategory() {

        List<RoadMapResponseDto> roadMapResponseDtos = new ArrayList<>();
        for (int i = 0; i < Main.getMaximumOrder(); i++) {
            Main mainDocsTitle = Main.getEnumByMainDocsOrder(i + 1);
            String url = mainDocsTitle.getUrl();
            List<Sub> subDocs = Sub.getOrderedSubDocsInCategory(mainDocsTitle.getMainDocsOrder());

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

        List<SubCategoryResponseDto> categoryResponseDtos = new ArrayList<>();

        log.info("RoadMap Detail Page");
        for (Sub sub : subCategoriesByMainCategory) {
            categoryResponseDtos.add(SubCategoryResponseDto.createSubCategoryResponseDto(sub, 0L,
                    mainCategory.getMainDocsUrl()));
        }

        return new Detail(categoryResponseDtos);
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
    }
}
