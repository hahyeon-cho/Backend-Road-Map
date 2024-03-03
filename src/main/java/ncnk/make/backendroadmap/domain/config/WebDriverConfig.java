package ncnk.make.backendroadmap.domain.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Value;
import ncnk.make.backendroadmap.domain.utils.WebDriverPool;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class WebDriverConfig {
    @Bean(destroyMethod = "quit")
    @Scope("prototype")
    public WebDriver webDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--log-level=3");
        return new ChromeDriver(options);
    }
}
