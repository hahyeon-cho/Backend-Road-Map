package ncnk.make.backendroadmap.domain.controller.dto.docsLike;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocsLikeResponseDto {

    private Long DocsLikeId;

    private DocsLikeResponseDto(Long docsLikeId) {
        DocsLikeId = docsLikeId;
    }

    public static DocsLikeResponseDto createDocsLikeResponseDto(Long docsLikeId) {
        return new DocsLikeResponseDto(docsLikeId);
    }
}
