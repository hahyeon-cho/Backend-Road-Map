package ncnk.make.backendroadmap.domain.service;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.Quiz;
import ncnk.make.backendroadmap.domain.repository.Quiz.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 퀴즈 Service (BIZ 로직)
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class QuizService {
    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    // 퀴즈 채점
    public boolean gradeQuiz(List<Long> quizIds, List<String> userAnswers) {
        if (quizIds.size() != userAnswers.size()) {
            throw new IllegalArgumentException("제출 답변 개수 다름");
        }

        int correctCount = 0;

        for (int i = 0; i < quizIds.size(); i++) {
            Long quizId = quizIds.get(i);
            String userAnswer = userAnswers.get(i);

            Optional<Quiz> quizOptional = quizRepository.findById(quizId);
//            Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> throw new ResourceNotFoundException());
            if (quizOptional.isPresent()) {
                Quiz quiz = quizOptional.get();
                if (quiz.getQuizAnswer().equals(userAnswer)) {
                    correctCount++;
                }
            } else {
                log.warn("ID가 {}인 퀴즈를 찾을 수 없습니다.", quizId);
            }
        }

        return correctCount >= (quizIds.size() / 2);
    }

    //대분류 이용해 퀴즈 정보 조회
    public List<Quiz> getQuizzes(MainCategory mainCategory) {

        return quizRepository.findQuizzesByMainCategory(mainCategory);

    }


}
