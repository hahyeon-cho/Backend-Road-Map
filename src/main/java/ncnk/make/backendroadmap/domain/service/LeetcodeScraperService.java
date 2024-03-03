package ncnk.make.backendroadmap.domain.service;

import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.api.leetcode.LeetCodeApiService;
import ncnk.make.backendroadmap.domain.utils.CodingTestAnswerDTO;
import ncnk.make.backendroadmap.domain.utils.CodingTestProblemDTO;
import ncnk.make.backendroadmap.domain.utils.WebDriverPool;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Service
public class LeetcodeScraperService {
    private static final String PROBLEMS_BASE_URL = "https://leetcode.com/problems/";

    public Optional<CodingTestProblemDTO> scrapeLeetCodeProblemContents(JSONObject problem, WebDriver driver) throws JSONException {
        CodingTestProblemDTO problemInfo = CodingTestProblemDTO.createProblemInfo();
        String slug = problem.getJSONObject("stat").getString("question__title_slug");
        String contents = "";
        List<CodingTestAnswerDTO> exlist;
        List<String> imagePaths = new ArrayList<>();
        List<String> topics;

        try {
            long acs = problem.getJSONObject("stat").getLong("total_acs");
            long submitted = problem.getJSONObject("stat").getLong("total_submitted");
            double correctRate = calCorrectRate(acs, submitted);

            driver.get(PROBLEMS_BASE_URL + slug);
            new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-track-load='description_content']")));

            Document doc = Jsoup.parse(driver.getPageSource());
            Element contentsElement = doc.selectFirst("[data-track-load='description_content']");

            Elements imgElements = contentsElement.select("img");
            if (!imgElements.isEmpty()) {
                String imgDir = "src/main/resources/images/algorithm/" + slug;
                for (Element img : imgElements) {
                    String imgUrl = img.attr("src");

                    String imgPath = changeImgPath(imgUrl, imgDir, slug, imgElements.indexOf(img));
                    img.attr("src", imgPath);
                    imagePaths.add(imgPath);
                }
            }

            String level;
            switch (problem.getJSONObject("difficulty").getInt("level")) {
                case 1:
                    level = "Easy";
                    break;
                case 2:
                    level = "Nomal";
                    break;
                case 3:
                    level = "Hard";
                    break;
                default:
                    level = "Easy";
                    break;
            }


            contents = contentsElement.outerHtml();

            Elements preData = contentsElement.select("pre");
            exlist = getExamples(preData);
            topics = getTopics(driver);

            problemInfo.updateInit(
                    problem.getJSONObject("stat").getString("question__title"),
                    slug,
                    level,
                    correctRate,
                    contents,
                    imagePaths,
                    exlist,
                    topics
            );

            return Optional.of(problemInfo);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public double calCorrectRate(long acs, long submitted) {
        if (acs == 0) {
            return 0.0;
        }

        double percentage = ((double) acs / submitted) * 100;
        return Math.round(percentage * 100.0) / 100.0;
    }

    private String changeImgPath(String imgUrl, String imgDir, String slug, int idx) {
        try {
            File directory = new File(imgDir);
            if (!directory.exists()) { directory.mkdirs(); }

            String imgPath = imgDir + "/" + slug + "_" + idx + ".png";
            URL url = new URL(imgUrl);
            InputStream inputStream = url.openStream();
            Path targetPath = Path.of(imgPath);
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);

            return imgPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<CodingTestAnswerDTO> getExamples(Elements preData) {
        List<CodingTestAnswerDTO> exlist = new ArrayList<>();

        for (Element pre : preData) {
            String preText = pre.text().trim();
            Pattern pattern = Pattern.compile("Input:(.*?)Output:(.*?)Explanation:(.*)", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(preText);

            if (matcher.find()) {
                CodingTestAnswerDTO ex = new CodingTestAnswerDTO();

                ex.setExample(matcher.group(1).trim(), matcher.group(2).trim());
                exlist.add(ex);
            }
        }

        return exlist;
    }

    public List<String> getTopics(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("div a[href^='/tag/']"))
        );
        List<WebElement> tagElements = driver.findElements(By.cssSelector("div a[href^='/tag/']"));

        List<String> topics = new ArrayList<>();
        for (WebElement tagElement : tagElements) {
            topics.add(tagElement.getAttribute("innerText"));
        }

        return topics;
    }

//    @Async
//    public void scrapeProblemAsync(JSONObject problem) {
//        WebDriver driver = null;
//        try {
//            driver = webDriverPool.getDriver();
//            Optional<CodingTestProblemDTO> problemOptional = scrapeLeetCodeProblemContents(problem, driver); // 수정된 메서드 호출
//
//            if (problemOptional.isPresent()) {
//                // DB insert
//                // problemOptional.ifPresent(this::insertDB);
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        } catch (Exception e) {
//            log.error("Error message", e);
//        } finally {
//            if (driver != null) {
//                webDriverPool.returnDriver(driver); // 드라이버 반환
//            }
//        }
//    }

//    @Async
//    public CompletableFuture<Optional<CodingTestProblemDTO>> scrapeProblemAsync(JSONObject problem) {
//        CompletableFuture<Optional<CodingTestProblemDTO>> future = new CompletableFuture<>();
//        WebDriver driver = null;
//
//        try {
//            driver = webDriverPool.getDriver();
//            Optional<CodingTestProblemDTO> problemOptional = scrapeLeetCodeProblemContents(problem, driver);
//            future.complete(problemOptional);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            future.completeExceptionally(e);
//        } catch (Exception e) {
//            future.completeExceptionally(e);
//        } finally {
//            if (driver != null) {
//                webDriverPool.returnDriver(driver); // 작업 완료 후 드라이버 반환
//            }
//        }
//        return future;
//    }

//    public void driverSettings(){
//        WebDriverManager.chromedriver().setup();
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
//        driver = new ChromeDriver(options);
//    }

//    @PreDestroy
//    public void destroy() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }

//

//    @Scheduled(cron = "0 0 3 * * SUN")  // 매주 일요일 새벽 3시에 실행
//    public void scrapeAllProblemsWeekly() {
//        try {
//            List<JSONObject> problems = leetCodeApiService.getLeetCodeProblemList();
//            for (JSONObject problem : problems) {
//                scrapeProblemAsync(problem);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void nonScheduledScrape() {
//        try {
//            List<JSONObject> problems = leetCodeApiService.getLeetCodeProblemList();
//
//            for (JSONObject p : problems) {
//                scrapeProblemAsync(p);
//            }
//        } catch (Exception e) {
//            log.error("Error message", e);
//        }
//    }


}