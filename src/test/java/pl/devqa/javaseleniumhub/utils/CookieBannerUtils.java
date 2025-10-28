package pl.devqa.javaseleniumhub.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.devqa.javaseleniumhub.config.AppConfig;

public final class CookieBannerUtils {

    private static final By BANNER_CONTAINER = By.cssSelector("div.fc-dialog-container");
    private static final By ACCEPT_BUTTON = By.cssSelector("button.fc-cta-consent");

    private CookieBannerUtils() {
        // prywatny konstruktor – klasa util, nie tworzymy instancji
    }

    /**
     * Sprawdza, czy baner cookies jest widoczny na stronie.
     *
     * @param driver instancja WebDriver
     * @return true jeśli baner jest wyświetlony i widoczny, w przeciwnym razie false
     */
    public static boolean isCookieBannerVisible(WebDriver driver) {
        try {
            WebElement banner = driver.findElement(BANNER_CONTAINER);
            return banner.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Jeśli baner cookies jest obecny — akceptuje go i czeka aż przestanie być widoczny.
     *
     * @param driver instancja WebDriver
     */
    @Step("Akceptacja banera cookies (jeśli obecny)")
    public static void acceptCookieBannerIfPresent(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, AppConfig.DEFAULT_TIMEOUT);
        if (isCookieBannerVisible(driver)) {
            WebElement acceptBtn = wait.until(ExpectedConditions.elementToBeClickable(ACCEPT_BUTTON));
            acceptBtn.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(BANNER_CONTAINER));
        }
    }
}
