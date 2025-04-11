package ncnk.make.backendroadmap.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.RecommendBook;
import ncnk.make.backendroadmap.domain.repository.RecommendBookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 책 추천 Service (BIZ 로직)
 */
@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecommendBookService {

    private final RecommendBookRepository recommendBookRepository;

    // 대분류 정보 이용해 추천 책 List 반환
    public List<RecommendBook> getRecommendBookList(MainCategory mainCategory) {
        return recommendBookRepository.findRecommendBooksByMainCategory(mainCategory);
    }
}
