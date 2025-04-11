package ncnk.make.backendroadmap.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import ncnk.make.backendroadmap.domain.entity.Main;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.repository.MainCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MainCategoryServiceTest {

    @PersistenceContext
    EntityManager em;
    @InjectMocks
    private MainCategoryService mainCategoryService;

    @Mock
    private MainCategoryRepository mainCategoryRepository;

    @Spy
    private MainCategory mainCategory;

    // TODO: INIT Data 때문에 대분류 PK 값이 21부터 시작함...
    @DisplayName("대분류 PK 값으로 대분류 찾기")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @CsvSource({"21, INTERNET", "22, 'BASIC_FE'", "23, 'OS'", "24, 'LANGUAGE'", "25, 'ALGORITHM'"})
    void findMainCategoryByIdTest(Long id, Main mainDocsTitle) {
        // given
        MainCategory mainCategory = createMainCategory(mainDocsTitle);

        when(mainCategoryRepository.findMainCategoriesByMainDocsId(id)).thenReturn(Optional.of(mainCategory));
        // when
        MainCategory findMainCategory = mainCategoryService.findMainCategoryById(id);

        // then
        assertAll(() -> assertThat(findMainCategory.getMainDocsId()).isEqualTo(id),
            () -> assertThat(findMainCategory.getMainDocsTitle()).isEqualTo(mainDocsTitle));
    }

    private MainCategory createMainCategory(Main mainDocsTitle) {
        mainCategory = MainCategory.createMainCategory(mainDocsTitle, mainDocsTitle.getUrl());
        em.persist(mainCategory);
        return mainCategory;
    }
}