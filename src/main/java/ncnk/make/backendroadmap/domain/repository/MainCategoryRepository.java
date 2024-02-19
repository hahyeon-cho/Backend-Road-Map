package ncnk.make.backendroadmap.domain.repository;

import java.util.Optional;
import ncnk.make.backendroadmap.domain.entity.Main;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {
    Optional<MainCategory> findMainCategoriesByMainDocsId(Long id);

    Optional<MainCategory> findMainCategoryByMainDocsTitle(String title);

    Optional<MainCategory> findMainCategoryByMainDocsTitle(Main main);
}
