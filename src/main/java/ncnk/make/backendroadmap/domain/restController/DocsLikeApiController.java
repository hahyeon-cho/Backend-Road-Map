package ncnk.make.backendroadmap.domain.restController;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.aop.time.callback.TraceTemplate;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import ncnk.make.backendroadmap.domain.restController.dto.like.DocsLikeResponseDto;
import ncnk.make.backendroadmap.domain.security.auth.LoginUser;
import ncnk.make.backendroadmap.domain.security.auth.dto.SessionUser;
import ncnk.make.backendroadmap.domain.service.DocsLikeService;
import ncnk.make.backendroadmap.domain.service.MemberService;
import ncnk.make.backendroadmap.domain.service.SubCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 소분류 좋아요 RestController (json)
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class DocsLikeApiController {

    private final SubCategoryService subCategoryService;
    private final DocsLikeService docsLikeService;
    private final MemberService memberService;
    private final TraceTemplate template;

    // 토글 형식의 좋아요
    @PostMapping("/{id}")
    public ResponseEntity<Result> toggleDocsLike(@PathVariable("id") Long id, @LoginUser SessionUser user) {
        // 로그인 하지 않은 사용자의 경우 예외 발생
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Result("로그인이 필요합니다."));
        }

        SubCategory subCategory = subCategoryService.findSubCategoryById(id); // 소분류 PK값을 통해 소분류 찾기
        Member member = memberService.findMemberByEmail(user.getEmail()); // 로그인한 사용자 정보 얻기

        Result result = template.execute("DocsLikeApiController.toggleDocsLike()", () -> {
            docsLikeService.toggleSubCategoryLike(member, subCategory);
            DocsLikeResponseDto docsLikeResponseDto = DocsLikeResponseDto.createDocsLikeResponseDto(member,
                subCategory);
            return new Result(docsLikeResponseDto, "성공적으로 처리되었습니다."); // TimeTrace Log와 함께 dto 반환
        });
        return ResponseEntity.ok().body(new Result(result));
    }

    @Getter
    @AllArgsConstructor
    @JsonInclude(Include.NON_NULL)
    static class Result<T> {

        private T docsLikeResponseDto;
        private String message;

        public Result(T docsLikeResponseDto) {
            this.docsLikeResponseDto = docsLikeResponseDto;
        }

        public Result(String message) {
            this.message = message;
        }
    }
}
