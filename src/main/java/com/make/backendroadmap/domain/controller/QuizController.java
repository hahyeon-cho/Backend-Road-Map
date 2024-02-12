package com.make.backendroadmap.domain.controller;

import com.make.backendroadmap.domain.controller.dto.Quiz.QuizResponseDto;
import com.make.backendroadmap.domain.entity.MainCategory;
import com.make.backendroadmap.domain.entity.Quiz;
import com.make.backendroadmap.domain.service.MainCategoryService;
import com.make.backendroadmap.domain.service.QuizService;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;
    private final MainCategoryService mainCategoryService;

    @GetMapping("/{mainCategoryId}")
    public Quizzes quizzes(@PathVariable Long mainCategoryId) {
        MainCategory mainCategory = mainCategoryService.findMainCategoryById(mainCategoryId);
        List<Quiz> quizzes = quizService.getQuizzes(mainCategory);
        List<QuizResponseDto> quizResponseDtos = new ArrayList<>();

        log.info("Quiz Page");
        for (Quiz quiz : quizzes) {
            QuizResponseDto quizResponseDto = QuizResponseDto.createQuizResponseDto(quiz);
            quizResponseDtos.add(quizResponseDto);
            log.info("Quiz Response = {}", quizResponseDto.getQuizExplain());
        }
        return new Quizzes(quizResponseDtos);
    }

    @AllArgsConstructor
    @Getter
    static class Quizzes<T> {
        private List<QuizResponseDto> quizzes;
    }

}
