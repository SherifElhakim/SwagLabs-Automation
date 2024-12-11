package Tests;

import Listeners.ITestResultLIstenerClass;
import Listeners.IinvokedListenerCLass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Pages.P03_CartPAge;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.getJsonData;

@Listeners({IinvokedListenerCLass.class, ITestResultLIstenerClass.class})
public class TC03_CartPageTests {
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
    public void CheckPrices() throws FileNotFoundException {
        String totalSelected = new P01_LoginPage(getDriver())
                .SetUsername(getJsonData("ValidLogin", "username"))
                .SetPassword(getJsonData("ValidLogin", "passw"))
                .Click_Login_Button()
                .addRandomProducts(3, 6)
                .getTotalPrice();
        new P02_LandingPage(getDriver()).clickCartIcon();

        Assert.assertTrue(new P03_CartPAge(getDriver()).ComparingPrices(totalSelected));
    }

    @AfterMethod
    public void End() {
        quitDriver();
    }
}
