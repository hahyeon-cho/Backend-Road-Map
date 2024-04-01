package ncnk.make.backendroadmap.domain.entity;

import jakarta.persistence.*;
import jakarta.persistence.Convert;
import lombok.AccessLevel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.common.BaseTimeEntity;
import ncnk.make.backendroadmap.domain.entity.converter.StringListConverter;

import java.util.ArrayList;
import java.util.List;
import ncnk.make.backendroadmap.domain.entity.converter.StringListConverter;
import ncnk.make.backendroadmap.domain.utils.LeetCode.wrapper.CodingTestAnswer;

/**
 * 코딩 테스트 테이블 - 리트코드 API 반환값에 따라 변동사항 있을 수 있음!
 */
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CodingTest extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codingTest_id")
    private Long codingTestId;
    private String problemTitle;
    private String problemSlug;
    private String problemLevel;

    @Column(name = "problem_accuracy")
    private Double problemAccuracy;
    @Column(length = 10000)
    private String problemContents;
    @Convert(converter = StringListConverter.class)
    @Column(length = 500)
    private List<String> problemImages = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column(length = 500)
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

    public static CodingTest createCodingTest(String problemContents) {
        return new CodingTest(problemContents);
    }

    // 코딩 테스트 사용자 결과와 기대 출력 값 비교하는 메서드
    public static boolean evaluate(String userCodeResult, String codingTestAnswer) {
        if (!userCodeResult.equals(codingTestAnswer)) {
            return false;
        }
        return true;
    }
}