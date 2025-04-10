package ncnk.make.backendroadmap.domain.restController.dto.codingTest;

import lombok.Getter;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.entity.Solved;

@Getter
public class ProblemPageResponseDto {

    private String problemTitle;
    private String problemLevel;
    private Double problemAccuracy;
    private String isSolved;

    private ProblemPageResponseDto(CodingTest codingTest, Solved solved) {
        this.problemTitle = codingTest.getProblemTitle();
        this.problemLevel = codingTest.getProblemLevel();
        this.problemAccuracy = codingTest.getProblemAccuracy();
        this.isSolved = changeToString(solved);
    }

    public static ProblemPageResponseDto createCodingTestResponse(CodingTest codingTest, Solved solved) {
        return new ProblemPageResponseDto(codingTest, solved);
    }

    public String changeToString(Solved solved) {
        if (solved != null) {
            Boolean problemSolved = solved.getProblemSolved();
            return String.valueOf(problemSolved);
        } else {
            return "unsolved";
        }
    }
}
