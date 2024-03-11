package ncnk.make.backendroadmap.domain.repository.SubCategory;

import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    Optional<SubCategory> findSubCategoryBySubDocsId(Long subDocId);

    List<SubCategory> findSubCategoriesByMainCategory(MainCategory mainCategory);

    Optional<SubCategory> findSubCategoryBySubDocsTitle(String title);
}
