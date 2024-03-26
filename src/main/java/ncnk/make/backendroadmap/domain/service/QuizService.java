package ncnk.make.backendroadmap.domain.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.Quiz;
import ncnk.make.backendroadmap.domain.exception.ResourceNotFoundException;
import ncnk.make.backendroadmap.domain.repository.Quiz.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 퀴즈 Service (BIZ 로직)
 */
@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;

    // 퀴즈 채점
    public boolean gradeQuiz(List<String> quizAnswers, List<String> userAnswers) {
        if (quizAnswers.size() != userAnswers.size()) {
            throw new IllegalArgumentException("제출 답변 개수 다릅니다.");
        }

        AtomicInteger correctCount = new AtomicInteger(0);

        for (int i = 0; i < quizAnswers.size(); i++) {
            String userAnswer = userAnswers.get(i);
            String quizAnswer = quizAnswers.get(i);

            if (quizAnswer.equals(userAnswer)) {
                correctCount.incrementAndGet();
            }
        }

        return correctCount.get() > (quizAnswers.size() / 2);
    }

    //대분류 이용해 퀴즈 정보 조회
    public List<Quiz> getQuizzes(MainCategory mainCategory) {
        return quizRepository.findQuizzesByMainCategory(mainCategory);
    }

    public Quiz getQuiz(String context, String quizAnswers) {
        return quizRepository.findQuizByQuizContextAndQuizAnswer(context, quizAnswers)
                .orElseThrow(() -> new ResourceNotFoundException());
    }
}
