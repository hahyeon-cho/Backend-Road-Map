package ncnk.make.backendroadmap.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Sub;
import ncnk.make.backendroadmap.domain.repository.SubCategory.SubCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;

    public List<Sub> getSubCategoriesByMainCategory(int mainCategoryDocsOrder) {
        return Sub.getOrderedSubDocsInCategory(mainCategoryDocsOrder);
    }

}