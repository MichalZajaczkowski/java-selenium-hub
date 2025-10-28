package pl.devqa.javaseleniumhub.tests.smoke;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.devqa.javaseleniumhub.base.BaseTest;
import pl.devqa.javaseleniumhub.config.AppConfig;
import pl.devqa.javaseleniumhub.config.UrlPaths;
import pl.devqa.javaseleniumhub.data.Credentials;
import pl.devqa.javaseleniumhub.data.DataFactory;
import pl.devqa.javaseleniumhub.pages.SignupLoginPage;

/**
 * TC3: Login User with incorrect email and password
 * Cel: potwierdzić, że dla niepoprawnych danych logowania system wyświetla komunikat:
 * 'Your email or password is incorrect!' i nie loguje użytkownika.
 *
 * KROKI (wg oficjalnych Test Cases):
 * 1) Launch browser
 * 2) Navigate to url 'http://automationexercise.com'
 * 3) Verify home page is visible successfully
 * 4) Click on 'Signup / Login'
 * 5) Verify 'Login to your account' is visible
 * 6) Enter incorrect email address and password
 * 7) Click 'login'
 * 8) Verify error 'Your email or password is incorrect!' is visible
 */
public class LoginUserNegativeTest extends BaseTest {

    @Test(groups = {"smoke", "regression"},
            description = "TC3: Błędne logowanie – oczekujemy komunikatu o niepoprawnych danych")
    @Description("Weryfikuje negatywną ścieżkę logowania: po podaniu błędnego email/hasła strona wyświetla komunikat 'Your email or password is incorrect!'.")
    @Severity(SeverityLevel.CRITICAL)
    public void tc3_loginUserWithIncorrectCredentials() {
        // 1-2) Start w BaseTest (Home + cookies); przechodzimy bezpośrednio do /login
        driver.navigate().to(AppConfig.BASE_URL + UrlPaths.LOGIN);

        // 5) Sekcja 'Login to your account' powinna być widoczna
        SignupLoginPage page = new SignupLoginPage(driver);
        Assert.assertTrue(page.isLoginSectionVisible(),
                "'Login to your account' nie jest widoczne (niespełniony krok TC3)");

        // 6) Niepoprawne dane logowania – z fabryki (deterministycznie lub losowo)
        Credentials wrong = DataFactory.invalidCredentials();

        // 7) Klik 'Login'
        page.fillLogin(wrong.email(), wrong.password())
                .submitLogin();

        // 8) Oczekujemy błędu 'Your email or password is incorrect!'
        Assert.assertTrue(page.isIncorrectCredentialsErrorVisible(),
                "Brak komunikatu 'Your email or password is incorrect!' dla błędnych danych logowania");
    }
}
