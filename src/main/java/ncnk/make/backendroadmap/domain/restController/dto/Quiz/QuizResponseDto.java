package ncnk.make.backendroadmap.domain.restController.dto.Quiz;

import lombok.Getter;
import lombok.Setter;
import ncnk.make.backendroadmap.domain.entity.Quiz;

@Getter
@Setter
public class QuizResponseDto {
    private String quizContext;
    private String quizAnswer;
    private String quizExplain;

    private QuizResponseDto(Quiz quiz) {
        this.quizContext = quiz.getQuizContext();
        this.quizAnswer = quiz.getQuizAnswer();
        this.quizExplain = quiz.getQuizExplain();
    }

    public static QuizResponseDto createQuizResponseDto(Quiz quiz) {
        return new QuizResponseDto(quiz);
    }
}
