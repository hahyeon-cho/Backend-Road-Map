package ncnk.make.backendroadmap.domain.restController.dto.Member;

import lombok.Getter;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.entity.Solved;

/**
 * 회원 업데이트 request
 */

@Getter
public class MyTestResponseDto {
    private Boolean problemSolved;
    private String problemTitle;
    private String problemLevel;
    private Double problemAccuracy;

    public MyTestResponseDto(Solved solved, CodingTest codingTest) {
        this.problemSolved = solved.getProblemSolved();
        this.problemTitle = codingTest.getProblemTitle();
        this.problemLevel = codingTest.getProblemLevel();
        this.problemAccuracy = codingTest.getProblemAccuracy();
    }

    public static MyTestResponseDto createTestResponseDto(Solved solved, CodingTest codingTest) {
        return new MyTestResponseDto(solved, codingTest);
    }
}
