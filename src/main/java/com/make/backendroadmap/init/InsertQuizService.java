package com.make.backendroadmap.init;

import com.make.backendroadmap.domain.entity.Quiz;
import com.make.backendroadmap.domain.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsertQuizService {
    private final QuizRepository quizRepository;

    public void insertQuiz(Quiz quiz) {
        Quiz csQuiz = Quiz.createQuiz(quiz.getQuizContext(), quiz.getQuizAnswer(), quiz.getQuizExplain(),
                quiz.getMainCategory());
        quizRepository.save(csQuiz);
    }
}
