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

import java.util.List;

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

    @Test(groups = {"smoke"}, description = "TC5/neg: Walidacja HTML5 dla niepoprawnego e-maila w New User Signup!")
    @Description("Dla wartości typu 'ą', 'ę', 'test@.pl' klikamy 'Signup' i sprawdzamy, że pole e-mail jest niepoprawne oraz pojawia się komunikat validationMessage (niepusty).")
    @Severity(SeverityLevel.NORMAL)
    public void tc5_signupEmailClientSideValidation_invalidFormats() {
        // 1) Przejście na ekran logowania/rejestracji
        driver.navigate().to(AppConfig.BASE_URL + UrlPaths.LOGIN);
        SignupLoginPage page = new SignupLoginPage(driver);

        // 2) Zestaw niepoprawnych wartości (bez DataProvider – prosta pętla)
        List<String> invalidEmails = List.of("ą", "ę", "test@.pl", "test@", "@test.pl", "test");

        for (String email : invalidEmails) {
            // Ważne: nadajemy jakieś imię – formularz ma oba pola w sekcji New User Signup!
            String validation = page.clickSignupAndGetEmailValidationMessage("Any Name", email);

            // a) pole powinno być niepoprawne wg HTML5 (valid=false)
            boolean isValidNow = page.isSignupEmailValid(email);
            Assert.assertFalse(isValidNow, "Pole e-mail niespodziewanie valid=true dla: " + email);

            // b) przeglądarka powinna zwrócić komunikat walidacyjny (zlokalizowany, ale niepusty)
            Assert.assertFalse(validation == null || validation.isBlank(),
                    "Brak natywnego komunikatu walidacyjnego dla: " + email);

            // c) dodatkowa asekuracja: pozostajemy na /login (nie przeszliśmy do rejestracji)
            Assert.assertTrue(driver.getCurrentUrl().contains(UrlPaths.LOGIN),
                    "Dla niepoprawnego e-maila nie powinniśmy opuszczać /login; URL: " + driver.getCurrentUrl());
        }
    }
}