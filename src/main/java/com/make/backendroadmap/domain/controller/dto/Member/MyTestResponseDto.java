package com.make.backendroadmap.domain.controller.dto.Member;

import com.make.backendroadmap.domain.entity.CodingTest;
import com.make.backendroadmap.domain.entity.Solved;
import lombok.Getter;

@Getter
public class MyTestResponseDto {
    private Boolean problemSolved;
    private String problemName;
    private String problemLevel;
    private Double problemAccuracy;

    public MyTestResponseDto(Solved solved, CodingTest codingTest) {
        this.problemSolved = solved.getProblemSolved();
        this.problemName = codingTest.getProblemName();
        this.problemLevel = codingTest.getProblemLevel();
        this.problemAccuracy = codingTest.getProblemAccuracy();
    }

    public static MyTestResponseDto createTestResponseDto(Solved solved, CodingTest codingTest) {
        return new MyTestResponseDto(solved, codingTest);
    }
}
