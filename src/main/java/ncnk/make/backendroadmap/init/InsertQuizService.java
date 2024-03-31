package ncnk.make.backendroadmap.init;

import lombok.RequiredArgsConstructor;
import ncnk.make.backendroadmap.domain.entity.Quiz;
import ncnk.make.backendroadmap.domain.repository.Quiz.QuizRepository;
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