package ncnk.make.backendroadmap.domain.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.api.leetcode.LeetCodeApiService;
import ncnk.make.backendroadmap.domain.utils.CodingTestAnswerDTO;
import ncnk.make.backendroadmap.domain.utils.ProblemInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Service
public class LeetcodeScraperService {
    private static WebDriver driver;
    private final LeetCodeApiService leetCodeApiService;

    @Autowired
    public LeetcodeScraperService(LeetCodeApiService leetCodeApiService) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

        this.leetCodeApiService = leetCodeApiService;
    }

    public ProblemInfo scrapeLeetCodeProblemContents(JSONObject problem) throws JSONException {
        String PROBLEMS_BASE_URL = "https://leetcode.com/problems/";

        ProblemInfo problemInfo = ProblemInfo.createProblemInfo();

        problemInfo.updateInit(problem.getJSONObject("stat").getString("question__title_slug"),
                problem.getJSONObject("stat").getString("question__title"),
                problem.getJSONObject("difficulty").getInt("level"));

        long acs = problem.getJSONObject("stat").getLong("total_acs");
        long submitted = problem.getJSONObject("stat").getLong("total_submitted");
        problemInfo.setCorrectRate(calCorrectRate(acs, submitted));

        LeetcodeScraperService.driver.get(PROBLEMS_BASE_URL + problemInfo.getSlug());
        new WebDriverWait(LeetcodeScraperService.driver, Duration.ofSeconds(20)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-track-load='description_content']")));

        Document doc = Jsoup.parse(LeetcodeScraperService.driver.getPageSource());
        Element contents = doc.selectFirst("[data-track-load='description_content']");

        List<String> imagePaths = new ArrayList<>();
        Elements imgElements = contents.select("img");
        if (!imgElements.isEmpty()) {
            String imgDir = "src/main/resources/images/algorithm/" + problemInfo.getSlug();
            for (int i = 0; i < imgElements.size(); i++) {
                Element img = imgElements.get(i);
                String imgUrl = img.attr("src");

                String imgPath = changeImgPath(imgUrl, imgDir, problemInfo.getSlug(), i);
                img.attr("src", imgPath);
                imagePaths.add(imgPath);
            }
        }

        problemInfo.setImages(imagePaths);
        problemInfo.setContents(contents.outerHtml());

        Elements preData = contents.select("pre");
        List<CodingTestAnswerDTO> exlist = getExamples(preData);
        problemInfo.setExampleResults(exlist);

        List<String> tags = getTags();
        problemInfo.setTags(tags);

        System.out.println(problemInfo.getTitle());

        return problemInfo;
    }

    public static double calCorrectRate(long acs, long submitted) {
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

    public static List<CodingTestAnswerDTO> getExamples(Elements preData) {
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

    public static List<String> getTags() {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("div a[href^='/tag/']"))
        );
        List<WebElement> tagElements = driver.findElements(By.cssSelector("div a[href^='/tag/']"));

        List<String> tags = new ArrayList<>();
        for (WebElement tagElement : tagElements) {
            tags.add(tagElement.getAttribute("innerText"));
        }

        return tags;
    }

    @Async
    public void scrapeProblemAsync(JSONObject problem) {
        try {
            scrapeLeetCodeProblemContents(problem);
        } catch (TimeoutException e){
            log.info("Scraper - Connection timed out.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void destroy() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Scheduled(cron = "0 0 3 * * SUN")  // 매주 일요일 새벽 3시에 실행
    public void scrapeAllProblemsWeekly() {

        try {
            List<JSONObject> problems = leetCodeApiService.getLeetCodeProblemList();
            for (JSONObject problem : problems) {
                scrapeProblemAsync(problem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}