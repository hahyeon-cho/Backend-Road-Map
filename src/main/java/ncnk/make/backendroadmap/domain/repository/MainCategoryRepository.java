package ncnk.make.backendroadmap.domain.repository;

import java.util.Optional;
import ncnk.make.backendroadmap.domain.entity.Main;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 대분류 테이블 Repository (Spring-data-JPA 이용)
 */
public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {
    Optional<MainCategory> findMainCategoriesByMainDocsId(Long id); //대분류 PK를 통해 대분류 정보 반환
    
    Optional<MainCategory> findMainCategoryByMainDocsTitle(Main main);  //대분류 정보를 통해 대분류 정보 반환
}
