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
import pl.devqa.javaseleniumhub.pages.components.HeaderNav;

/**
 * TC2: Login User with correct email and password
 * Cel: pozytywne logowanie istniejącego użytkownika i weryfikacja headera.
 *
 * Precond: konto istnieje (dostarcz parametry: -DloginEmail=... -DloginPassword=...),
 *          albo użyj domyślnej pary z DataFactory.existingUserTC2().
 */
public class LoginUserPositiveTest extends BaseTest {

    @Test(groups = {"smoke"}, description = "TC2: Pozytywne logowanie i weryfikacja 'Logged in as ...' + 'Delete Account'")
    @Description("Wchodzi na /login, loguje poprawnym emailem/hasłem i sprawdza nagłówek 'Logged in as {name}' oraz obecność linku 'Delete Account'.")
    @Severity(SeverityLevel.CRITICAL)
    public void tc2_loginUserWithCorrectCredentials() {
        // 1) Wejście bezpośrednio na /login (Home ładuje się w BaseTest; tu idziemy na ekran logowania):
        driver.navigate().to(AppConfig.BASE_URL + UrlPaths.LOGIN);

        // 2) Dane logowania – z fabryki (parametryzowane przez -DloginEmail/-DloginPassword)
        Credentials creds = DataFactory.existingUserTC2();

        // 3) Logowanie (sekcja 'Login to your account')
        SignupLoginPage loginPage = new SignupLoginPage(driver);
        HeaderNav header = loginPage
                .fillLogin(creds.email(), creds.password())
                .submitLogin();

        // 4) Asercje w headerze: 'Logged in as {name}' + link 'Delete Account'
        // Uwaga: 'name' do weryfikacji – zależy jak nazwa konta jest wyświetlana.
        // Jeśli znasz wyświetlaną nazwę, podstaw ją wprost. W przypadku konta z TC1: user.name().
        // Dla TC2 (istniejący user) możesz skonfigurować -DdisplayName=... i pobrać przez System.getProperty.
        String expectedDisplayName = System.getProperty("displayName", "User"); // dopasuj do swojego konta
        Assert.assertTrue(header.isLoggedInAsVisible(expectedDisplayName),
                "Header nie zawiera 'Logged in as " + expectedDisplayName + "'");

        Assert.assertTrue(header.isDeleteAccountPresent(),
                "Brak linku 'Delete Account' w headerze");
    }
}
