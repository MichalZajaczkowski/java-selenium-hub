package pl.devqa.javaseleniumhub.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pl.devqa.javaseleniumhub.config.UrlPaths;
import pl.devqa.javaseleniumhub.driver.DriverFactory;
import pl.devqa.javaseleniumhub.config.AppConfig;
import pl.devqa.javaseleniumhub.utils.CookieBannerUtils;

public class BaseTest {

    protected WebDriver driver;

    @Parameters({"browser","headless"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser,
                      @Optional("false") String headless) {
        boolean isHeadless = Boolean.parseBoolean(headless);
        DriverFactory.initDriver(browser, isHeadless);
        driver = DriverFactory.getDriver();
        driver.get(AppConfig.BASE_URL + UrlPaths.ROOT);
        CookieBannerUtils.acceptCookieBannerIfPresent(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
