package ncnk.make.backendroadmap.domain.restController;

import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.entity.Main;
import ncnk.make.backendroadmap.domain.entity.MainCategory;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.entity.Quiz;
import ncnk.make.backendroadmap.domain.restController.DocsLikeApiController.Result;
import ncnk.make.backendroadmap.domain.restController.dto.quiz.AlgorithmResponseDto;
import ncnk.make.backendroadmap.domain.restController.dto.quiz.QuizAnswerDto;
import ncnk.make.backendroadmap.domain.restController.dto.quiz.QuizPageDto;
import ncnk.make.backendroadmap.domain.security.auth.LoginUser;
import ncnk.make.backendroadmap.domain.security.auth.dto.SessionUser;
import ncnk.make.backendroadmap.domain.service.CodingTestService;
import ncnk.make.backendroadmap.domain.service.MainCategoryService;
import ncnk.make.backendroadmap.domain.service.MemberService;
import ncnk.make.backendroadmap.domain.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final MemberService memberService;

    // 퀴즈 풀기 페이지
    @Timed("QuizApiController.quizzes")
    @GetMapping("/{mainCategoryId}")
    public Quizzes quizzes(@PathVariable Long mainCategoryId, HttpSession session) {
        session.removeAttribute("quizSubmitted");

        if (mainCategoryId == Main.ALGORITHM.getMainDocsOrder()) {
            List<AlgorithmResponseDto> algorithmResponseDtos = new ArrayList<>();
//            List<CodingTest> randomProblemsByLevel = codingTestService.findRandomProblemsByLevel();
            List<CodingTest> randomProblemsByLevel = codingTestService.findRandomProblemsByLevelWorst();

            for (CodingTest codingTest : randomProblemsByLevel) {
                AlgorithmResponseDto algorithmResponseDto = AlgorithmResponseDto.createAlgorithmResponseDto(codingTest);
                algorithmResponseDtos.add(algorithmResponseDto);
            }
            return new Quizzes(algorithmResponseDtos);
        }

        MainCategory mainCategory = mainCategoryService.findMainCategoryById(mainCategoryId); // 대분류 PK값을 통해 대분류 찾기
        List<Quiz> quizzes = quizService.getQuizzes(mainCategory); // 대분류에 해당하는 퀴즈 List 얻기

        // 리스트 섞음
        Collections.shuffle(quizzes);

        // 섞인 리스트에서 처음 3개 데이터 선택
        quizzes = quizzes.subList(0, Math.min(quizzes.size(), 3));

        List<QuizPageDto> quizResponseDtos = new ArrayList<>();
        log.info("Quiz Page");
        for (Quiz quiz : quizzes) {
            QuizPageDto quizResponseDto = QuizPageDto.createQuizResponseDto(quiz);
            quizResponseDtos.add(quizResponseDto); // 퀴즈 응답값 dto 만들기
        }

        session.setAttribute("quizzes", quizResponseDtos);

        return new Quizzes(quizResponseDtos);
    }

    // 퀴즈 채점 버튼
    @Timed("QuizApiController.gradeQuizzes")
    @PostMapping("/grade")
    public ResponseEntity<?> gradeQuizzes(@LoginUser SessionUser user,
        HttpSession session, @RequestBody List<String> userAnswers) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Result("로그인이 필요합니다."));
        }

        if (Boolean.TRUE.equals(session.getAttribute("quizSubmitted"))) {
            return ResponseEntity.badRequest().body("이미 퀴즈를 제출하셨습니다.");
        } else {
            //  퀴즈 채점 로직 실행
            session.setAttribute("quizSubmitted", Boolean.TRUE);

            try {
                List<QuizPageDto> quizResponseDtos = (List<QuizPageDto>) session.getAttribute("quizzes");

                List<String> quizAnswer = new ArrayList<>();
                for (QuizPageDto quizPageDto : quizResponseDtos) {
                    Quiz quiz = quizService.getQuiz(quizPageDto.getQuizContext(), quizPageDto.getQuizAnswer());
                    quizAnswer.add(quiz.getQuizAnswer());
                }

                boolean passed = quizService.gradeQuiz(quizAnswer, userAnswers);
                Member member = memberService.findMemberByEmail(user.getEmail()); // 로그인한 사용자 정보 얻기
                if (passed) {
                    memberService.updateLevel(member);
                }

                return ResponseEntity.ok().body(Map.of("passed", passed));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
    }

    // 퀴즈 해설 페이지
    @Timed("QuizApiController.quizExplain")
    @GetMapping("/explain")
    public QuizExplain quizExplain(HttpSession session) {
        List<QuizPageDto> quizResponseDtos = (List<QuizPageDto>) session.getAttribute("quizzes");

        List<QuizAnswerDto> quizAnswerDtos = new ArrayList<>();
        for (QuizPageDto quizPageDto : quizResponseDtos) {
            Quiz quiz = quizService.getQuiz(quizPageDto.getQuizContext(), quizPageDto.getQuizAnswer());
            quizAnswerDtos.add(QuizAnswerDto.createQuizResponseDto(quiz));
        }

        return new QuizExplain(quizAnswerDtos);
    }

    @AllArgsConstructor
    @Getter
    static class Quizzes<T> {

        private List<QuizPageDto> quizzes;
    }

    @AllArgsConstructor
    @Getter
    static class QuizExplain<T> {

        private List<QuizAnswerDto> quizzes;
    }
}