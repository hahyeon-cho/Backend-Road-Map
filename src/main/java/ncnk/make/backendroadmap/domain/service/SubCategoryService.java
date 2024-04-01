package ncnk.make.backendroadmap.domain.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.Sub;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import ncnk.make.backendroadmap.domain.exception.ResourceNotFoundException;
import ncnk.make.backendroadmap.domain.repository.SubCategory.SubCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 소분류 Service (BIZ 로직)
 */
@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;

    //대분류 정보를 이용해 대분류에 포함된 소분류 List로 반환
    public List<Sub> getSubCategoriesByMainCategory(MainCategory mainCategoryDocsOrder) {
        List<SubCategory> subCategories = subCategoryRepository.findSubCategoriesByMainCategory(
                mainCategoryDocsOrder);
        List<Sub> subs = new ArrayList<>();

        for (SubCategory subCategory : subCategories) {
            subs.add(subCategory.getSubDocsTitle());
        }
        return subs;
    }

    //소분류 PK 이용해 소분류 정보 조회
    public SubCategory findSubCategoryById(Long subCategoryId) {
        return subCategoryRepository.findSubCategoryBySubDocsId(subCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    public SubCategory findSubCategoryBySubDocsTitle(Sub subDocsTitle) {
        return subCategoryRepository.findSubCategoryBySubDocsTitle(subDocsTitle)
                .orElseThrow(() -> new ResourceNotFoundException());
    }
}