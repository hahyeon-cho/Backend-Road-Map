package ncnk.make.backendroadmap.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Main;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.Sub;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import ncnk.make.backendroadmap.domain.repository.SubCategory.SubCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@Slf4j
class SubCategoryServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    SubCategoryService subCategoryService;

    @Mock // Spring Context에 등록된 Bean을 Mocking합니다.
    SubCategoryRepository subCategoryRepository;

    @DisplayName("대분류로 소분류 찾기")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @CsvSource({"IP, '[Internet protocol]\n네트워크를 통해 데이터를 보내는 데 사용되는 프로토콜.'",
            "TCP_UDP, '[Transmission Control Protocol]\n[User Datagram Protocol]\n네트워크를 통해 데이터를 전송하는 데 사용되는 전송 계층 프로토콜. TCP는 신뢰성 있는 전송을, UDP는 빠른 전송을 제공.'",
            "PORT, '통신을 위한 운영 체제의 포트.'",
            "DNS, '[Domain Name System]\n도메인 이름을 IP 주소로 변환하여 웹 사이트에 액세스하는 데 사용되는 시스템.'",
            "HTTP, '[HyperText Transfer Protocol]\n웹에서 데이터를 전송하는 데 사용되는 프로토콜.'"})
    void getSubCategoriesByMainCategoryTest(Sub sub, String subDescription) {
        //given
        MainCategory mainCategory = createMainCategory();
        SubCategory subCategory = createSubCategory(sub, mainCategory);
        when(subCategoryRepository.findSubCategoriesByMainCategory(mainCategory)).thenReturn(
                Collections.singletonList(subCategory));
        //when
        List<Sub> subCategoriesByMainCategory = subCategoryService.getSubCategoriesByMainCategory(mainCategory);

        //then
        assertAll(
                () -> assertThat(subCategoriesByMainCategory).isNotNull(),
                () -> assertThat(subCategoriesByMainCategory).hasSize(1),
                () -> assertThat(subCategoriesByMainCategory.get(0).getSubDescription()).isEqualTo(subDescription)
        );
    }

    @DisplayName("소분류 PK 값으로 소분류 찾기")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @CsvSource({"1, IP", "2, TCP_UDP", "3, PORT", "4, DNS", "5, HTTP"})
    void findSubCategoryByIdTest(Long id, Sub sub) {
        //given
        MainCategory mainCategory = MainCategory.createMainCategory(Main.INTERNET, Main.INTERNET.getUrl());
        SubCategory subCategory = SubCategory.createSubCategory(sub, 0L, sub.getSubDescription(), mainCategory);
        when(subCategoryRepository.findSubCategoryBySubDocsId(id)).thenReturn(Optional.of(subCategory));

        //when
        SubCategory findSubCategory = subCategoryService.findSubCategoryById(id);

        //then
        assertAll(
                () -> assertThat(findSubCategory.getSubDocsId()).isEqualTo(id),
                () -> assertThat(findSubCategory.getSubDocsTitle()).isEqualTo(sub)
        );
    }

    private MainCategory createMainCategory() {
        MainCategory mainCategory = MainCategory.createMainCategory(Main.INTERNET, Main.INTERNET.getUrl());
        em.persist(mainCategory);
        return mainCategory;
    }

    private SubCategory createSubCategory(Sub sub, MainCategory mainCategory) {
        SubCategory subCategory = SubCategory.createSubCategory(sub, 0L, sub.getSubDescription(), mainCategory);
        em.persist(subCategory);
        return subCategory;
    }
}