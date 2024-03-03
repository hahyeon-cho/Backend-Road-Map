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

/**
 * 코딩 테스트 테이블 - 리트코드 API 반환값에 따라 변동사항 있을 수 있음!
 */
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CodingTest extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "codingTest_id")
    private Long codingTestId; //PK
    private String problemName; //문제 이름
    private String problemDifficulty; //난이도
    private String problemContext; //문제 내용
    private String problemImage; //문제 그림
    private String problemInput; //예상 입력
    private String problemOutput; //예상 출력
    private Double problemAccuracy; //정답률

    @ManyToOne
    @JoinColumn(name = "main_docs_id")
    private MainCategory mainCategory; //대분류 FK

    //생성자
    private CodingTest(String problemName, String problemDifficulty, String problemContext, String problemImage,
                       String problemInput, String problemOutput, Double problemAccuracy, MainCategory mainCategory) {
        this.problemName = problemName;
        this.problemDifficulty = problemDifficulty;
        this.problemContext = problemContext;
        this.problemImage = problemImage;
        this.problemInput = problemInput;
        this.problemOutput = problemOutput;
        this.problemAccuracy = problemAccuracy;
        this.mainCategory = mainCategory;
    }

    //정적 팩토리 메서드 방식을 적용한 생성자
    public static CodingTest createCodingTest(String problemName, String problemDifficulty, String problemContext,
                                              String problemImage, String problemInput, String problemOutput,
                                              Double problemAccuracy, MainCategory mainCategory) {
        return new CodingTest(problemName, problemDifficulty, problemContext, problemImage, problemInput, problemOutput,
                problemAccuracy, mainCategory);
    }
}
