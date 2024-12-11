package Pages;

import Utilities.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P06_CheckoutCompletePage {

    private final By ThanksMsg = By.className("complete-header");
    private final WebDriver driver;

    public P06_CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean CheckVisibilityOfThanksMsg() {
        return Util.findWebElement(driver, ThanksMsg).isDisplayed();
    }

}
