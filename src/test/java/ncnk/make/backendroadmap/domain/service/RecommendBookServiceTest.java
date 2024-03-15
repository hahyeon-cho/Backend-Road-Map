package ncnk.make.backendroadmap.domain.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import ncnk.make.backendroadmap.domain.entity.Main;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.RecommendBook;
import ncnk.make.backendroadmap.domain.repository.RecommendBookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class RecommendBookServiceTest {

    @Autowired
    RecommendBookService recommendBookService;

    @MockBean // Spring Context에 등록된 Bean을 Mocking합니다.
    RecommendBookRepository recommendBookRepository;

    @DisplayName("도서 추천 리스트 테스트")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @CsvSource({"INTERNET, http://localhost:8080/roadmap/sub/1", "BASIC_FE, http://localhost:8080/roadmap/sub/2",
            "OS, http://localhost:8080/roadmap/sub/3", "LANGUAGE, http://localhost:8080/roadmap/sub/4",
            "ALGORITHM, http://localhost:8080/roadmap/sub/5"})
    void getRecommendBookListTest(Main main, String url) {
        //given
        MainCategory mainCategory = MainCategory.createMainCategory(main, url);

        //when
        List<RecommendBook> recommendBookList = recommendBookService.getRecommendBookList(mainCategory);

        //then
        assertAll(() -> assertNotNull(recommendBookList));
    }
}