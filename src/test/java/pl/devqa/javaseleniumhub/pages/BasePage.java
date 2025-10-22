package pl.devqa.javaseleniumhub.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pl.devqa.javaseleniumhub.utils.WaitUtils;

public class BasePage {
    private final WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Lokator logotypu (sprawdź w DevTools – najczęściej <div class="logo"> img)
    @FindBy(css = "div.logo img")
    private WebElement siteLogo;

    // Górna nawigacja (ul.navbar-nav) – sanity check, że UI się zrenderował
    @FindBy(css = "ul.navbar-nav")
    private WebElement topNav;

    // Link 'Signup / Login' (na navbarze)
    @FindBy(css = "a[href='/login']")
    private WebElement signupLoginLink;

    @Step("Weryfikuję, że BasePage jest załadowany (tytuł zawiera 'Automation Exercise')")
    public boolean isBasePageLoaded() {
        return WaitUtils.titleContains(driver, "Automation Exercise");
    }

    @Step("Sprawdzam widoczność logotypu na BasePage")
    public boolean isLogoDisplayed() {
        return WaitUtils.visible(driver, siteLogo).isDisplayed();
    }

    @Step("Sprawdzam widoczność górnej nawigacji")
    public boolean isTopNavVisible() {
        return WaitUtils.visible(driver, topNav).isDisplayed();
    }

    @Step("Klikam 'Signup / Login' na BasePage")
    public void goToSignupLogin() {
        WaitUtils.visible(driver, topNav); // sanity – nawigacja widoczna
        WaitUtils.visible(driver, signupLoginLink).click();
    }
}
