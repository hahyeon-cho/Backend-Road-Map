package ncnk.make.backendroadmap.domain.restController.dto.Quiz;

import java.util.List;
import lombok.Getter;
import ncnk.make.backendroadmap.domain.entity.CodingTest;

@Getter
public class AlgorithmResponseDto {
    private String problemTitle; //문제 제목
    private String problemLevel; //문제 난이도
    private Double problemAccuracy; //맞춘 사람 퍼센트
    private String problemContents; //문제 내용
    //    private List<String> problemImages = new ArrayList<>(); //contetns에 포함되어 있을 듯.. 없으면 추가해야함!
    private List<String> problemTopics;

    private AlgorithmResponseDto(CodingTest codingTest) {
        this.problemTitle = codingTest.getProblemTitle();
        this.problemLevel = codingTest.getProblemLevel();
        this.problemAccuracy = codingTest.getProblemAccuracy();
        this.problemContents = codingTest.getProblemContents();
        this.problemTopics = codingTest.getProblemTopics();
    }

    public static AlgorithmResponseDto createAlgorithmResponseDto(CodingTest codingTest) {
        return new AlgorithmResponseDto(codingTest);
    }
}
