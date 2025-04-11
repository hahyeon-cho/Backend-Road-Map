package ncnk.make.backendroadmap.domain.repository;

import java.util.List;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.RecommendBook;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 책 추천 테이블 Repository (Spring-data-JPA 이용)
 */
public interface RecommendBookRepository extends JpaRepository<RecommendBook, Long> {

    // 대분류에 따른 추천 책 List 반환
    List<RecommendBook> findRecommendBooksByMainCategory(MainCategory mainCategory);
}
