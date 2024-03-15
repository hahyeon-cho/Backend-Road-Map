package ncnk.make.backendroadmap.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import ncnk.make.backendroadmap.domain.entity.Main;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MainCategoryServiceTest {
    @Autowired
    MainCategoryService mainCategoryService;

    @DisplayName("대분류 PK 값으로 대분류 찾기")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @CsvSource({"1, INTERNET", "2, 'BASIC_FE'", "3, 'OS'", "4, 'LANGUAGE'", "5, 'ALGORITHM'"})
    void findMainCategoryByIdTest(Long id, Main mainDocsTitle) {
        //given

        //when
        MainCategory findMainCategory = mainCategoryService.findMainCategoryById(id);

        //then
        assertAll(() -> assertThat(findMainCategory.getMainDocsId()).isEqualTo(id),
                () -> assertThat(findMainCategory.getMainDocsTitle()).isEqualTo(mainDocsTitle));
    }
}