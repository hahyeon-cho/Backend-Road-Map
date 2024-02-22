package ncnk.make.backendroadmap.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.RecommendBook;
import ncnk.make.backendroadmap.domain.repository.RecommendBookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class RecommendBookService {
    private final RecommendBookRepository recommendBookRepository;

    public List<RecommendBook> getRecommendBookList(MainCategory mainCategory) {
        return recommendBookRepository.findRecommendBooksByMainCategory(mainCategory);
    }
}
