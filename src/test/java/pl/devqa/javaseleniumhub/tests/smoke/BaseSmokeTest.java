package pl.devqa.javaseleniumhub.tests.smoke;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.devqa.javaseleniumhub.base.BaseTest;
import pl.devqa.javaseleniumhub.pages.BasePage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class BaseSmokeTest extends BaseTest {

    @Test(groups = {"smoke"}, description = "TC0: Verify that Base Page of AutomationExercise is loaded")
    @Description("Test verifies that the Base page of Automation Exercise loads correctly by checking title.")
    @Severity(SeverityLevel.CRITICAL)
    public void tc0_verifyBasePageLoaded() {
        BasePage basePage = new BasePage(driver);
        Assert.assertTrue(basePage.isBasePageLoaded(), "Base page did not load as expected");
    }

    @Test(groups = {"smoke"}, description = "TC0a: Verify Base Page logo is displayed")
    @Description("Test verifies that the Base page of AutomationExercise displays the site logo or main header element")
    @Severity(SeverityLevel.NORMAL)
    public void tc0a_verifyBasePageLogoDisplayed() {
        BasePage basePage = new BasePage(driver);

        // Weryfikacja, że logo jest widoczne
        Assert.assertTrue(basePage.isLogoDisplayed(), "Base page logo is not visible, page may not have loaded correctly");
    }
}
