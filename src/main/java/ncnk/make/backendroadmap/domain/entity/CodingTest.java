package ncnk.make.backendroadmap.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.common.BaseTimeEntity;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CodingTest extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "codingTest_id")
    private Long codingTestId;
    private String problemName;
    private String problemLevel;
    private String problemContext;
    private String problemImage;
    private String problemInput;
    private String problemOutput;
    private Double problemAccuracy;
    private Boolean testOrQuiz;

    @ManyToOne
    @JoinColumn(name = "main_docs_id")
    private MainCategory mainCategory;

    private CodingTest(String problemName, String problemLevel, String problemContext,
                       String problemImage, String problemInput, String problemOutput, Double problemAccuracy,
                       Boolean testOrQuiz, MainCategory mainCategory) {
        this.problemName = problemName;
        this.problemLevel = problemLevel;
        this.problemContext = problemContext;
        this.problemImage = problemImage;
        this.problemInput = problemInput;
        this.problemOutput = problemOutput;
        this.problemAccuracy = problemAccuracy;
        this.testOrQuiz = testOrQuiz;
        this.mainCategory = mainCategory;
    }

    public static CodingTest createCodingTest(String problemName, String problemLevel, String problemContext,
                                              String problemImage, String problemInput, String problemOutput,
                                              Double problemAccuracy,
                                              Boolean testOrQuiz, MainCategory mainCategory) {
        return new CodingTest(problemName, problemLevel, problemContext, problemImage, problemInput, problemOutput,
                problemAccuracy, testOrQuiz, mainCategory);
    }

    private CodingTest(String problemContext, String problemInput, String problemOutput, MainCategory mainCategory) {
        this.problemContext = problemContext;
        this.problemInput = problemInput;
        this.problemOutput = problemOutput;
        this.testOrQuiz = false;
        this.mainCategory = mainCategory;
    }

    public static CodingTest createAlgorithmTest(String problemContext, String problemInput,
                                                 String problemOutput, MainCategory mainCategory) {
        return new CodingTest(problemContext, problemInput, problemOutput, mainCategory);
    }
}
