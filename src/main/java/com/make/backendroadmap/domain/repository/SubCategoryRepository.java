package com.make.backendroadmap.domain.repository;

import com.make.backendroadmap.domain.entity.MainCategory;
import com.make.backendroadmap.domain.entity.SubCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    Optional<SubCategory> findSubCategoryBySubDocsId(Long subDocId);

    List<SubCategory> findSubCategoriesByMainCategory(MainCategory mainCategory);

    Optional<SubCategory> findSubCategoryBySubDocsTitle(String title);
}
