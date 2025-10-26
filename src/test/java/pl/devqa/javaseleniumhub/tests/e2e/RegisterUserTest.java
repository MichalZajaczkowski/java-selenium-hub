package pl.devqa.javaseleniumhub.tests.e2e;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.devqa.javaseleniumhub.base.BaseTest;
import pl.devqa.javaseleniumhub.data.DataFactory;
import pl.devqa.javaseleniumhub.data.UserData;
import pl.devqa.javaseleniumhub.pages.*;
import pl.devqa.javaseleniumhub.pages.components.HeaderNav;

/**
 * TC1: Register User
 * Flow:
 * Home -> Signup/Login -> Enter Account Information -> Create Account -> Account Created -> Continue
 * -> Header: 'Logged in as {name}' + obecny link 'Delete Account'
 */
public class RegisterUserTest extends BaseTest {

    @Test(groups = {"e2e"}, description = "TC1: Rejestracja nowego użytkownika i weryfikacja headera")
    @Description("Tworzy nowego użytkownika, potwierdza 'ACCOUNT CREATED!', następnie 'Logged in as {name}' i link 'Delete Account' w headerze.")
    @Severity(SeverityLevel.CRITICAL)
    public void tc1_registerUser() {
        // 1) Start na Home
        HomePage home = new HomePage(driver);
        Assert.assertTrue(home.isLoaded(), "Home page nie jest załadowana");

        // 2) Dane testowe (poza testem – DataFactory)
        UserData user = DataFactory.newUser();

        // 3) Signup/Login -> wprowadź 'New User Signup!' -> Signup
        SignupLoginPage signup = home.goToSignupLogin()
                .fillNewUser(user.name(), user.email());
        RegisterAccountPage register = signup.submitSignup();

        register.verifyNameAndEmail(user.name(), user.email());
        // 4) Wypełnij formularz rejestracji (Enter Account Information + Address) -> Create Account
        register.fillAccountInformation(user)
                .fillAddressInformation(user);
        AccountCreatedPage created = register.submitCreateAccount();

        // 5) 'ACCOUNT CREATED!' + Continue
        Assert.assertTrue(created.isAccountCreatedVisible(), "'ACCOUNT CREATED!' nie jest widoczne");
        HeaderNav header = created.continueToLoggedInHome();

        // 6) Header: 'Logged in as {name}' + obecny link 'Delete Account'
        Assert.assertTrue(header.isLoggedInAsVisible(user.name()),
                "Header nie zawiera 'Logged in as " + user.name() + "'");

        Assert.assertTrue(header.isDeleteAccountPresent(),
                "Brak linku 'Delete Account' w headerze");

        header.clickDeleteAccount();
        Assert.assertTrue(new AccountDeletedPage(driver).isAccountDeletedVisible(),
                "'ACCOUNT DELETED!' nie jest widoczne po usunięciu konta");

    }


}
