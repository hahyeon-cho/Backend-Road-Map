package ncnk.make.backendroadmap.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.common.BaseTimeEntity;

/**
 * 퀴즈 테이블
 */
import java.util.Objects;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Quiz extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "quiz_id")
    private Long quizId; //PK

    @Column(length = 1000)
    private String quizContext; //문제 내용
    private String quizAnswer; //정답
    @Column(length = 1000)
    private String quizExplain; //해설

    @ManyToOne
    @JoinColumn(name = "main_docs_id")
    private MainCategory mainCategory; //대분류 FK

    //생성자
    private Quiz(String quizContext, String quizAnswer, String quizExplain, MainCategory mainCategory) {
        this.quizContext = quizContext;
        this.quizAnswer = quizAnswer;
        this.quizExplain = quizExplain;
        this.mainCategory = mainCategory;
    }

    //정적 팩토리 메서드 방식을 적용한 생성자
    public static Quiz createQuiz(String quizContext, String quizAnswer, String quizExplain,
                                  MainCategory mainCategory) {
        return new Quiz(quizContext, quizAnswer, quizExplain, mainCategory);
    }
}
