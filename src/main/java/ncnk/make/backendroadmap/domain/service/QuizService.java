package ncnk.make.backendroadmap.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.Quiz;
import ncnk.make.backendroadmap.domain.repository.QuizRepository;
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

    //대분류 이용해 퀴즈 정보 조회
    public List<Quiz> getQuizzes(MainCategory mainCategory) {
        return quizRepository.findQuizzesByMainCategory(mainCategory);
    }
}
