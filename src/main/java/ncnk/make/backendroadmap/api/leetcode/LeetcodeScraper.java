package ncnk.make.backendroadmap.api.leetcode;

import ncnk.make.backendroadmap.domain.repository.ExampleResult;
import ncnk.make.backendroadmap.domain.repository.ProblemInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static java.lang.Thread.sleep;


public class LeetcodeScraper {
    private static WebDriver driver;

    public LeetcodeScraper() {
        WebDriverManager.chromedriver().clearDriverCache().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    public List<JSONObject> getLeetCodeProblemList() throws IOException, JSONException {
        String apiUrl = "https://leetcode.com/api/problems/algorithms/";
        String json = Jsoup.connect(apiUrl).ignoreContentType(true).execute().body();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray problemsArray = jsonObject.getJSONArray("stat_status_pairs");

        List<JSONObject> freeProblems = new ArrayList<>();
        for (int i = 0; i < problemsArray.length(); i++) {
            JSONObject problem = problemsArray.getJSONObject(i);
            if (!problem.getBoolean("paid_only")) {
                freeProblems.add(problem);
            }
        }

        return freeProblems;
    }//getLeetCodeProblemList()

    public ProblemInfo scrapeLeetCodeProblemContents(JSONObject problem) throws JSONException {
        String PROBLEMS_BASE_URL = "https://leetcode.com/problems/";
        ProblemInfo problemInfo = ProblemInfo.createProblemInfo();

        try {
            // problemInfo.setId(problem.getJSONObject("stat").getInt("question_id"));
            problemInfo.setSlug(problem.getJSONObject("stat").getString("question__title_slug"));
            problemInfo.setTitle(problem.getJSONObject("stat").getString("question__title"));
            problemInfo.setLevel(problem.getJSONObject("difficulty").getInt("level"));

            long acs = problem.getJSONObject("stat").getLong("total_acs");
            long submitted = problem.getJSONObject("stat").getLong("total_submitted");
            problemInfo.setCorrectRate(calCorrectRate(acs, submitted));

            LeetcodeScraper.driver.get(PROBLEMS_BASE_URL + problemInfo.getSlug());
            new WebDriverWait(LeetcodeScraper.driver, Duration.ofSeconds(20)).until(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-track-load='description_content']"))
            );

            Document doc = Jsoup.parse(LeetcodeScraper.driver.getPageSource());
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
            List<ExampleResult> exlist = getExamples(preData);
            problemInfo.setExampleResults(exlist);

            List<String> tags = getTags();
            problemInfo.setTags(tags);

            return problemInfo;
        } catch (NoSuchElementException e) {
            return null;
        } catch (WebDriverException e) {
            // Almost errors are reported.
            return null;
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
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

    public static List<ExampleResult> getExamples(Elements preData) {
        List<ExampleResult> exlist = new ArrayList<>();

        for (Element pre : preData) {
            String preText = pre.text().trim();
            Pattern pattern = Pattern.compile("Input:(.*?)Output:(.*?)Explanation:(.*)", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(preText);

            if (matcher.find()) {
                ExampleResult ex = new ExampleResult();

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

    public static void main(String[] args) {
        LeetcodeScraper scraper = new LeetcodeScraper();
        int count = 0;

        try {
            List<JSONObject> problems = scraper.getLeetCodeProblemList();
            for (JSONObject p : problems) {
                ProblemInfo pdata = scraper.scrapeLeetCodeProblemContents(p);
                if(pdata != null) {
                    // Database insert 로직 필요
                    // Entity, DTO 확인할 것.
                }

                if (count % 25 == 0) {
                    driver.quit();
                    scraper = new LeetcodeScraper();
                    sleep(120);
                } else {
                    sleep(10);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }//main()
}



