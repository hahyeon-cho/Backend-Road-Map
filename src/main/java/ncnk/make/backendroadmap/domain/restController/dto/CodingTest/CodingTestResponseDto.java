package ncnk.make.backendroadmap.domain.restController.dto.CodingTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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
    private String problemInputOutput;
    private List<String> problemTopics;

    private CodingTestResponseDto(CodingTest codingTest) {
        this.problemTitle = codingTest.getProblemTitle();
        this.problemSlug = codingTest.getProblemSlug();
        this.problemLevel = codingTest.getProblemLevel();
        this.problemAccuracy = codingTest.getProblemAccuracy();
        this.problemContents = codingTest.getProblemContents();
        this.problemImages = codingTest.getProblemImages();
        this.problemInputOutput = selectInputOutput(codingTest);
        this.problemTopics = codingTest.getProblemTopics();
    }

    public static CodingTestResponseDto createCodingTestResponse(CodingTest codingTest) {
        return new CodingTestResponseDto(codingTest);
    }

    // 예상 입출력은 1개만 주어진다.
    public String selectInputOutput(CodingTest codingTest) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // LinkedHashMap 객체를 JSON 문자열로 변환
            String json = mapper.writeValueAsString(codingTest.getProblemInputOutput());
            // JSON 문자열을 CodingTestAnswer 객체의 리스트로 변환
            List<CodingTestAnswer> answers = mapper.readValue(json, new TypeReference<>() {
            });

            // 예상 입출력 중 첫 번째의 출력값을 반환
            if (!answers.isEmpty()) {
                String output = answers.get(0).getOutput();
                return output;
            } else {
                log.error("Empty input/output list");
                return "";
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new JsonParsingException();
        }
    }
}
