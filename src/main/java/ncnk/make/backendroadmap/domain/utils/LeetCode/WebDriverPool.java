package ncnk.make.backendroadmap.domain.utils.LeetCode;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class WebDriverPool {
    private final Semaphore semaphore;
    private final BlockingQueue<WebDriver> pool;
    private final ApplicationContext applicationContext;
    private static final AtomicInteger poolSize = new AtomicInteger(4);

    @Autowired
    public WebDriverPool(ApplicationContext applicationContext) {
        this.semaphore = new Semaphore(poolSize.get());
        this.pool = new LinkedBlockingQueue<>(poolSize.get());
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < poolSize.get(); i++) {
            pool.offer(applicationContext.getBean(WebDriver.class));
        }
    }


    public WebDriver getDriver() throws InterruptedException {
        semaphore.acquire();
        return pool.take();
    }

    public void returnDriver(WebDriver driver) {
        boolean driverAdded;

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
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private boolean isDriverValid(WebDriver driver) {
        return false;
//        try {
//            String title = driver.getTitle();
//            return !title.isEmpty() && !title.equals("Page Not Found - LeetCode");
//        } catch (Exception e) {
//            return false;
//        }
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