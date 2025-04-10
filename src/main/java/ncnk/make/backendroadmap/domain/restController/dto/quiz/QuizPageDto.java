package ncnk.make.backendroadmap.domain.restController.dto.quiz;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import ncnk.make.backendroadmap.domain.entity.Quiz;

/**
 * 퀴즈 Dto
 */
@Getter
@Setter
public class QuizPageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String quizContext; // 퀴즈 내용
    private String quizAnswer; // 퀴즈 정답

    private QuizPageDto(Quiz quiz) {
        this.quizContext = quiz.getQuizContext();
        this.quizAnswer = quiz.getQuizAnswer();
    }

    public static QuizPageDto createQuizResponseDto(Quiz quiz) {
        return new QuizPageDto(quiz);
    }
}
