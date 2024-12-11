package Pages;

import Utilities.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P04_CheckoutPage {
    private final WebDriver driver;
    private final By FirstNameField = By.id("first-name");
    private final By LastNameField = By.id("last-name");
    private final By ZipcodeField = By.id("postal-code");
    private final By ContinueButton = By.id("continue");


    public P04_CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public P04_CheckoutPage FillData(String fname, String lname, String zip) {
        Util.SetData(driver, FirstNameField, fname);
        Util.SetData(driver, LastNameField, lname);
        Util.SetData(driver, ZipcodeField, zip);

        return this;
    }

    public P05_OverviewPage ClickContinueButton() {
        Util.ClickElement(driver, ContinueButton);
        return new P05_OverviewPage(driver);
    }


}
