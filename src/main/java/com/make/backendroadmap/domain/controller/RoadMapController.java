package com.make.backendroadmap.domain.controller;


import com.make.backendroadmap.domain.controller.dto.RoadMap.RoadMapResponseDto;
import com.make.backendroadmap.domain.controller.dto.RoadMap.SubCategoryResponseDto;
import com.make.backendroadmap.domain.entity.Main;
import com.make.backendroadmap.domain.entity.MainCategory;
import com.make.backendroadmap.domain.entity.SubCategory;
import com.make.backendroadmap.domain.service.MainCategoryService;
import com.make.backendroadmap.domain.service.SubCategoryService;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/roadmap")
public class RoadMapController {
    private final MainCategoryService mainCategoryService;
    private final SubCategoryService subCategoryService;

    @GetMapping("/category")
    public RoadMap mainCategory() {
        RoadMapResponseDto roadMapResponseDto = RoadMapResponseDto.createRoadMapResponseDto();
        return new RoadMap(roadMapResponseDto.getMainDocsTitle());
    }

    @GetMapping("/sub/{mainCategoryId}")
    public Detail subCategory(@PathVariable Long mainCategoryId) {
        MainCategory mainCategory = mainCategoryService.findMainCategoryById(mainCategoryId);
        List<SubCategory> subCategoriesByMainCategory = subCategoryService.getSubCategoriesByMainCategory(mainCategory);

        List<SubCategoryResponseDto> categoryResponseDtos = new ArrayList<>();
        if (!subCategoriesByMainCategory.isEmpty()) {
            log.info("RoadMap Detail Page");

            for (SubCategory subCategory : subCategoriesByMainCategory) {
                categoryResponseDtos.add(SubCategoryResponseDto.createSubCategoryResponseDto(
                        subCategory.getSubDocsTitle(), subCategory.getLikeCount(), subCategory.getSubDocsUrl()));
            }
        }

        return new Detail(categoryResponseDtos);
    }


    @AllArgsConstructor
    static class RoadMap<T> {
        private List<Main> mainDocsTitle;
    }


    @AllArgsConstructor
    static class Detail<T> {
        private List<SubCategoryResponseDto> subCategory;
    }
}
