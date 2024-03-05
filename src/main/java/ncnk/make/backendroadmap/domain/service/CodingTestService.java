package ncnk.make.backendroadmap.domain.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.api.leetcode.LeetCodeApi;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.repository.CodingTestRepository;
import ncnk.make.backendroadmap.domain.utils.LeetCodeCrawling;
import ncnk.make.backendroadmap.domain.utils.WebDriverPool;
import ncnk.make.backendroadmap.domain.utils.wrapper.CodingTestProblem;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CodingTestService {
    private final CodingTestRepository codingTestRepository;
    private final LeetCodeApi leetCodeApi;
    private final LeetCodeCrawling leetcodeCrawling;
    private final WebDriverPool webDriverPool;
    private static final int COUNT = 50;


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

    @Scheduled(cron = "0 0 3 * * SUN") // 매주 일요일 새벽 3시
    //    @Scheduled(cron = "0 0 3 1 * ?")  // 매월 1일 새벽 3시
    public void scrapeAllProblemsOnSchedule() {
        scrapeAllProblems();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void scrapeAllProblemsAtStart() {
        scrapeAllProblems();
    }

    private void scrapeAllProblems() {
        try {
            List<JSONObject> problems = leetCodeApi.getLeetCodeProblemList();
            int temp = 0;
            for (JSONObject problem : problems) {
                if (temp < COUNT) {
                    scrapeAndSaveProblemAsync(problem);
                    temp++;
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
}