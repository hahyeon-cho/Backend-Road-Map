package com.make.backendroadmap.domain.repository;

import com.make.backendroadmap.domain.entity.MainCategory;
import com.make.backendroadmap.domain.entity.RecommendBook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendBookRepository extends JpaRepository<RecommendBook, Long> {
    List<RecommendBook> findRecommendBooksByMainCategory(MainCategory mainCategory);
}
