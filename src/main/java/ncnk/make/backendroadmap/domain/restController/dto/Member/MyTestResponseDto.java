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
    private String problemName;
    private Double problemAccuracy;
    private String problemDifficulty;

    public MyTestResponseDto(Solved solved, CodingTest codingTest) {
        this.problemSolved = solved.getProblemSolved();
//        this.problemName = codingTest.getProblemName();
        this.problemLevel = codingTest.getProblemLevel();
        this.problemAccuracy = codingTest.getProblemAccuracy();
        this.problemDifficulty = codingTest.getProblemDifficulty();
    }

    public static MyTestResponseDto createTestResponseDto(Solved solved, CodingTest codingTest) {
        return new MyTestResponseDto(solved, codingTest);
    }
}
