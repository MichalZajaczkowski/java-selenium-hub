package pl.devqa.javaseleniumhub.listeners;

import io.qameta.allure.Allure;
import org.testng.*;
import pl.devqa.javaseleniumhub.utils.AllureAttachments;

/**
 * Łapie błędy/skipy konfiguracji (@Before/@After), gdzie @AfterMethod może się nie wykonać.
 * Dołącza artefakty, jeśli to możliwe.
 */
public class AllureTestListener implements ITestListener, IConfigurationListener {

    // Konfiguracja (fixtures)
    @Override public void onConfigurationFailure(ITestResult itr) { attachConfig("CONFIG_FAIL", itr); }
    @Override public void onConfigurationSkip(ITestResult itr)    { attachConfig("CONFIG_SKIP", itr); }
    @Override public void onConfigurationSuccess(ITestResult itr) {}

    private void attachConfig(String reason, ITestResult result) {
        Allure.addAttachment("Status", "text/plain", reason);
        // Jeśli driver istnieje, poniższe załączniki się dodadzą; jeśli nie — będą placeholdery.
        AllureAttachments.screenshot();
        AllureAttachments.pageSource();
        AllureAttachments.url();
        AllureAttachments.title();
        Throwable t = result.getThrowable();
        Allure.addAttachment("Throwable", "text/plain", t == null ? "<no-exception>" : t.toString());
    }

    // Pozostałe ITestListener nie są potrzebne (obsługę fail/skip przejął @AfterMethod)
    @Override public void onTestStart(ITestResult r) {}
    @Override public void onTestSuccess(ITestResult r) {}
    @Override public void onTestFailure(ITestResult r) {}
    @Override public void onTestFailedWithTimeout(ITestResult r) {}
    @Override public void onTestSkipped(ITestResult r) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult r) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}

