package ncnk.make.backendroadmap.domain.restController.dto.codingTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.exception.JsonParsingException;
import ncnk.make.backendroadmap.domain.utils.LeetCode.wrapper.CodingTestAnswer;

@Getter
@Slf4j
public class CodingTestResponseDto {

    private String problemTitle;
    private String problemSlug;
    private String problemLevel;
    private Double problemAccuracy;
    private String problemContents;
    private List<String> problemImages;
    private List<String> problemInput;
    private List<String> problemTopics;

    private CodingTestResponseDto(CodingTest codingTest) {
        this.problemTitle = codingTest.getProblemTitle();
        this.problemSlug = codingTest.getProblemSlug();
        this.problemLevel = codingTest.getProblemLevel();
        this.problemAccuracy = codingTest.getProblemAccuracy();
        this.problemContents = codingTest.getProblemContents();
        this.problemImages = codingTest.getProblemImages();
        this.problemInput = selectInputOutput(codingTest);
        this.problemTopics = codingTest.getProblemTopics();
    }

    public static CodingTestResponseDto createCodingTestResponse(CodingTest codingTest) {
        return new CodingTestResponseDto(codingTest);
    }

    // 예상 입출력은 1개만 주어진다.
    public List<String> selectInputOutput(CodingTest codingTest) {
        ObjectMapper mapper = new ObjectMapper();
        List<String> expectInputOutput = new CopyOnWriteArrayList<>();

        try {
            // LinkedHashMap 객체를 JSON 문자열로 변환
            String json = mapper.writeValueAsString(codingTest.getProblemInputOutput());
            // JSON 문자열을 CodingTestAnswer 객체의 리스트로 변환
            List<CodingTestAnswer> answers = mapper.readValue(json, new TypeReference<>() {
            });

            // 예상 입출력 중 첫 번째의 출력값을 반환
            if (!answers.isEmpty()) {
                String input = answers.get(0).getInput();
                String output = answers.get(0).getOutput();
                expectInputOutput.add(input);
                expectInputOutput.add(output);
                return expectInputOutput;
            } else {
                log.error("Empty input/output list");
                throw new JsonParsingException();
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JsonParsingException();
        }
    }
}
