package ncnk.make.backendroadmap.domain.restController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.entity.Main;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.Quiz;
import ncnk.make.backendroadmap.domain.restController.dto.Quiz.AlgorithmResponseDto;
import ncnk.make.backendroadmap.domain.restController.dto.Quiz.QuizResponseDto;
import ncnk.make.backendroadmap.domain.service.CodingTestService;
import ncnk.make.backendroadmap.domain.service.MainCategoryService;
import ncnk.make.backendroadmap.domain.service.QuizService;
import org.springframework.web.bind.annotation.*;

/**
 * 퀴즈 RestController (json)
 */

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/quiz")
public class QuizApiController {
    private final QuizService quizService;
    private final MainCategoryService mainCategoryService;
    private final CodingTestService codingTestService;

    //퀴즈 페이지 json
    @GetMapping("/{mainCategoryId}")
    public Quizzes quizzes(@PathVariable Long mainCategoryId) {
        if (mainCategoryId == Main.ALGORITHM.getMainDocsOrder()) {
            List<AlgorithmResponseDto> algorithmResponseDtos = new ArrayList<>();
            List<CodingTest> randomProblemsByLevel = codingTestService.findRandomProblemsByLevel();

            for (CodingTest codingTest : randomProblemsByLevel) {
                AlgorithmResponseDto algorithmResponseDto = AlgorithmResponseDto.createAlgorithmResponseDto(codingTest);
                algorithmResponseDtos.add(algorithmResponseDto);
            }
            return new Quizzes(algorithmResponseDtos);
        }

        MainCategory mainCategory = mainCategoryService.findMainCategoryById(mainCategoryId); //대분류 PK값을 통해 대분류 찾기
        List<Quiz> quizzes = quizService.getQuizzes(mainCategory); //대분류에 해당하는 퀴즈 List 얻기

        // 리스트 섞음
        Collections.shuffle(quizzes);

        // 섞인 리스트에서 처음 3개 데이터 선택
        quizzes = quizzes.subList(0, Math.min(quizzes.size(), 3));

        List<QuizResponseDto> quizResponseDtos = new ArrayList<>();
        log.info("Quiz Page");
        for (Quiz quiz : quizzes) {
            QuizResponseDto quizResponseDto = QuizResponseDto.createQuizResponseDto(quiz);
            quizResponseDtos.add(quizResponseDto); //퀴즈 응답값 dto 만들기
        }
        return new Quizzes(quizResponseDtos);
    }

    @AllArgsConstructor
    @Getter
    static class Quizzes<T> {
        private List<QuizResponseDto> quizzes;
    }

}