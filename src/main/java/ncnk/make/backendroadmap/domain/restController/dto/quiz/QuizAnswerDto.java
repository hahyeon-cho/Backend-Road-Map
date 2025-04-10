package ncnk.make.backendroadmap.domain.restController.dto.quiz;

import lombok.Getter;
import lombok.Setter;
import ncnk.make.backendroadmap.domain.entity.Quiz;

/**
 * 퀴즈 Dto
 */
@Getter
@Setter
public class QuizAnswerDto {

    private String quizContext; // 퀴즈 내용
    private String quizAnswer; // 퀴즈 정답
    private String quizExplain; // 퀴즈 설명

    private QuizAnswerDto(Quiz quiz) {
        this.quizContext = quiz.getQuizContext();
        this.quizAnswer = quiz.getQuizAnswer();
        this.quizExplain = quiz.getQuizExplain();
    }

    public static QuizAnswerDto createQuizResponseDto(Quiz quiz) {
        return new QuizAnswerDto(quiz);
    }
}
