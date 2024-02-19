package ncnk.make.backendroadmap.domain.repository.SubCategory;

import java.util.Optional;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long>, SubCategoryCustomRepository {

    Optional<SubCategory> findSubCategoryBySubDocsId(Long subDocId);

    Optional<SubCategory> findSubCategoryBySubDocsTitle(String title);
}
