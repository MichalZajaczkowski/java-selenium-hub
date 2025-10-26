package pl.devqa.javaseleniumhub.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pl.devqa.javaseleniumhub.pages.components.HeaderNav;

/**
 * Ekran Signup/Login – obsługuje:
 *  - sekcję 'New User Signup!' (TC1)
 *  - sekcję 'Login to your account' (TC2/TC4)
 * Rola: enkapsuluje interakcje formularzy, by test pozostał „thin”.
 */
public class SignupLoginPage {

    private final WebDriver driver;

    // --- NEW USER SIGNUP! (TC1) ---
    @FindBy(css = "input[data-qa='signup-name']")
    private WebElement signupNameInput;

    @FindBy(css = "input[data-qa='signup-email']")
    private WebElement signupEmailInput;

    @FindBy(css = "button[data-qa='signup-button']")
    private WebElement signupButton;

    // --- LOGIN TO YOUR ACCOUNT (TC2/TC4) ---
    @FindBy(css = "input[data-qa='login-email']")
    private WebElement loginEmailInput;

    @FindBy(css = "input[data-qa='login-password']")
    private WebElement loginPasswordInput;

    @FindBy(css = "button[data-qa='login-button']")
    private WebElement loginButton;

    public SignupLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ---------------- TC1 (pozostawiamy dla kompletności) ----------------
    @Step("Wprowadź dane do 'New User Signup!': {name}, {email}")
    public SignupLoginPage fillNewUser(String name, String email) {
        signupNameInput.clear();
        signupNameInput.sendKeys(name);
        signupEmailInput.clear();
        signupEmailInput.sendKeys(email);
        return this;
    }

    @Step("Klik 'Signup'")
    public RegisterAccountPage submitSignup() {
        signupButton.click();
        return new RegisterAccountPage(driver);
    }

    // ---------------- TC2 (tu nowość) ----------------
    /** Wypełnia część 'Login to your account'. */
    @Step("Wpisz dane logowania: {email} / ******")
    public SignupLoginPage fillLogin(String email, String password) {
        loginEmailInput.clear();
        loginEmailInput.sendKeys(email);
        loginPasswordInput.clear();
        loginPasswordInput.sendKeys(password);
        return this;
    }

    /** Zatwierdza logowanie i zwraca komponent headera (po poprawnym logowaniu). */
    @Step("Klik 'Login'")
    public HeaderNav submitLogin() {
        loginButton.click();
        return new HeaderNav(driver);
    }
}
