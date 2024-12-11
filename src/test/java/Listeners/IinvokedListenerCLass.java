package Listeners;

import Utilities.LogsUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.io.IOException;

import static DriverFactory.DriverFactory.getDriver;
import static Utilities.Util.takeScreenshot;

public class IinvokedListenerCLass implements IInvokedMethodListener {
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            LogsUtils.info("Test Case " + testResult.getName() + " failed");
            try {
                takeScreenshot(getDriver(), testResult.getName());
//                Util.TakeFullScreenshot(getDriver(), new P02_LandingPage(getDriver()).getNumber_of_products_in_cart());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
