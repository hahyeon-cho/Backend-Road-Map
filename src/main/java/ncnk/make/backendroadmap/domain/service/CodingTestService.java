package ncnk.make.backendroadmap.domain.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.api.leetcode.LeetCodeApi;
import ncnk.make.backendroadmap.domain.aop.time.callback.TraceTemplate;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.entity.Problem;
import ncnk.make.backendroadmap.domain.exception.JsonParsingException;
import ncnk.make.backendroadmap.domain.exception.ResourceNotFoundException;
import ncnk.make.backendroadmap.domain.repository.CodingTest.CodingTestRepository;
import ncnk.make.backendroadmap.domain.utils.LeetCode.LeetCodeCrawling;
import ncnk.make.backendroadmap.domain.utils.LeetCode.WebDriverPool;
import ncnk.make.backendroadmap.domain.utils.LeetCode.wrapper.CodingTestAnswer;
import ncnk.make.backendroadmap.domain.utils.LeetCode.wrapper.CodingTestProblem;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodingTestService {
    private final CodingTestRepository codingTestRepository;
    private final LeetCodeApi leetCodeApi;
    private final LeetCodeCrawling leetcodeCrawling;
    private final WebDriverPool webDriverPool;
    private static final AtomicInteger COUNT = new AtomicInteger(20);
    ;
    private final TraceTemplate template;

    //    @Scheduled(cron = "0 0 3 * * SUN") // 매주 일요일 새벽 3시
    //    @Scheduled(cron = "0 0 3 1 * ?")  // 매월 1일 새벽 3시
    @Profile("!test")
    public void scrapeAllProblemsOnSchedule() {
        scrapeAllProblems();
    }

    @Profile("!test")
    @EventListener(ApplicationReadyEvent.class)
    public void scrapeAllProblemsAtStart() {
        scrapeAllProblems();
    }

    @Timed("CodingTestService.scrapeAndSaveProblemAsync")
    @Counted("Counted.CodingTest.scrapeAndSaveProblemAsync")
    @Async
    public void scrapeAndSaveProblemAsync(JSONObject problem) {
        WebDriver driver = null;
        try {
            driver = webDriverPool.getDriver();
            Optional<CodingTestProblem> problemOptional = leetcodeCrawling.scrapeLeetCodeProblemContents(
                    problem, driver);

            if (problemOptional.isPresent()) {
                saveProblem(problemOptional);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("[ERROR] During async scraping and saving problem = {}", e.getMessage());
        } finally {
            if (driver != null) {
                webDriverPool.returnDriver(driver);
            }
        }
    }

    private void scrapeAllProblems() {
        try {
            List<JSONObject> problems = leetCodeApi.getLeetCodeProblemList();
            AtomicInteger temp = new AtomicInteger(0);
            for (JSONObject problem : problems) {
                if (temp.get() < COUNT.get()) {
                    log.info("--------Before scrapeAndSaveProblemAsync------");
                    scrapeAndSaveProblemAsync(problem);
                    log.info("--------After scrapeAndSaveProblemAsync------");
                    temp.incrementAndGet();
                }
            }
        } catch (Exception e) {
            log.error("[ERROR] ScrapeAllProblems = {}", e.getMessage());
        }
    }

    private CodingTest saveProblem(Optional<CodingTestProblem> codingTestProblem) {
        CodingTest codingTest = CodingTest.createCodingTest(
                codingTestProblem.get().getProblemTitle(),
                codingTestProblem.get().getProblemSlug(),
                codingTestProblem.get().getProblemLevel(),
                codingTestProblem.get().getProblemAccuracy(),
                codingTestProblem.get().getProblemContents(),
                codingTestProblem.get().getProblemImages(),
                codingTestProblem.get().getExInputOutput(),
                codingTestProblem.get().getProblemTopics()
        );
        return codingTestRepository.save(codingTest);
    }

    /**
     * 예상 입출력 중 첫 번째 예상 입력에 대한 예상 출력 값과 사용자가 웹 컴파일러를 통해 결과를 반환한 것을 비교한다. List<CodingTestAnswer>의 예상 출력 값이 사용자가 반환한 결과와
     * 일치하면 문제 정답 처리!
     */
    @Timed("CodingTestService.evaluateCodingTest")
    @Transactional
    public boolean evaluateCodingTest(String userCodeResult, List<CodingTestAnswer> codingTestAnswer) {
        ObjectMapper mapper = new ObjectMapper();

        return template.execute("CodingTestService.evaluateCodingTest", () -> {
            try {
                // LinkedHashMap 객체를 JSON 문자열로 변환
                String json = mapper.writeValueAsString(codingTestAnswer.get(0));
                // JSON 문자열을 CodingTestAnswer 객체로 변환
                CodingTestAnswer answer = mapper.readValue(json, CodingTestAnswer.class);
                String output = answer.getOutput();

                // URL에 특정 예약된 문자들이 퍼센트 인코딩(%로 시작하는 인코딩)을 사용하여 전송되어야 하는 경우
                String decodedUserCodeResult = URLDecoder.decode(userCodeResult,
                        StandardCharsets.UTF_8.name());

                return CodingTest.evaluate(decodedUserCodeResult, output);
            } catch (JsonProcessingException | UnsupportedEncodingException e) {
                throw new JsonParsingException();
            }
        });
    }

    public List<CodingTest> findRandomProblemsByLevelWorst() {
        return codingTestRepository.findCsProblems();
    }

    // 알고리즘 문제는 사용자가 풀지 않은 문제 중 하/하/중 3문제를 랜덤으로 뽑는다.
    public List<CodingTest> findRandomProblemsByLevel() {
        List<CodingTest> normalProblems = codingTestRepository.findRandomProblemsByLevel(
                Problem.NORMAL.getProblemLevel(), 1);
        List<CodingTest> easyProblems = codingTestRepository.findRandomProblemsByLevel(
                Problem.EASY.getProblemLevel(), 2);

        List<CodingTest> result = new ArrayList<>();
        result.addAll(normalProblems);
        result.addAll(easyProblems);

        return result;
    }

    // 코딩 테스트 PK 값으로 알고리즘 문제 찾기
    public CodingTest findCodingTestById(Long codingTestId) {
        return codingTestRepository.findCodingTestByCodingTestId(codingTestId)
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    // 문제 리스트에서 정렬 기능
    public Page<CodingTest> dynamicSearching(String problemLevel, String problemAccuracy, String status,
                                             Pageable pageable) {
        return codingTestRepository.dynamicSearching(problemLevel, problemAccuracy, status, pageable);
    }
}