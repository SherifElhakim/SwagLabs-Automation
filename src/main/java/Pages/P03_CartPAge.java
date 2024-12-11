package Pages;

import Utilities.LogsUtils;
import Utilities.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P03_CartPAge {
    static float TotalPrice = 0;
    private final By SelectedProductsPrices = By.xpath("//button[.=\"Remove\"]//preceding-sibling::div[@data-test='inventory-item-price']");
    private final By CheckoutButton = By.xpath("//button[@data-test='checkout']");
    private final WebDriver driver;

    public P03_CartPAge(WebDriver driver) {
        this.driver = driver;
    }

    public String getTotalPriceInCart() {
        try {
            List<WebElement> pricesOfSelectedProducts = driver.findElements(SelectedProductsPrices);

            for (int i = 1; i <= pricesOfSelectedProducts.size(); i++) {
                By prod = By.xpath("(//button[.=\"Remove\"]//preceding-sibling::div[@data-test='inventory-item-price'])[" + i + "]");
                String fullText = driver.findElement(prod).getText();
                TotalPrice += Float.parseFloat(fullText.replace("$", ""));
            }
            LogsUtils.info("Total Price of items in Cart = " + TotalPrice);
            return String.valueOf(TotalPrice);
        } catch (Exception e) {
            return "0";
        }
    }

    public boolean ComparingPrices(String price) {
        return getTotalPriceInCart().equals(price);
    }

    public P04_CheckoutPage clickCheckoutButton() {
        Util.ClickElement(driver, CheckoutButton);
        return new P04_CheckoutPage(driver);
    }
}
