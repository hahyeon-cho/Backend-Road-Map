package ncnk.make.backendroadmap.domain.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.api.leetcode.LeetCodeApiService;
import ncnk.make.backendroadmap.domain.entity.CodingTest;
import ncnk.make.backendroadmap.domain.repository.CodingTestRepository;
import ncnk.make.backendroadmap.domain.utils.CodingTestProblemDTO;
import ncnk.make.backendroadmap.domain.utils.WebDriverPool;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class CodingTestService {
    private final CodingTestRepository codingTestRepository;
    private final LeetCodeApiService leetCodeApiService;
    private final LeetcodeScraperService leetcodeScraperService;
    private final WebDriverPool webDriverPool;

    @Autowired
    public CodingTestService(CodingTestRepository codingTestRepository,
                             LeetCodeApiService leetCodeApiService,
                             LeetcodeScraperService leetcodeScraperService,
                             WebDriverPool webDriverPool) {
        this.codingTestRepository = codingTestRepository;
        this.leetCodeApiService = leetCodeApiService;
        this.leetcodeScraperService = leetcodeScraperService;
        this.webDriverPool = webDriverPool;
    }

    @Async
    public void scrapeAndSaveProblemAsync(JSONObject problem) {
        WebDriver driver = null;
        try {
            driver = webDriverPool.getDriver();
            Optional<CodingTestProblemDTO> problemOptional = leetcodeScraperService.scrapeLeetCodeProblemContents(problem, driver);

            problemOptional.ifPresent(this::saveProblem);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Error during async scraping and saving problem", e);
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

    public void scrapeAllProblems() {
        try {
            List<JSONObject> problems = leetCodeApiService.getLeetCodeProblemList();
            for (JSONObject problem : problems) {
                scrapeAndSaveProblemAsync(problem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CodingTest saveProblem(CodingTestProblemDTO problemDTO) {
        CodingTest codingTest = CodingTest.createCodingTest(
                problemDTO.getProblemTitle(),
                problemDTO.getProblemSlug(),
                problemDTO.getProblemLevel(),
                problemDTO.getProblemAccuracy(),
                problemDTO.getProblemContents(),
                problemDTO.getProblemImages(),
                problemDTO.getProblemTopics()
        );

        return codingTestRepository.save(codingTest);
    }


}