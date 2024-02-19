package ncnk.make.backendroadmap.domain.service;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.Quiz;
import ncnk.make.backendroadmap.domain.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;

    public List<Quiz> getQuizzes(MainCategory mainCategory) {
        List<Quiz> quizzes = quizRepository.findQuizzesByMainCategory(mainCategory);

        Collections.shuffle(quizzes);

        if (quizzes.size() > 3) { //TODO: Constant
            return quizzes.subList(0, 3);
        } else {
            return quizzes;
        }
    }
}
