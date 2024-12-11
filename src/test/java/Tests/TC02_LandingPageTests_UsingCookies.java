package Tests;

import Listeners.ITestResultLIstenerClass;
import Listeners.IinvokedListenerCLass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Util;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.getJsonData;

@Listeners({IinvokedListenerCLass.class, ITestResultLIstenerClass.class})
public class TC02_LandingPageTests_UsingCookies {

    private Set<Cookie> cookiesset;

    @BeforeClass
    public void LoginSession() throws IOException {
        LogsUtils.info("driver initiated");
        setupDriver(DataUtils.getPropertiesValue("enviroments", "Browser"));
        getDriver().get(DataUtils.getPropertiesValue("enviroments", "Base_URL"));
        getDriver().manage().window().maximize();
        LogsUtils.info("Page is redirected to URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        new P01_LoginPage(getDriver())
                .SetUsername(DataUtils.getJsonData("ValidLogin", "username"))
                .SetPassword(getJsonData("ValidLogin", "passw"))
                .Click_Login_Button();
        cookiesset = Util.getAllCookies(getDriver());
        quitDriver();
    }

    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(DataUtils.getPropertiesValue("enviroments", "Browser"));
        getDriver().get(DataUtils.getPropertiesValue("enviroments", "Base_URL"));
        getDriver().manage().window().maximize();
        Util.restoreSession(getDriver(), cookiesset);
        getDriver().get(DataUtils.getPropertiesValue("enviroments", "Home_URL"));
        getDriver().navigate().refresh();
    }

    @Test
    public void CheckNumberOfSelectedProducts() throws FileNotFoundException {

        new P02_LandingPage(getDriver()).AddAllProductsToCart();

        Assert.assertTrue(new P02_LandingPage(getDriver()).CheckifSelectedEqualToCart());

    }

    @Test
    public void addingRandomProducts() throws FileNotFoundException {
        new P02_LandingPage(getDriver()).addRandomProducts(3, 6);

        Assert.assertTrue(new P02_LandingPage(getDriver()).CheckifSelectedEqualToCart());
    }

    @Test
    public void ClickOnCartIcon() throws IOException {
        new P02_LandingPage(getDriver()).clickCartIcon();

        Assert.assertTrue(Util.VerifyRedirectToPage(getDriver(), DataUtils.getPropertiesValue("enviroments", "Cart_URL")));
    }

    @AfterMethod
    public void End() {
        quitDriver();
    }

    @AfterClass
    public void Clear() {
        cookiesset.clear();
    }
}
