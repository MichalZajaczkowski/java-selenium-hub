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
 * TC5: Register User with existing email
 * Cel: Wpisanie istniejącego e-maila w sekcji 'New User Signup!' i weryfikacja błędu:
 * 'Email Address already exist!'.
 */
public class RegisterUserWithExistingEmailTest extends BaseTest {

    @Test(groups = {"smoke"}, description = "TC5: Rejestracja na istniejący e-mail powinna zwrócić błąd")
    @Description("Na /login w sekcji 'New User Signup!' podaje istniejący e-mail i sprawdza komunikat 'Email Address already exist!'.")
    @Severity(SeverityLevel.NORMAL)
    public void tc5_registerUserWithExistingEmail() {
        // 1-3) Start w BaseTest (Home + cookies); przejście na /login
        driver.navigate().to(AppConfig.BASE_URL + UrlPaths.LOGIN);

        SignupLoginPage page = new SignupLoginPage(driver);

        // 5) 'New User Signup!' jest widoczne (wymóg TC5)
        Assert.assertTrue(page.isNewUserSignupVisible(),
                "'New User Signup!' nie jest widoczne – niespełniony krok TC5");

        // 6) Weź istniejącego usera (np. z parametrów -DloginEmail/-DloginPassword)
        Credentials existing = DataFactory.existingUserTC2();
        String displayName = System.getProperty("displayName", "Existing User");

        // 6-7) Wprowadź imię + istniejący e-mail i kliknij 'Signup'
        page.fillNewUser(displayName, existing.email())
                .submitSignup();

        // 8) Oczekujemy błędu 'Email Address already exist!'
        Assert.assertTrue(page.isExistingEmailErrorVisible(),
                "Brak komunikatu 'Email Address already exist!' dla istniejącego e-maila");
    }
}