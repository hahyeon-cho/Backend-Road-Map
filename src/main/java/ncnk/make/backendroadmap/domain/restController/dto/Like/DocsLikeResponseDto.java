package ncnk.make.backendroadmap.domain.restController.dto.Like;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.Sub;
import ncnk.make.backendroadmap.domain.entity.SubCategory;

@Getter
@Slf4j
public class DocsLikeResponseDto {
    private String nickName;
    private String email;
    private Sub subDocsTitle;
    private Long likeCount;

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
