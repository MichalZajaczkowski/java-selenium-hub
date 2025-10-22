package pl.devqa.javaseleniumhub.utils;

import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import pl.devqa.javaseleniumhub.driver.DriverFactory;

public final class AllureAttachments {
    private AllureAttachments() {
    }

    private static WebDriver drv() {
        try {
            return DriverFactory.getDriver();
        } catch (Throwable t) {
            return null;
        }
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] screenshot() {
        try {
            WebDriver d = drv();
            if (d instanceof TakesScreenshot) {
                return ((TakesScreenshot) d).getScreenshotAs(OutputType.BYTES);
            }
        } catch (Throwable ignored) {
        }
        return new byte[0];
    }

    @Attachment(value = "Page Source", type = "text/html", fileExtension = ".html")
    public static String pageSource() {
        try {
            WebDriver d = drv();
            if (d != null) return d.getPageSource();
        } catch (Throwable ignored) {
        }
        return "<no-page-source>";
    }

    @Attachment(value = "URL", type = "text/plain")
    public static String url() {
        try {
            WebDriver d = drv();
            return d != null ? d.getCurrentUrl() : "<no-driver>";
        } catch (Throwable ignored) {
            return "<no-driver>";
        }
    }

    @Attachment(value = "Title", type = "text/plain")
    public static String title() {
        try {
            WebDriver d = drv();
            return d != null ? d.getTitle() : "<no-driver>";
        } catch (Throwable ignored) {
            return "<no-driver>";
        }
    }
}
