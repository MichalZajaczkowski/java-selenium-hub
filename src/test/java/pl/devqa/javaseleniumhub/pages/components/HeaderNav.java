package pl.devqa.javaseleniumhub.pages.components;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pl.devqa.javaseleniumhub.pages.SignupLoginPage;

/** Komponent Header/Nav – asercja 'Logged in as ...', link 'Delete Account'. */
public class HeaderNav {

    private final WebDriver driver;

    // 'Logged in as username' – zwykle <a><i class="fa fa-user"></i> Logged in as {name}</a>
    @FindBy(xpath = "//div[contains(@class,'header-middle')]//ul[contains(@class,'nav')]/li/a[contains(.,'Logged in as')]")
    private WebElement loggedInAsLink;

    // Link 'Delete Account' – <a href="/delete_account">Delete Account</a>
    @FindBy(css = "div.header-middle ul.nav.navbar-nav a[href='/delete_account']")
    private WebElement deleteAccountLink;

    // 'Logout'
    @FindBy(css = "div.header-middle ul.nav.navbar-nav a[href='/logout']")
    private WebElement logoutLink;

    public HeaderNav(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /** Weryfikuje 'Logged in as {expectedName}' w headerze. */
    @Step("Weryfikacja 'Logged in as {expectedName}' w headerze")
    public boolean isLoggedInAsVisible(String expectedName) {
        String text = loggedInAsLink.getText().trim();
        return text.contains("Logged in as") && text.contains(expectedName);
    }

    /** Czy link 'Delete Account' jest obecny i widoczny. */
    @Step("Weryfikacja obecności linku 'Delete Account'")
    public boolean isDeleteAccountPresent() {
        return deleteAccountLink.isDisplayed();
    }

    /** Czy link 'Logout' jest widoczny. */
    @Step("Weryfikacja obecności linku 'Logout'")
    public boolean isLogoutPresent() {
        return logoutLink.isDisplayed();
    }

    /** Klik 'Delete Account' – przejście do procesu kasowania konta. */
    @Step("Klik 'Delete Account'")
    public void clickDeleteAccount() {
        deleteAccountLink.click();
    }

    @Step("Klik 'Logout'")
    public SignupLoginPage clickLogout() {
        logoutLink.click();
        return new SignupLoginPage(driver);
    }
}
