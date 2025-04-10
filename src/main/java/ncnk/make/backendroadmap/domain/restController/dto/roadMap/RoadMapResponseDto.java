package ncnk.make.backendroadmap.domain.restController.dto.roadMap;

import java.util.List;
import lombok.Getter;
import ncnk.make.backendroadmap.domain.entity.Main;
import ncnk.make.backendroadmap.domain.entity.Sub;

/**
 * 로드맵 Dto
 */
@Getter
public class RoadMapResponseDto {

    private Main mainDocsTitle; // 대분류 데이터
    private List<Sub> subDocs; // 대분류에 속한 소분류 데이터
    private String url; // 대분류 URL

    private RoadMapResponseDto(Main mainDocsTitle, List<Sub> subDocs, String url) {
        this.mainDocsTitle = mainDocsTitle;
        this.subDocs = subDocs;
        this.url = url;
    }

    public static RoadMapResponseDto createRoadMapResponseDto(Main mainDocsTitle, List<Sub> subDocs, String url) {
        return new RoadMapResponseDto(mainDocsTitle, subDocs, url);
    }
}