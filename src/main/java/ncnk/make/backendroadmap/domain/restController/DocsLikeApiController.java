package ncnk.make.backendroadmap.domain.restController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import ncnk.make.backendroadmap.domain.exception.SessionNullPointException;
import ncnk.make.backendroadmap.domain.restController.dto.Like.DocsLikeResponseDto;
import ncnk.make.backendroadmap.domain.security.auth.dto.SessionUser;
import ncnk.make.backendroadmap.domain.service.DocsLikeService;
import ncnk.make.backendroadmap.domain.service.MemberService;
import ncnk.make.backendroadmap.domain.service.SubCategoryService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/like")
public class DocsLikeApiController {
    private final SubCategoryService subCategoryService;
    private final DocsLikeService docsLikeService;
    private final MemberService memberService;

    @PostMapping("/{id}")
    public Result toggleDocsLike(@PathVariable("id") Long id,
                                 @SessionAttribute(name = "member", required = false) SessionUser sessionUser) {

        if (sessionUser == null) {
            throw new SessionNullPointException("[ERROR] SessionUser is null");
        }

        SubCategory subCategory = subCategoryService.findSubCategoryById(id);
        Member member = memberService.findMemberByEmail(sessionUser.getEmail());

        docsLikeService.toggleSubCategoryLike(member, subCategory);
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
