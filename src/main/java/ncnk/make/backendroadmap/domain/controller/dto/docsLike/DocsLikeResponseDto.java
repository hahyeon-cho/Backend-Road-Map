package ncnk.make.backendroadmap.domain.controller.dto.docslike;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocsLikeResponseDto {

    private Long docsLikeId;

    private DocsLikeResponseDto(Long docsLikeId) {
        this.docsLikeId = docsLikeId;
    }

    public static DocsLikeResponseDto createDocsLikeResponseDto(Long docsLikeId) {
        return new DocsLikeResponseDto(docsLikeId);
    }
}
