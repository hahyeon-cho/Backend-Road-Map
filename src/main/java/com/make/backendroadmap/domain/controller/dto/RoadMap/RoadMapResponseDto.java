package com.make.backendroadmap.domain.controller.dto.RoadMap;

import com.make.backendroadmap.domain.entity.Main;
import com.make.backendroadmap.domain.entity.Sub;
import java.util.List;
import lombok.Getter;

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
