package ncnk.make.backendroadmap.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.common.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

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
    @ElementCollection
    private List<String> problemImages = new ArrayList<>();
    //    private List<String> problemInputOutput;
    @ElementCollection
    private List<String> problemTopics = new ArrayList<>();
    //    private Boolean testOrQuiz;


    private CodingTest(String problemTitle, String problemSlug, String problemLevel, Double problemAccuracy,
                       String problemContents,
                       List<String> problemImages,
                       List<String> problemTopics) {
        this.problemTitle = problemTitle;
        this.problemSlug = problemSlug;
        this.problemLevel = problemLevel;
        this.problemAccuracy = problemAccuracy;
        this.problemContents = problemContents;
        this.problemImages = problemImages;
        this.problemTopics = problemTopics;
    }

    public static CodingTest createCodingTest(String problemTitle, String problemSlug,
                                              String problemLevel, Double problemAccuracy,
                                              String problemContents,
                                              List<String> problemImages,
                                              List<String> problemTopics) {
        return new CodingTest(problemTitle, problemSlug, problemLevel, problemAccuracy, problemContents, problemImages, problemTopics);
    }

    private CodingTest(String problemContents) {
        this.problemContents = problemContents;
    }

    public static CodingTest createAlgorithmTest(String problemContents) {
        return new CodingTest(problemContents);
    }
}
