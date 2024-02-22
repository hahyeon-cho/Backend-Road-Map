package ncnk.make.backendroadmap.domain.repository;

import java.util.List;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.RecommendBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendBookRepository extends JpaRepository<RecommendBook, Long> {
    List<RecommendBook> findRecommendBooksByMainCategory(MainCategory mainCategory);
}
