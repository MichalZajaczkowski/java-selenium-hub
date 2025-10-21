package pl.devqa.javaseleniumhub.tests.smoke;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.devqa.javaseleniumhub.base.BaseTest;
import pl.devqa.javaseleniumhub.pages.HomePage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class HomeSmokeTest extends BaseTest {

    @Test(groups = {"smoke"}, description = "TC0: Verify that Home Page of AutomationExercise is loaded")
    @Description("Test verifies that the home page of Automation Exercise loads correctly by checking title.")
    @Severity(SeverityLevel.CRITICAL)
    public void tc0_verifyHomePageLoaded() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageLoaded(), "Home page did not load as expected");
    }

    @Test(groups = {"smoke"}, description = "TC0a: Verify Home Page logo is displayed")
    @Description("Test verifies that the home page of AutomationExercise displays the site logo or main header element")
    @Severity(SeverityLevel.NORMAL)
    public void tc0a_verifyHomePageLogoDisplayed() {
        HomePage homePage = new HomePage(driver);

        // Weryfikacja, że logo jest widoczne
        Assert.assertTrue(homePage.isLogoDisplayed(), "Home page logo is not visible, page may not have loaded correctly");
    }
}
