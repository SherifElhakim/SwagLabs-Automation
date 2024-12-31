package Tests;

import DriverFactory.DriverFactory;
import Listeners.ITestResultLIstenerClass;
import Listeners.IinvokedListenerCLass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Util;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.getJsonData;

@Listeners({IinvokedListenerCLass.class, ITestResultLIstenerClass.class})
public class TC02_LandingPageTests {

    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(DataUtils.getPropertiesValue("enviroments", "Browser"));
        LogsUtils.info("driver initiated");
        getDriver().get(DataUtils.getPropertiesValue("enviroments", "Base_URL"));
        LogsUtils.info("Page is redirected to URL");
        getDriver().manage().window().maximize();
    }

    @Test
    public void CheckNumberOfSelectedProducts() throws FileNotFoundException {
        new P01_LoginPage(getDriver())
                .SetUsername(DataUtils.getJsonData("ValidLogin", "username"))
                .SetPassword(getJsonData("ValidLogin", "passw"))
                .Click_Login_Button()
                .AddAllProductsToCart();

        Assert.assertTrue(new P02_LandingPage(getDriver()).CheckifSelectedEqualToCart());

    }

    @Test
    public void CheckNumberOfSelectedProductsAfterremoving() throws FileNotFoundException {
        new P01_LoginPage(getDriver())
                .SetUsername(DataUtils.getJsonData("ValidLogin", "username"))
                .SetPassword(getJsonData("ValidLogin", "passw"))
                .Click_Login_Button()
                .AddAllProductsToCart()
                .UnselectRandomElement();

        Assert.assertTrue(new P02_LandingPage(getDriver()).CheckifSelectedEqualToCart());

    }

    @Test
    public void addingRandomProducts() throws FileNotFoundException {
        new P01_LoginPage(getDriver())
                .SetUsername(getJsonData("ValidLogin", "username"))
                .SetPassword(getJsonData("ValidLogin", "passw"))
                .Click_Login_Button()
                .addRandomProducts(3, 6);

        Assert.assertTrue(new P02_LandingPage(getDriver()).CheckifSelectedEqualToCart());
    }

    @Test
    public void ClickOnCartIcon() throws IOException {
        new P01_LoginPage(getDriver())
                .SetUsername(getJsonData("ValidLogin", "username"))
                .SetPassword(getJsonData("ValidLogin", "passw"))
                .Click_Login_Button()
                .clickCartIcon();

        Assert.assertTrue(Util.VerifyRedirectToPage(getDriver(), DataUtils.getPropertiesValue("enviroments", "Cart_URL")));
    }


    @Test
    public void VerifyTwitterButton() throws FileNotFoundException {
        new P01_LoginPage(getDriver())
                .SetUsername(getJsonData("ValidLogin", "username"))
                .SetPassword(getJsonData("ValidLogin", "passw"))
                .Click_Login_Button()
                .ClickTwitterButton();
        Set<String> handles = getDriver().getWindowHandles();
        for (String h : handles) {
            if (!Objects.equals(getDriver().getWindowHandle(), h)) {
                getDriver().switchTo().window(h);
            }
        }
        LogsUtils.info("Window is " + getDriver().getCurrentUrl());
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().equals("https://x.com/saucelabs"));
    }

    @Test
    public void VerifyFacebookButton() throws FileNotFoundException {
        new P01_LoginPage(getDriver())
                .SetUsername(getJsonData("ValidLogin", "username"))
                .SetPassword(getJsonData("ValidLogin", "passw"))
                .Click_Login_Button()
                .ClickFacebookButton();
        Set<String> handles = getDriver().getWindowHandles();
        for (String h : handles) {
            if (!Objects.equals(getDriver().getWindowHandle(), h)) {
                getDriver().switchTo().window(h);
            }
        }
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().equals("https://www.facebook.com/saucelabs"));
    }

    @Test
    public void VerifyLinkedinButton() throws FileNotFoundException {
        new P01_LoginPage(getDriver())
                .SetUsername(getJsonData("ValidLogin", "username"))
                .SetPassword(getJsonData("ValidLogin", "passw"))
                .Click_Login_Button()
                .ClickLinkedinButton();
        Set<String> handles = getDriver().getWindowHandles();
        for (String h : handles) {
            if (!Objects.equals(getDriver().getWindowHandle(), h)) {
                getDriver().switchTo().window(h);
            }
        }
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().equals("https://www.linkedin.com/company/sauce-labs/"));
    }

    @AfterMethod
    public void End() {
        quitDriver();
    }
}
