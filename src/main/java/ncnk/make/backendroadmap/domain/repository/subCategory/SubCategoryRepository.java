package ncnk.make.backendroadmap.domain.repository.subCategory;

import java.util.List;
import java.util.Optional;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.Sub;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 소분류 테이블 Repository (Spring-data-JPA 이용)
 */
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long>, SubCategoryCustomRepository {

    // 소분류 PK값 이용해 소분류 찾아서 반환
    Optional<SubCategory> findSubCategoryBySubDocsId(Long subDocId);

    // 대분류 값을 이용해 소분류 데이터를 List로 반환
    List<SubCategory> findSubCategoriesByMainCategory(MainCategory mainCategory);

    // 소분류 제목을 이용해 소분류 찾아서 반환
    Optional<SubCategory> findSubCategoryBySubDocsTitle(Sub subDocsTitle);
}
