package com.make.backendroadmap.domain.controller.dto.Quiz;

import com.make.backendroadmap.domain.entity.Quiz;
import lombok.Getter;
import lombok.Setter;

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
