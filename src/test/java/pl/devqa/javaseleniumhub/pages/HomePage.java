package pl.devqa.javaseleniumhub.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Objects;

/**
 * Reprezentuje stronę główną AutomationExercise – punkt startowy TC1.
 */
public class HomePage {

    private final WebDriver driver;

    // Link w headerze do strony logowania/rejestracji
    @FindBy(css = "a[href='/login']")
    private WebElement signupLoginLink;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Weryfikuje, że strona główna jest załadowana (prosta asercja po tytule).
     */
    @Step("Weryfikacja: strona główna załadowana")
    public boolean isLoaded() {
        // Prosto i stabilnie – tytuł zawiera 'Automation Exercise'
        return Objects.requireNonNull(driver.getTitle()).contains("Automation Exercise");
    }

    /**
     * Przejście do ekranu Signup/Login. Używana w TC1 jako pierwszy krok.
     */
    @Step("Przejście do: Signup / Login")
    public SignupLoginPage goToSignupLogin() {
        signupLoginLink.click();
        return new SignupLoginPage(driver);
    }
}
