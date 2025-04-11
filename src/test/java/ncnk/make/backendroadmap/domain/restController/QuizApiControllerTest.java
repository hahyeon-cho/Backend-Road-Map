package ncnk.make.backendroadmap.domain.restController;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.Main;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.Quiz;
import ncnk.make.backendroadmap.domain.service.MainCategoryService;
import ncnk.make.backendroadmap.domain.service.QuizService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Slf4j
@Transactional
class QuizApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private QuizService quizService;

    @Autowired
    private MainCategoryService mainCategoryService;

    @DisplayName("퀴즈 Rest API")
    @ParameterizedTest(name = "{index} {displayName} arguments = {arguments}")
    @EnumSource(value = Main.class, names = {"INTERNET", "BASIC_FE", "OS", "LANGUAGE", "ALGORITHM"})
    void quizzesTest(Main main) throws Exception {
        // given
        MainCategory mainCategory = mainCategoryService.findMainCategoryById((long) main.getMainDocsOrder());
        List<Quiz> quizzes = quizService.getQuizzes(mainCategory);

        // when & then
        mockMvc.perform(get("/api/quiz/{mainCategoryId}", mainCategory.getMainDocsId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.quizzes", hasSize(3)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.quizzes[0].quizContext")
                .value(quizzes.get(0).getQuizContext()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.quizzes[0].quizAnswer")
                .value(quizzes.get(0).getQuizAnswer()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.quizzes[0].quizExplain")
                .value(quizzes.get(0).getQuizExplain()));
    }
}