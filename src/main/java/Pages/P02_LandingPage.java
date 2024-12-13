package Pages;

import Utilities.LogsUtils;
import Utilities.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class P02_LandingPage {
    static float TotalPrice = 0;
    private static List<WebElement> AllProducts;
    private static List<WebElement> SelectedProducts;
    private final By Add_all_elements_to_cart = By.xpath("//button[@class]");
    private final By Number_of_products_in_cart = By.className("shopping_cart_badge");
    private final By Number_of_Selected_Products = By.xpath("//button[.='Remove']");
    private final By CartIcon = By.xpath("//a[@data-test='shopping-cart-link']");
    private final By SelectedProductsPrices = By.xpath("//button[.=\"Remove\"]//preceding-sibling::div[@data-test='inventory-item-price']");
    private final By twitterButton = By.xpath("//a[@data-test='social-twitter']");
    private final By FaceBook = By.xpath("//a[@data-test='social-facebook']");
    private final By linkedin = By.xpath("//a[@data-test='social-linkedin']");

    private final WebDriver driver;

    public P02_LandingPage(WebDriver driver) {
        this.driver = driver;
    }

    public By getNumber_of_products_in_cart() {
        return Number_of_products_in_cart;
    }

    public P02_LandingPage AddAllProductsToCart() {
        AllProducts = driver.findElements(Add_all_elements_to_cart);
        
        /*to go through the list of products you cant use class name because when the button is clicked the class changes
        so instead we used dynamic locator*/
        LogsUtils.info("Number of all products = " + AllProducts.size());
        for (int i = 1; i <= AllProducts.size(); i++) {
            By Product = By.xpath("(//button[@class])[" + i + "]");
            Util.ClickElement(driver, Product);
        }
        return this;
    }

    public String CounterOnCart() {
        //we used try and catch in case an element wasn't clicked, or it was out of stock so the cart icon will not have a field to display a text
        try {
            LogsUtils.info("Number of Products in Cart = " + Util.getText(driver, Number_of_products_in_cart));
            return Util.getText(driver, Number_of_products_in_cart);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }

    public String NumberOfSelectedProducts() {
        try {
            SelectedProducts = driver.findElements(Number_of_Selected_Products);
            LogsUtils.info("Number of Selected Products = " + String.valueOf((SelectedProducts.size())));
            return String.valueOf((SelectedProducts.size()));
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
            return "0";
        }
    }

    public boolean CheckifSelectedEqualToCart() {
        return NumberOfSelectedProducts().equals(CounterOnCart());
    }

    public P02_LandingPage addRandomProducts(int NumberOfProductsNeeded, int TotalNumOfProducts) {
        Set<Integer> randomNums = Util.generateUniqueRandNums(NumberOfProductsNeeded, TotalNumOfProducts);

        for (int rand : randomNums) {
            LogsUtils.info("Number of Random Product Selected is " + rand);
            By Product = By.xpath("(//button[@class])[" + rand + "]");
            Util.ClickElement(driver, Product);
        }
        return this;
    }

    public P03_CartPAge clickCartIcon() {
        Util.ClickElement(driver, CartIcon);
        return new P03_CartPAge(driver);
    }

    public String getTotalPrice() {
        try {
            List<WebElement> pricesOfSelectedProducts = driver.findElements(SelectedProductsPrices);

            for (int i = 1; i <= pricesOfSelectedProducts.size(); i++) {
                By prod = By.xpath("(//button[.=\"Remove\"]//preceding-sibling::div[@data-test='inventory-item-price'])[" + i + "]");
                String fullText = driver.findElement(prod).getText();
                TotalPrice += Float.parseFloat(fullText.replace("$", ""));
            }
            LogsUtils.info("Total Price = " + TotalPrice);
            return String.valueOf(TotalPrice);
        } catch (Exception e) {
            return "0";
        }
    }

    public P02_LandingPage UnselectRandomElement() {
        Random x = new Random();
        int ran = x.nextInt(6) + 1;
        LogsUtils.info("Random Project Removed is the Product number " + ran);
        By ProductToBeRemoved = By.xpath("(//button[.='Remove'])[" + ran + "]");
        Util.ClickElement(driver, ProductToBeRemoved);
        return this;
    }

    public P02_LandingPage ClickTwitterButton() {
        Util.ClickElement(driver, twitterButton);
        return this;
    }

    public P02_LandingPage ClickFacebookButton() {
        Util.ClickElement(driver, FaceBook);
        return this;
    }

    public P02_LandingPage ClickLinkedinButton() {
        Util.ClickElement(driver, linkedin);
        return this;
    }
}
