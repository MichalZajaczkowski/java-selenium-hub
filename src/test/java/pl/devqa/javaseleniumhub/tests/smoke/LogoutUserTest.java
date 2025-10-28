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
 * TC4: Logout User
 * Cel: po poprawnym logowaniu kliknąć 'Logout' i potwierdzić, że wróciliśmy do
 * ekranu logowania ('Login to your account' jest widoczne).
 *
 * Precond: konto istnieje – dostarcz parametry -DloginEmail/-DloginPassword
 * (lub użyj domyślnych w DataFactory.existingUserTC2()).
 */
public class LogoutUserTest extends BaseTest {

    @Test(groups = {"smoke"}, description = "TC4: Logout – powrót na ekran logowania")
    @Description("Loguje użytkownika, klika 'Logout' i weryfikuje, że widoczny jest nagłówek 'Login to your account' na stronie /login.")
    @Severity(SeverityLevel.CRITICAL)
    public void tc4_logoutUser() {
        // 1) Wejście bezpośrednio na /login
        driver.navigate().to(AppConfig.BASE_URL + UrlPaths.LOGIN);

        // 2) Pozytywne logowanie (jak w TC2) – dane z fabryki
        Credentials creds = DataFactory.existingUserTC2();
        SignupLoginPage loginPage = new SignupLoginPage(driver);
        HeaderNav header = loginPage
                .fillLogin(creds.email(), creds.password())
                .submitLogin();

        // 3) Weryfikacje po zalogowaniu (opcjonalnie – pomagają w przejrzystości)
        Assert.assertTrue(header.isLogoutPresent(), "Brak linku 'Logout' w headerze.");
        // 8) Weryfikacja 'Logged in as {name}' – jeśli masz znaną nazwę, ustaw -DdisplayName
        String expectedDisplayName = System.getProperty("displayName", "User");

        Assert.assertTrue(header.isLoggedInAsVisible(expectedDisplayName),
                "Header nie zawiera 'Logged in as " + expectedDisplayName + "'");
        // 4) Klik 'Logout' → powinniśmy być na ekranie logowania
        SignupLoginPage loginAfterLogout = header.clickLogout();

        // 5) Weryfikacja: 'Login to your account' jest widoczne + URL wskazuje na /login
        Assert.assertTrue(loginAfterLogout.isLoginSectionVisible(),
                "'Login to your account' nie jest widoczne po wylogowaniu.");

        Assert.assertTrue(driver.getCurrentUrl().contains(UrlPaths.LOGIN),
                "Po wylogowaniu URL nie wskazuje na '/login'. Aktualny: " + driver.getCurrentUrl());
    }
}
