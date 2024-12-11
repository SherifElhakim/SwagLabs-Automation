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
import java.time.Duration;

import static DriverFactory.DriverFactory.getDriver;
import static DriverFactory.DriverFactory.setupDriver;
import static Utilities.DataUtils.getJsonData;

@Listeners({IinvokedListenerCLass.class, ITestResultLIstenerClass.class})
public class TC02_LandingPageTests {

    @BeforeMethod
    public void setup() throws IOException {
        LogsUtils.info("driver initiated");
        setupDriver(DataUtils.getPropertiesValue("enviroments", "Browser"));
        getDriver().get(DataUtils.getPropertiesValue("enviroments", "Base_URL"));
        getDriver().manage().window().maximize();
        LogsUtils.info("Page is redirected to URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().equals("https://x.com/saucelabs"));
    }

    @Test
    public void VerifyFacebookButton() throws FileNotFoundException {
        new P01_LoginPage(getDriver())
                .SetUsername(getJsonData("ValidLogin", "username"))
                .SetPassword(getJsonData("ValidLogin", "passw"))
                .Click_Login_Button()
                .ClickFacebookButton();
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().equals("https://www.facebook.com/saucelabs"));
    }

    @Test
    public void VerifyLinkedinButton() throws FileNotFoundException {
        new P01_LoginPage(getDriver())
                .SetUsername(getJsonData("ValidLogin", "username"))
                .SetPassword(getJsonData("ValidLogin", "passw"))
                .Click_Login_Button()
                .ClickLinkedinButton();
        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().equals("https://www.linkedin.com/authwall?trk=bf&trkInfo=AQGXBECpTeRMlQAAAZOzQaD4dVWitSOOE3OrrrVTkjltPy3X4pbofVajGqUjtZ6rhfRLQeoFif5po9p78u3FIX6-YtbGUhpO9OEU4mJa3182CMcl-vMNAmN0PGaOiNdqIs8nGcg=&original_referer=&sessionRedirect=https%3A%2F%2Fwww.linkedin.com%2Fcompany%2Fsauce-labs%2F"));
    }

    @AfterMethod
    public void End() {
        // quitDriver();
    }
}
