package ncnk.make.backendroadmap.domain.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

// LeetCode 스크래핑용 Chrome
@Configuration
public class WebDriverConfig {

    @Value("${leetcode.proxy.enabled:false}")
    private boolean proxyEnabled;
    @Value("${leetcode.proxy.host:}")
    private String proxyHost;
    @Value("${leetcode.proxy.port:0}")
    private int proxyPort;

    @Bean(destroyMethod = "quit")
    @Scope("prototype")
    public WebDriver webDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--log-level=3");

        // 봇 탐지 완화 설정
        // - User-Agent를 실제 브라우저처럼 위장
        // - Selenium 자동화 흔적(AutomationControlled, enable-automation, 확장 기능) 제거
        options.addArguments(
            "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
        );
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);

        // 프록시 설정: IP 제한 회피
        if (proxyEnabled && proxyHost != null && !proxyHost.isBlank() && proxyPort > 0) {
            options.addArguments("--proxy-server=http://" + proxyHost + ":" + proxyPort);
        }

        return new ChromeDriver(options);
    }
}