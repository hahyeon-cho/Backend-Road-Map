package ncnk.make.backendroadmap.domain.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.entity.Member;
import ncnk.make.backendroadmap.domain.exception.SessionNullPointException;
import ncnk.make.backendroadmap.domain.security.auth.LoginUser;
import ncnk.make.backendroadmap.domain.security.auth.dto.SessionUser;
import ncnk.make.backendroadmap.domain.service.CodingTestService;
import ncnk.make.backendroadmap.domain.service.MemberService;
import ncnk.make.backendroadmap.domain.service.SolvedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 코딩 테스트 페이지
 */
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/codingtest")
public class CodingTestController {

    private final CodingTestService codingTestService;
    private final SolvedService solvedService;
    private final MemberService memberService;

    // 문제 리스트 페이지
    @GetMapping("")
    public String codingTest(@LoginUser SessionUser user, Model model) {
        loginValidate(user);
        Member member = memberService.findMemberByEmail(user.getEmail());

        List<CodingTest> problems = codingTestService.findAllProblems();
        model.addAttribute("userPicture", user.getPicture());
        model.addAttribute("problems", problems);
        return "/codingTest/codingTest";
    }

    @GetMapping("/{id}")
    public String problemDetail(@PathVariable("id") Long codingTestId, @LoginUser SessionUser user, Model model) {
        loginValidate(user);
        Member member = memberService.findMemberByEmail(user.getEmail());
        model.addAttribute("userPicture", user.getPicture());

        CodingTest problem = codingTestService.findCodingTestById(codingTestId);
        model.addAttribute("problemId", problem.getCodingTestId());
        model.addAttribute("problemTitle", problem.getProblemTitle());
        model.addAttribute("problemDescription", problem.getProblemContents());
        model.addAttribute("problemLevel", problem.getProblemLevel());
        if (problem.getProblemTopics() != null) {
            model.addAttribute("problemTopic", String.join(", ", problem.getProblemTopics()));
        }
        return "/codingTest/codingTestProblem";
    }


    // 제출 버튼
    // Body 값에 userCodeResult 줘야함! ex: output
    @PostMapping("/{id}")
    public ResponseEntity<?> submitButton(@PathVariable Long id,
        @LoginUser SessionUser user,
        @RequestBody String userCodeResult) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Member member = memberService.findMemberByEmail(user.getEmail()); //로그인한 사용자 정보 얻기

        CodingTest codingTest = codingTestService.findCodingTestById(id);
        boolean isCorrect = codingTestService.evaluateCodingTest(userCodeResult, codingTest.getProblemInputOutput());
        solvedService.recordAttemptedProblem(codingTest, member, isCorrect);

        if (isCorrect) {
            solvedService.solvedProblem(codingTest, member);
            return ResponseEntity.ok().body("{\"result\":\"correct\"}");
        } else {
            return ResponseEntity.ok().body("{\"result\":\"incorrect\"}");
        }
    }

    private static void loginValidate(SessionUser user) {
        if (user == null) {
            throw new SessionNullPointException("[ERROR] SessionUser is null");
        }
    }
}