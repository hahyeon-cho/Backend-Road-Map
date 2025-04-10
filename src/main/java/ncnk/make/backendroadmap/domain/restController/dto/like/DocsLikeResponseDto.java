package ncnk.make.backendroadmap.domain.restController.dto.like;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.Sub;
import ncnk.make.backendroadmap.domain.entity.SubCategory;

/**
 * 소분류 좋아요 Dto
 */
@Getter
@Slf4j
public class DocsLikeResponseDto {

    private String nickName; // 닉네임
    private String email; // 이메일
    private Sub subDocsTitle; // 소분류 제목
    private Long likeCount; // 누적 좋아요 개수

    private DocsLikeResponseDto(Member member, SubCategory subCategory) {
        this.nickName = member.getNickName();
        this.email = member.getEmail();
        this.subDocsTitle = subCategory.getSubDocsTitle();
        this.likeCount = subCategory.getLikeCount();

    }

    public static DocsLikeResponseDto createDocsLikeResponseDto(Member member, SubCategory subCategory) {
        return new DocsLikeResponseDto(member, subCategory);
    }
}
