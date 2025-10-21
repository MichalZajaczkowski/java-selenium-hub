package pl.devqa.javaseleniumhub.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import io.qameta.allure.Step;

public class HomePage {

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "div.logo > a > img") // przykładowy lokator – dostosuj do rzeczywistej struktury strony
    private WebElement siteLogo;

    @Step("Verify Home Page is loaded")
    public boolean isHomePageLoaded() {
        return driver.getTitle().contains("Automation Exercise");
    }

    @Step("Verify Home Page logo is displayed")
    public boolean isLogoDisplayed() {
        try {
            return siteLogo.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
