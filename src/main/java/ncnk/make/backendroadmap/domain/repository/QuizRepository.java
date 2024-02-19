package ncnk.make.backendroadmap.domain.repository;

import java.util.List;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findQuizzesByMainCategory(MainCategory mainCategory);
}
