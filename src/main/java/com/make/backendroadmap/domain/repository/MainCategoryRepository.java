package com.make.backendroadmap.domain.repository;

import com.make.backendroadmap.domain.entity.MainCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {
    Optional<MainCategory> findMainCategoriesByMainDocsId(Long id);

    Optional<MainCategory> findMainCategoryByMainDocsTitle(String title);
}
