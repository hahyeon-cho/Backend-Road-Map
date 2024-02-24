package ncnk.make.backendroadmap.domain.restController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.DocsLike;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import ncnk.make.backendroadmap.domain.restController.dto.Like.DocsLikeResponseDto;
import ncnk.make.backendroadmap.domain.service.DocsLikeService;
import ncnk.make.backendroadmap.domain.service.SubCategoryService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/like")
public class DocsLikeApiController {
    private final SubCategoryService subCategoryService;
    private final DocsLikeService docsLikeService;

    @PostMapping("/insert/{id}")
    public Result toggleDocsLike(@PathVariable("id") Long id, HttpServletRequest request) {
        SubCategory subCategory = subCategoryService.findSubCategoryById(id);
        HttpSession session = request.getSession();
        Member member = (Member) session.getAttribute("member");

        DocsLike docsLike = docsLikeService.findDocsLikeByMemberAndSubCategory(member, subCategory);
        docsLikeService.toggleSubCategoryLike(docsLike);
        log.info("좋아요 로직 수행!");
        DocsLikeResponseDto docsLikeResponseDto = DocsLikeResponseDto.createDocsLikeResponseDto(member, subCategory);
        return new Result(docsLikeResponseDto);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private DocsLikeResponseDto docsLikeResponseDto;
    }
}
