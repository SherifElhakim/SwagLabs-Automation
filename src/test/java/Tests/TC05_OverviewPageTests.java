package Tests;

import Listeners.ITestResultLIstenerClass;
import Listeners.IinvokedListenerCLass;
import Pages.P01_LoginPage;
import Pages.P06_CheckoutCompletePage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Util;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static DriverFactory.DriverFactory.*;
import static Utilities.DataUtils.getJsonData;

@Listeners({IinvokedListenerCLass.class, ITestResultLIstenerClass.class})
public class TC05_OverviewPageTests {
    private final String zipcode = new Faker().number().digits(5);

    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(DataUtils.getPropertiesValue("enviroments", "Browser"));
        LogsUtils.info("driver initiated");
        getDriver().get(DataUtils.getPropertiesValue("enviroments", "Base_URL"));
        LogsUtils.info("Page is redirected to URL");
        getDriver().manage().window().maximize();
    }

    @Test
    public void CheckOutStepOne() throws FileNotFoundException {
        new P01_LoginPage(getDriver())
                .SetUsername(getJsonData("ValidLogin", "username"))
                .SetPassword(getJsonData("ValidLogin", "passw"))
                .Click_Login_Button()
                .addRandomProducts(3, 6)
                .clickCartIcon()
                .clickCheckoutButton()
                .FillData("Sherif" + "-" + Util.getTimeStamp(), "Elhakim" + "-" + Util.getTimeStamp(), zipcode)//To make the data unique every time this test is run
                .ClickContinueButton()
                .ClickFinishButton();

        Assert.assertTrue(new P06_CheckoutCompletePage(getDriver()).CheckVisibilityOfThanksMsg());
    }

    @AfterMethod
    public void End() {
        quitDriver();
    }
}
