package Pages;

import Utilities.LogsUtils;
import Utilities.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_OverviewPage {

    static float subTotalPrice = 0;

    private final By SubtotalElement = By.className("summary_subtotal_label");
    private final By Tax = By.className("summary_tax_label");
    private final By totalElement = By.className("summary_total_label");
    private final By FinishButton = By.cssSelector("button[data-test='finish']");

    private final WebDriver driver;

    public P05_OverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public float getSubTotalPrice() {
        String fullText = Util.getText(driver, SubtotalElement);
//        subTotalPrice = Float.parseFloat(fullText.replace("$", ""));
        subTotalPrice = Float.parseFloat(fullText.replaceAll("[^\\d.]", "")); // Removes all non-numeric characters except the decimal point
        LogsUtils.info("SubTotal Price: " + subTotalPrice);
        return subTotalPrice;
    }

    public float getTax() {
        return Float.parseFloat(Util.getText(driver, Tax).replace("Tax: $", ""));
    }

    public float Total() {
        return Float.parseFloat(Util.getText(driver, totalElement).replace("Total: $", ""));
    }

    public boolean CheckTotalPrice() {
        float x = getSubTotalPrice();
        float t = getTax();
        float tot = Total();

        LogsUtils.info(x + " + " + t + " = " + tot);
        return (x + t == tot);
    }

    public P06_CheckoutCompletePage ClickFinishButton() {
        Util.ClickElement(driver, FinishButton);
        return new P06_CheckoutCompletePage(driver);
    }

}
