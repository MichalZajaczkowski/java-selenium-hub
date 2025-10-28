package pl.devqa.javaseleniumhub.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
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

    // --- Nagłówek sekcji logowania, do walidacji kroku z TC3/TC4 ---
    @FindBy(xpath = "//h2[normalize-space()='Login to your account']")
    private WebElement loginSectionHeader;

    // --- Komunikat błędu dla niepoprawnych danych logowania (TC3) ---
    @FindBy(xpath = "//p[contains(normalize-space(),\"Your email or password is incorrect!\")]")
    private WebElement incorrectCredentialsError;

    public SignupLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    // --- Nagłówek sekcji 'New User Signup!' (opcjonalna weryfikacja kroku 5 TC5) ---
    @FindBy(xpath = "//h2[normalize-space()='New User Signup!']")
    private WebElement newUserSignupHeader;

    // --- Komunikat błędu dla istniejącego e-maila ---
    @FindBy(xpath = "//p[contains(normalize-space(), 'Email Address already exist!')]")
    private WebElement existingEmailError;

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

    // ... (konstruktor i metody TC1/TC2)

    /** Weryfikuje, że sekcja 'Login to your account' jest widoczna. */
    @Step("Weryfikacja: 'Login to your account' jest widoczne")
    public boolean isLoginSectionVisible() {
        return loginSectionHeader.isDisplayed();
    }

    /** Zwraca true, gdy komunikat błędu niepoprawnych danych logowania jest widoczny. */
    @Step("Weryfikacja błędu: 'Your email or password is incorrect!'")
    public boolean isIncorrectCredentialsErrorVisible() {
        return incorrectCredentialsError.isDisplayed();
    }

    /** Weryfikuje, że sekcja 'New User Signup!' jest widoczna. */
    @Step("Weryfikacja: 'New User Signup!' jest widoczne")
    public boolean isNewUserSignupVisible() {
        return newUserSignupHeader.isDisplayed();
    }

    /** Weryfikuje widoczność błędu 'Email Address already exist!'. */
    @Step("Weryfikacja błędu: 'Email Address already exist!'")
    public boolean isExistingEmailErrorVisible() {
        return existingEmailError.isDisplayed();
    }

    /** Zwraca true, jeśli pole 'signup-email' przechodzi walidację HTML5 (validity.valid). */
    @Step("Sprawdź poprawność (HTML5) pola 'signup-email' dla wartości: {email}")
    public boolean isSignupEmailValid(String email) {
        signupEmailInput.clear();
        signupEmailInput.sendKeys(email);
        // Nie wypełniamy niczego więcej i nie submitujemy – tu tylko sprawdzamy stan pola.
        return (Boolean) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].validity.valid;", signupEmailInput);
    }

    /** Klik 'Signup' i zwróć komunikat walidacyjny HTML5 z pola e-mail (może być pusty, gdy valid). */
    @Step("Klik 'Signup' i odczytaj komunikat walidacyjny pola e-mail")
    public String clickSignupAndGetEmailValidationMessage(String name, String email) {
        signupNameInput.clear();
        signupNameInput.sendKeys(name);

        signupEmailInput.clear();
        signupEmailInput.sendKeys(email);

        signupButton.click(); // wywoła natywną walidację formularza

        // Zwracamy komunikat przeglądarki; jest zlokalizowany (PL/EN itp.).
        return (String) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].validationMessage;", signupEmailInput);
    }
}
