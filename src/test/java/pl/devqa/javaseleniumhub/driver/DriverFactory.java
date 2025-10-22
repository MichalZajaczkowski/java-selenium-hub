package pl.devqa.javaseleniumhub.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import java.time.Duration;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> TL_DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static WebDriver getDriver() {
        return TL_DRIVER.get();
    }

    public static void initDriver() {
        if (TL_DRIVER.get() != null) return;

        String browser = System.getProperty("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        WebDriver driver = createDriver(browser, headless);
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        TL_DRIVER.set(driver);
    }

    public static WebDriver createDriver(String browser, boolean headless) {
        WebDriver driver;
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ffOptions = new FirefoxOptions();
                if (headless) {
                    ffOptions.addArguments("--headless=new");
                }
                driver = new FirefoxDriver(ffOptions);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                }
                driver = new EdgeDriver(edgeOptions);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }
                chromeOptions.addArguments("--window-size=1920,1080");
                driver = new ChromeDriver(chromeOptions);
                break;
        }
        TL_DRIVER.set(driver);
        return driver;
    }

    public static void quitDriver() {
        if (TL_DRIVER.get() != null) {
            TL_DRIVER.get().quit();
            TL_DRIVER.remove();
        }
    }
}
