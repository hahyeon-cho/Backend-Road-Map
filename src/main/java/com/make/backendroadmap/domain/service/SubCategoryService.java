package com.make.backendroadmap.domain.service;

import com.make.backendroadmap.domain.entity.MainCategory;
import com.make.backendroadmap.domain.entity.SubCategory;
import com.make.backendroadmap.domain.repository.SubCategory.SubCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;

    public List<SubCategory> getSubCategoriesByMainCategory(MainCategory mainCategory) {
        return subCategoryRepository.findSubCategoriesByMainCategory(mainCategory);
    }

}
