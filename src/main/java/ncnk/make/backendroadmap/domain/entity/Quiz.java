package ncnk.make.backendroadmap.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.common.BaseTimeEntity;

import java.util.Objects;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Quiz extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "quiz_id")
    private Long quizId;

    @Column(length = 1000)
    private String quizContext;
    private String quizAnswer;
    @Column(length = 1000)
    private String quizExplain;

    @ManyToOne
    @JoinColumn(name = "main_docs_id")
    private MainCategory mainCategory;

    private Quiz(String quizContext, String quizAnswer, String quizExplain, MainCategory mainCategory) {
        this.quizContext = quizContext;
        this.quizAnswer = quizAnswer;
        this.quizExplain = quizExplain;
        this.mainCategory = mainCategory;
    }

    public static Quiz createQuiz(String quizContext, String quizAnswer, String quizExplain,
                                  MainCategory mainCategory) {
        return new Quiz(quizContext, quizAnswer, quizExplain, mainCategory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quiz quiz = (Quiz) o;
        return Objects.equals(quizContext, quiz.quizContext);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quizContext);
    }

}