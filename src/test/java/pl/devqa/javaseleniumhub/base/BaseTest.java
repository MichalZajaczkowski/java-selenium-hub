package pl.devqa.javaseleniumhub.base;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pl.devqa.javaseleniumhub.config.UrlPaths;
import pl.devqa.javaseleniumhub.driver.DriverFactory;
import pl.devqa.javaseleniumhub.config.AppConfig;
import pl.devqa.javaseleniumhub.utils.AllureAttachments;
import pl.devqa.javaseleniumhub.utils.CookieBannerUtils;

public class BaseTest {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        DriverFactory.initDriver();
        driver = DriverFactory.getDriver();
        driver.get(AppConfig.BASE_URL + UrlPaths.ROOT);
        CookieBannerUtils.acceptCookieBannerIfPresent(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            int s = result.getStatus();
            if (s == ITestResult.FAILURE || s == ITestResult.SKIP) {
                AllureAttachments.screenshot();
                AllureAttachments.pageSource();
                AllureAttachments.url();
                AllureAttachments.title();
            }
        } finally {
            log.info("Zamykam przeglądarkę");
            DriverFactory.quitDriver();
        }
    }
}