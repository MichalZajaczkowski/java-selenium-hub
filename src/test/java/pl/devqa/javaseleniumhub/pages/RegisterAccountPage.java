package pl.devqa.javaseleniumhub.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import pl.devqa.javaseleniumhub.data.UserData;

/**
 * Formularz 'ENTER ACCOUNT INFORMATION' + 'ADDRESS INFORMATION' (TC1).
 */
public class RegisterAccountPage {

    private final WebDriver driver;

    // --- ENTER ACCOUNT INFORMATION ---
    @FindBy(id = "id_gender1")
    private WebElement mrRadio;  // Mr
    @FindBy(id = "id_gender2")
    private WebElement mrsRadio; // Mrs
    @FindBy(id = "password")
    private WebElement passwordInput;
    @FindBy(id = "days")
    private WebElement daySelect;
    @FindBy(id = "months")
    private WebElement monthSelect;
    @FindBy(id = "years")
    private WebElement yearSelect;
    @FindBy(id = "newsletter")
    private WebElement newsletterChk;
    @FindBy(id = "optin")
    private WebElement offersChk;

    // --- ADDRESS INFORMATION ---
    @FindBy(id = "first_name")
    private WebElement firstNameInput;
    @FindBy(id = "last_name")
    private WebElement lastNameInput;
    @FindBy(id = "company")
    private WebElement companyInput;
    @FindBy(id = "address1")
    private WebElement address1Input;
    @FindBy(id = "address2")
    private WebElement address2Input;
    @FindBy(id = "country")
    private WebElement countrySelect;
    @FindBy(id = "state")
    private WebElement stateInput;
    @FindBy(id = "city")
    private WebElement cityInput;
    @FindBy(id = "zipcode")
    private WebElement zipcodeInput;
    @FindBy(id = "mobile_number")
    private WebElement mobileInput;

    @FindBy(css = "button[data-qa='create-account']")
    private WebElement createAccountBtn;

    public RegisterAccountPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Wypełnia sekcję ENTER ACCOUNT INFORMATION na podstawie UserData.
     */
    @Step("Wypełnij 'Enter Account Information'")
    public RegisterAccountPage fillAccountInformation(UserData data) {
        // Płeć – wybierz Mr (lub mrs, gdybyś dodał do danych). Tu dla uproszczenia Mr.
        mrRadio.click();
        passwordInput.sendKeys(data.password());

        new Select(daySelect).selectByVisibleText(data.day());
        new Select(monthSelect).selectByVisibleText(data.month());
        new Select(yearSelect).selectByVisibleText(data.year());

        setCheckbox(newsletterChk, data.newsletter());
        setCheckbox(offersChk, data.offers());
        return this;
    }

    /**
     * Wypełnia część adresową.
     */
    @Step("Wypełnij 'Address Information'")
    public RegisterAccountPage fillAddressInformation(UserData data) {
        firstNameInput.sendKeys(data.firstName());
        lastNameInput.sendKeys(data.lastName());
        companyInput.sendKeys(data.company());
        address1Input.sendKeys(data.address1());
        address2Input.sendKeys(data.address2());

        new Select(countrySelect).selectByVisibleText(data.country());
        stateInput.sendKeys(data.state());
        cityInput.sendKeys(data.city());
        zipcodeInput.sendKeys(data.zipcode());
        mobileInput.sendKeys(data.mobile());
        return this;
    }

    /**
     * Klik 'Create Account' – przejście do 'Account Created'.
     */
    @Step("Klik 'Create Account'")
    public AccountCreatedPage submitCreateAccount() {
        createAccountBtn.click();
        return new AccountCreatedPage(driver);
    }

    @Step("Weryfikacja 'Name' oraz 'Email' w sekcji 'Enter Account Information'")
    public RegisterAccountPage verifyNameAndEmail(String expectedName, String expectedEmail) {
        WebElement nameElement = driver.findElement(By.id("name"));
        WebElement emailElement = driver.findElement(By.id("email"));
        String actualName = nameElement.getAttribute("value");
        String actualEmail = emailElement.getAttribute("value");
        if (!actualName.equals(expectedName)) {
            throw new AssertionError("Expected name: " + expectedName + ", but got: " + actualName);
        }
        if (!actualEmail.equals(expectedEmail)) {
            throw new AssertionError("Expected email: " + expectedEmail + ", but got: " + actualEmail);
        }
        return this;
    }

    /**
     * Pomocnik do ustawiania checkboxów.
     */
    private void setCheckbox(WebElement element, boolean shouldBeChecked) {
        if (element.isSelected() != shouldBeChecked) {
            element.click();
        }
    }
}
