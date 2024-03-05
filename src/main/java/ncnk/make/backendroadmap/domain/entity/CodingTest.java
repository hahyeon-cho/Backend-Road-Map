package ncnk.make.backendroadmap.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.common.BaseTimeEntity;
import ncnk.make.backendroadmap.domain.entity.converter.StringListConverter;
import ncnk.make.backendroadmap.domain.utils.wrapper.CodingTestAnswer;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CodingTest extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "codingTest_id")
    private Long codingTestId;
    private String problemTitle;
    private String problemSlug;
    private String problemLevel;
    private Double problemAccuracy;
    @Column(length = 10000)
    private String problemContents;
    @Convert(converter = StringListConverter.class)
    private List<String> problemImages = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    private List<CodingTestAnswer> problemInputOutput;
    @Convert(converter = StringListConverter.class)
    private List<String> problemTopics = new ArrayList<>();

    private CodingTest(String problemTitle, String problemSlug, String problemLevel, Double problemAccuracy,
                       String problemContents, List<String> problemImages, List<CodingTestAnswer> problemInputOutput,
                       List<String> problemTopics) {
        this.problemTitle = problemTitle;
        this.problemSlug = problemSlug;
        this.problemLevel = problemLevel;
        this.problemAccuracy = problemAccuracy;
        this.problemContents = problemContents;
        this.problemImages = problemImages;
        this.problemInputOutput = problemInputOutput;
        this.problemTopics = problemTopics;
    }

    public static CodingTest createCodingTest(String problemTitle, String problemSlug,
                                              String problemLevel, Double problemAccuracy,
                                              String problemContents, List<String> problemImages,
                                              List<CodingTestAnswer> problemInputOutput, List<String> problemTopics) {
        return new CodingTest(problemTitle, problemSlug, problemLevel, problemAccuracy, problemContents, problemImages,
                problemInputOutput, problemTopics);
    }

    private CodingTest(String problemContents) {
        this.problemContents = problemContents;
    }

    public static CodingTest createAlgorithmTest(String problemContents) {
        return new CodingTest(problemContents);
    }
}
