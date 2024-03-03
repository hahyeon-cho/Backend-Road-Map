package ncnk.make.backendroadmap.domain.utils;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.Semaphore;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class WebDriverPool {
    private final Semaphore semaphore;
    private final BlockingQueue<WebDriver> pool;
    private final int poolSize = 5;

    @Autowired
    private ApplicationContext applicationContext;

//    public WebDriverPool(@Value("${webdriver.pool.size:5}") int poolSize) {
    public WebDriverPool() {
        this.semaphore = new Semaphore(this.poolSize);
        this.pool = new LinkedBlockingQueue<>(this.poolSize);
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < this.poolSize; i++) {
            pool.offer(applicationContext.getBean(WebDriver.class));
        }
    }


    public WebDriver getDriver() throws InterruptedException {
        semaphore.acquire();
        return pool.take();
    }

    public void returnDriver(WebDriver driver) {
        boolean driverAdded = false;

        synchronized (this) {
            if (isDriverValid(driver)) {
                driverAdded = pool.offer(driver);
            } else {
                driver.quit();
                WebDriver newDriver = applicationContext.getBean(WebDriver.class);
                driverAdded = pool.offer(newDriver);
            }
        }

        if (driverAdded) {
            semaphore.release();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private boolean isDriverValid(WebDriver driver) {
        try {
            String title = driver.getTitle();
            return !title.isEmpty() && !title.equals("Page Not Found - LeetCode");
        } catch (Exception e) {
            return false;
        }
    }

    @PreDestroy
    public void closeAllDrivers() {
        while (!pool.isEmpty()) {
            WebDriver driver = pool.poll();
            if (driver != null) {
                driver.quit();
            }
        }
    }
}