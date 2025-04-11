package ncnk.make.backendroadmap.domain.controller;

import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.service.MainCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 소분류 페이지 Controller
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/roadmap/sub")
public class PageController {

    private final MainCategoryService mainCategoryService;

    // id값으로 url 변경
    @GetMapping("/{id}")
    public String internet(@PathVariable Long id, Model model) {
        MainCategory mainCategory = mainCategoryService.findMainCategoryById(id);
        if (mainCategory != null) {
            String mainDocsTitle = String.valueOf(mainCategory.getMainDocsTitle());
            model.addAttribute("mainDocsTitle", mainDocsTitle);
            return "/DataSet/" + mainDocsTitle + "/" + mainDocsTitle; // 반환
        } else {
            // MainCategory가 없는 경우 에러 페이지 반환
            return "error";
        }
    }
}