package ncnk.make.backendroadmap.domain.restController;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Main;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.RecommendBook;
import ncnk.make.backendroadmap.domain.entity.Sub;
import ncnk.make.backendroadmap.domain.entity.SubCategory;
import ncnk.make.backendroadmap.domain.restController.dto.roadMap.RoadMapResponseDto;
import ncnk.make.backendroadmap.domain.service.MainCategoryService;
import ncnk.make.backendroadmap.domain.service.SubCategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc // MockMvc 자동 구성
@ActiveProfiles("test")
@Slf4j
@Transactional
class RoadMapApiControllerTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainCategoryService mainCategoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @DisplayName("전체 대분류 카테고리")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @MethodSource("mainCategoryTestArguments")
    void mainCategoryTest(long id, Main main) throws Exception {
        // given
        MainCategory findMainCategory = mainCategoryService.findMainCategoryById(id);
        List<Sub> subCategories = subCategoryService.getSubCategoriesByMainCategory(findMainCategory);
        RoadMapResponseDto roadMapResponseDto = RoadMapResponseDto.createRoadMapResponseDto(main, subCategories,
            main.getUrl());
        List<RoadMapResponseDto> roadMapResponseDtos = Arrays.asList(roadMapResponseDto);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/roadmap/category"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(roadMapResponseDtos.size()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.mainDocsTitle[%d].url", main.ordinal())
                .value(main.getUrl()));
    }

    @DisplayName("대분류 카테고리 자세히 보기")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @MethodSource("subCategoryTestArguments")
    void subCategoryTest(long id, Sub sub) throws Exception {
        //given
        MainCategory mainCategory = mainCategoryService.findMainCategoryById(id);

        SubCategory subCategory = createSubCategory(sub, mainCategory);
        RecommendBook recommendBook = createRecommend(mainCategory);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/roadmap/sub/{mainCategoryId}", mainCategory.getMainDocsId()))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.subCategory[0].subDocsTitle")
                .value(subCategory.getSubDocsTitle().getSubCategory()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.subCategory[0].subDescription")
                .value(subCategory.getSubDescription()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.subCategory[0].likeCount")
                .value(subCategory.getLikeCount()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.url")
                .value(subCategory.getMainCategory().getMainDocsTitle().getUrl()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.recommendBookDtos[0].bookTitle")
                .value(recommendBook.getBookTitle()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.recommendBookDtos[0].bookAuthor")
                .value(recommendBook.getBookAuthor()));
    }

    static Stream<Arguments> subCategoryTestArguments() {
        List<Arguments> arguments = new ArrayList<>();
        long[] ids = {1, 2, 3, 4, 5};
        Sub[] subs = {Sub.IP, Sub.HTML, Sub.OS_WORK, Sub.JAVA, Sub.BUBBLE_SORT};

        for (int i = 0; i < subs.length; i++) {
            arguments.add(Arguments.of(ids[i], subs[i]));
        }

        return arguments.stream();
    }

    static Stream<Arguments> mainCategoryTestArguments() {
        List<Arguments> arguments = new ArrayList<>();
        long[] ids = {1, 2, 3, 4, 5};
        Main[] mains = {Main.INTERNET, Main.BASIC_FE, Main.OS, Main.LANGUAGE, Main.ALGORITHM};

        for (int i = 0; i < mains.length; i++) {
            arguments.add(Arguments.of(ids[i], mains[i]));
        }

        return arguments.stream();
    }


    private SubCategory createSubCategory(Sub sub, MainCategory mainCategory) {
        SubCategory subCategory = SubCategory.createSubCategory(sub, 0L, sub.getSubDescription(), mainCategory);
        em.persist(subCategory);
        return subCategory;
    }

    private RecommendBook createRecommend(MainCategory mainCategory) {
        RecommendBook recommendBook = RecommendBook.createRecommend("bookTitle", "bookAuthor", "bookImage", "publisher",
            mainCategory);
        em.persist(recommendBook);
        return recommendBook;
    }
}