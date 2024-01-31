package com.make.backendroadmap.domain.controller.dto.RoadMap;

import com.make.backendroadmap.domain.entity.Main;
import java.util.List;
import lombok.Getter;

@Getter
public class RoadMapResponseDto {
    private List<Main> mainDocsTitle;

    private RoadMapResponseDto() {
        this.mainDocsTitle = Main.getOrderedMainDocs();
    }


    public static RoadMapResponseDto createRoadMapResponseDto() {
        return new RoadMapResponseDto();
    }
}
