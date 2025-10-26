package pl.devqa.javaseleniumhub.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pl.devqa.javaseleniumhub.pages.components.HeaderNav;

/** Strona 'ACCOUNT CREATED!' – potwierdzenie utworzenia konta. */
public class AccountCreatedPage {

    private final WebDriver driver;

    @FindBy(css = "h2[data-qa='account-created']")
    private WebElement accountCreatedHeader;

    @FindBy(css = "a[data-qa='continue-button'], button[data-qa='continue-button']")
    private WebElement continueBtn;

    public AccountCreatedPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /** Czy widzimy nagłówek 'ACCOUNT CREATED!' */
    @Step("Weryfikacja: 'ACCOUNT CREATED!' widoczne")
    public boolean isAccountCreatedVisible() {
        return accountCreatedHeader.isDisplayed();
    }

    /** Klik 'Continue' – powrót do strony z headerem 'Logged in as ...'. */
    @Step("Klik 'Continue'")
    public HeaderNav continueToLoggedInHome() {
        continueBtn.click();
        return new HeaderNav(driver);
    }
}
