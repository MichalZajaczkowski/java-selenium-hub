package pl.devqa.javaseleniumhub.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.devqa.javaseleniumhub.config.AppConfig;
import java.time.Duration;

public final class WaitUtils {
    private WaitUtils() {}

    private static WebDriverWait wait(WebDriver driver) {
        return new WebDriverWait(driver, AppConfig.DEFAULT_TIMEOUT);
    }

    /** Czeka aż element będzie widoczny (By). Użycie: WaitUtils.visible(driver, locator). */
    public static WebElement visible(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /** Czeka aż element będzie widoczny (WebElement). */
    public static WebElement visible(WebDriver driver, WebElement element) {
        return wait(driver).until(ExpectedConditions.visibilityOf(element));
    }

    /** Czeka aż element będzie klikalny. */
    public static WebElement clickable(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    /** Czeka aż element zniknie z DOM lub przestanie być widoczny. */
    public static boolean invisible(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /** Czeka aż tytuł będzie zawierał tekst. */
    public static boolean titleContains(WebDriver driver, String text) {
        return wait(driver).until(ExpectedConditions.titleContains(text));
    }

    /** Czeka aż URL będzie zawierał ścieżkę. */
    public static boolean urlContains(WebDriver driver, String path) {
        return wait(driver).until(ExpectedConditions.urlContains(path));
    }

    /** Krótki waiter (np. 2 s) jako fallback – używaj oszczędnie. */
    public static WebDriverWait shortWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    // src/test/java/pl/devqa/javaseleniumhub/utils/WaitUtils.java
    public static WebElement presence(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static boolean attributeContains(WebDriver driver, WebElement el, String attr, String value) {
        return wait(driver).until(ExpectedConditions.attributeContains(el, attr, value));
    }

    public static boolean textPresentIn(WebDriver driver, WebElement el, String text) {
        return wait(driver).until(ExpectedConditions.textToBePresentInElement(el, text));
    }

}
