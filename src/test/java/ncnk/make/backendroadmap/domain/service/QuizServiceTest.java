package ncnk.make.backendroadmap.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import ncnk.make.backendroadmap.domain.entity.Main;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.Quiz;
import ncnk.make.backendroadmap.domain.repository.Quiz.QuizRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class QuizServiceTest {
    @Autowired
    QuizService quizService;

    @MockBean // Spring Context에 등록된 Bean을 Mocking합니다.
    QuizRepository quizRepository;


    @DisplayName("대분류에 맞는 퀴즈 검색")
    @Test
    void getQuizzesTest() {
        //given
        MainCategory mainCategory = MainCategory.createMainCategory(Main.BASIC_FE, "url");

        Quiz quiz1 = Quiz.createQuiz("퀴즈 내용1", "퀴즈 정답1", "퀴즈 설명1", mainCategory);

        Quiz quiz2 = Quiz.createQuiz("퀴즈 내용2", "퀴즈 정답2", "퀴즈 설명2", mainCategory);

        List<Quiz> expectedQuizzes = Arrays.asList(quiz1, quiz2); // 예상되는 반환 값 설정
        when(quizRepository.findQuizzesByMainCategory(mainCategory)).thenReturn(expectedQuizzes);

        //when
        List<Quiz> quizzes = quizService.getQuizzes(mainCategory);

        //then
        assertAll(() -> assertEquals(quizzes.size(), 2),
                () -> assertThat(quizzes).containsExactlyInAnyOrderElementsOf(expectedQuizzes));
    }
}