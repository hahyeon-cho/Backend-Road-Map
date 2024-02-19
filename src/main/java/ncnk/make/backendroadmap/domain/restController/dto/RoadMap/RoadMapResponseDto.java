package ncnk.make.backendroadmap.domain.restController.dto.RoadMap;

import java.util.List;
import lombok.Getter;
import ncnk.make.backendroadmap.domain.entity.Main;
import ncnk.make.backendroadmap.domain.entity.Sub;

@Getter
public class RoadMapResponseDto {
    private Main mainDocsTitle;
    private List<Sub> subDocs;
    private String url;

    private RoadMapResponseDto(Main mainDocsTitle, List<Sub> subDocs, String url) {
        this.mainDocsTitle = mainDocsTitle;
        this.subDocs = subDocs;
        this.url = url;
    }

    public static RoadMapResponseDto createRoadMapResponseDto(Main mainDocsTitle, List<Sub> subDocs, String url) {
        return new RoadMapResponseDto(mainDocsTitle, subDocs, url);
    }
}
