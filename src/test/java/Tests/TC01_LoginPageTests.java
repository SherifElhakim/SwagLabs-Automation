package Tests;

import Listeners.ITestResultLIstenerClass;
import Listeners.IinvokedListenerCLass;
import Pages.P01_LoginPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;

@Listeners({IinvokedListenerCLass.class, ITestResultLIstenerClass.class})
public class TC01_LoginPageTests {

//    private WebDriver driver;

    @BeforeMethod
    public void setup() throws IOException {
//        driver = new ChromeDriver();
//        setupDriver("chrome");
        LogsUtils.info("driver initiated");
        setupDriver(DataUtils.getPropertiesValue("enviroments", "Browser"));
//        driver.get("https://www.saucedemo.com/");
        getDriver().get(DataUtils.getPropertiesValue("enviroments", "Base_URL"));
        getDriver().manage().window().maximize();
        LogsUtils.info("Page is redirected to URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void Valid_Login() throws IOException {
        new P01_LoginPage(getDriver())
                .SetUsername(DataUtils.getJsonData("ValidLogin", "username"))
                .SetPassword(DataUtils.getJsonData("ValidLogin", "passw"))
                .Click_Login_Button();

        Assert.assertTrue(new P01_LoginPage(getDriver()).assertLoginTC(DataUtils.getPropertiesValue("enviroments", "Home_URL")));
//        Assert.assertEquals(getDriver().getCurrentUrl(), DataUtils.getPropertiesValue("enviroments", "Home_URL"));
    }

    @Test
    public void InvalidLoginEmptyFields() {
        new P01_LoginPage(getDriver()).Click_Login_Button();

        Assert.assertTrue(new P01_LoginPage(getDriver()).assertErrorMsgVisibility());
    }

    @Test
    public void InvalidLoginData() {
        new P01_LoginPage(getDriver())
                .SetUsername("Invalid")
                .SetPassword("invalid")
                .Click_Login_Button();

        Assert.assertTrue(new P01_LoginPage(getDriver()).assertErrorMsgVisibility());
    }

    @AfterMethod
    public void End() {
        quitDriver();
    }
}
