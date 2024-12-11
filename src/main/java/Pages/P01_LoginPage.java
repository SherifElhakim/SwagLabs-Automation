package Pages;

import Utilities.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P01_LoginPage {

    private final By un = By.id("user-name");
    private final By pw = By.id("password");
    private final By LoginBut = By.id("login-button");
    private final By errMsg = By.xpath("//h3[@data-test='error']");

    private final WebDriver driver;

    public P01_LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public P01_LoginPage SetUsername(String s) {
        Util.SetData(driver, un, s);
        return this;
    }

    public P01_LoginPage SetPassword(String s) {
        Util.SetData(driver, pw, s);
        return this;
    }

    public P02_LandingPage Click_Login_Button() {
        Util.ClickElement(driver, LoginBut);
        return new P02_LandingPage(driver);
    }

    public boolean assertLoginTC(String expected) {
        return driver.getCurrentUrl().equals(expected);
    }

    public boolean assertErrorMsgVisibility() {
        return Util.checkVisibilityofElement(driver, errMsg);
    }
}
