package ncnk.make.backendroadmap.domain.repository.quiz;

import java.util.List;
import java.util.Optional;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 퀴즈 테이블 Repository (Spring-data-JPA 이용)
 */
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // 대분류에 따라 퀴즈 정보 List로 반환
    List<Quiz> findQuizzesByMainCategory(MainCategory mainCategory);

    Optional<Quiz> findQuizByQuizContextAndQuizAnswer(String context, String answer);
}
