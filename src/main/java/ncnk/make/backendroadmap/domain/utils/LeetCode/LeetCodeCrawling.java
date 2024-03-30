package ncnk.make.backendroadmap.domain.utils.LeetCode;

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
import lombok.extern.slf4j.Slf4j;
import ncnk.make.backendroadmap.domain.utils.LeetCode.wrapper.CodingTestAnswer;
import ncnk.make.backendroadmap.domain.utils.LeetCode.wrapper.CodingTestProblem;
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
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class LeetCodeCrawling {
    private static final String PROBLEMS_BASE_URL = "https://leetcode.com/problems/";

    public Optional<CodingTestProblem> scrapeLeetCodeProblemContents(JSONObject problem, WebDriver driver)
            throws JSONException {
        try {
            String slug = problem.getJSONObject("stat").getString("question__title_slug");
            long acs = problem.getJSONObject("stat").getLong("total_acs");
            long submitted = problem.getJSONObject("stat").getLong("total_submitted");
            double correctRate = calCorrectRate(acs, submitted);

            String level;
            switch (problem.getJSONObject("difficulty").getInt("level")) {
                case 1:
                    level = "Easy";
                    break;
                case 2:
                    level = "Normal";
                    break;
                case 3:
                    level = "Hard";
                    break;
                default:
                    level = "Easy";
                    break;
            }

            driver.get(PROBLEMS_BASE_URL + slug);
            new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector("[data-track-load='description_content']")));

            Document doc = Jsoup.parse(driver.getPageSource());
            Element contentsElement = doc.selectFirst("[data-track-load='description_content']");

            String contents = contentsElement.outerHtml();

            List<String> imagePaths = new ArrayList<>();
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

            Elements exampleData = contentsElement.select("div.example-block");
            if (exampleData.isEmpty()) { exampleData = contentsElement.select("pre"); }
            List<CodingTestAnswer> exlist = getExamples(exampleData);
            List<String> topics = getTopics(driver);

            CodingTestProblem problemInfo = CodingTestProblem.createProblemInfo(
                    problem.getJSONObject("stat").getString("question__title"), slug, level, correctRate, contents,
                    imagePaths, exlist, topics);
            return Optional.of(problemInfo);
        } catch (Exception e) {
            log.error("==========스크랩 도중 에러 발생==========", e.getMessage());
            return Optional.empty();
        }
    }

    private double calCorrectRate(long acs, long submitted) {
        if (acs == 0) {
            return 0.0;
        }

        double percentage = ((double) acs / submitted) * 100;
        return Math.round(percentage * 100.0) / 100.0;
    }

    private String changeImgPath(String imgUrl, String imgDir, String slug, int idx) {
        try {
            File directory = new File(imgDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String imgPath = imgDir + "/" + slug + "_" + idx + ".png";
            URL url = new URL(imgUrl);
            InputStream inputStream = url.openStream();
            Path targetPath = Path.of(imgPath);
            Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);

            return imgPath;
        } catch (IOException e) {
            log.error("[Error] Fail ChangeImgPath! {}", e.getMessage());
            return null;
        }
    }

    private List<CodingTestAnswer> getExamples(Elements exampleData) {
        List<CodingTestAnswer> exlist = new ArrayList<>();
        Pattern pattern = Pattern.compile("Input:\\s*(.*?)\\s*Output:\\s*(.*?)(?:\\s*Explanation:|$)", Pattern.DOTALL);

        for (Element example : exampleData) {
            String exampleText = example.text().trim();
            Matcher matcher = pattern.matcher(exampleText);

            while (matcher.find()) {
                String input = matcher.group(1).trim().replace("\"", "");
                String output = matcher.group(2).trim().replace("\"", "");
                CodingTestAnswer ex = CodingTestAnswer.createCodingTestAnswer(input, output);
                exlist.add(ex);
            }
        }

        return exlist;
    }


    private List<String> getTopics(WebDriver driver) {
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
}