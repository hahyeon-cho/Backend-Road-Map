package ncnk.make.backendroadmap.domain.restController.dto.CodingTest;

import lombok.Getter;
import ncnk.make.backendroadmap.domain.entity.CodingTest;

@Getter
public class ProblemPageResponseDto {
    private String problemTitle;
    private String problemLevel;
    private Double problemAccuracy;

    private ProblemPageResponseDto(CodingTest codingTest) {
        this.problemTitle = codingTest.getProblemTitle();
        this.problemLevel = codingTest.getProblemLevel();
        this.problemAccuracy = codingTest.getProblemAccuracy();
    }

    public static ProblemPageResponseDto createCodingTestResponse(CodingTest codingTest) {
        return new ProblemPageResponseDto(codingTest);
    }
}
