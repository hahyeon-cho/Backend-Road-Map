package ncnk.make.backendroadmap.domain.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WebDriverConfig.class)
@ActiveProfiles("test")
@Slf4j
public class WebDriverConfigTest {

    @Autowired
    private WebDriver driver;

    @Test
    public void webDriverNullTest() {
        log.info("Starting webDriverBeanShouldBeNotNull test");
        assertNotNull(driver, "The WebDriver bean should not be null");
    }

    @Test
    public void webDriverOpenPage() {
        log.info("Starting webDriverShouldOpenPage test");
        driver.get("https://www.naver.com");
        assertEquals("NAVER", driver.getTitle());
    }

    @AfterEach
    public void dropWebDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}