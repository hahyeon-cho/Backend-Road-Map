package com.make.backendroadmap.domain.repository.SubCategory;

import com.make.backendroadmap.domain.entity.SubCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long>, SubCategoryCustomRepository {

    Optional<SubCategory> findSubCategoryBySubDocsId(Long subDocId);
    
    Optional<SubCategory> findSubCategoryBySubDocsTitle(String title);
}
