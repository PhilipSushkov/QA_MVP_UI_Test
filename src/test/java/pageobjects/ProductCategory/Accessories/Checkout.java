package pageobjects.ProductCategory.Accessories;

import org.json.simple.JSONObject;
import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;
import util.Log;

import static specs.AbstractSpec.propUIProdCategory;

/**
 * Created by PSUSHKOV on Aug, 2018
 **/


public class Checkout extends AbstractPageObject {
    private static By continueBtn;
    private static String sItemCheckout = propUIProdCategory.getProperty("href_ItemCheckout");
    private static String sItemQuantity = propUIProdCategory.getProperty("inp_ItemQuantity");

    public Checkout(WebDriver driver) {
        super(driver);

        continueBtn = By.xpath(propUIProdCategory.getProperty("href_Continue"));
    }

    public String getTitle() {
        try {
            waitForElement(continueBtn);
            return driver.getTitle();
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
            Log.error("Get Checkout Page Title: ElementNotFoundException occurred");
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
            Log.error("Get Checkout Page Title: ElementNotVisibleException occurred");
        } catch (TimeoutException e) {
            e.printStackTrace();
            Log.error("Get Checkout Page Title: TimeoutException occurred");
        }

        return null;
    }

    public String getItemName(JSONObject data) {
        String sItem = data.get("item").toString();

        try {
            By itemCheckoutHref = By.xpath(String.format(sItemCheckout, sItem));
            waitForElement(itemCheckoutHref);
            return findElement(itemCheckoutHref).getText();
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
            Log.error("Checkout. Get Item Name: ElementNotFoundException occurred");
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
            Log.error("Checkout. Get Item Name: ElementNotVisibleException occurred");
        } catch (TimeoutException e) {
            e.printStackTrace();
            Log.error("Checkout. Get Item Name: TimeoutException occurred");
        }

        return null;
    }

    public long getItemQuantity(JSONObject data) {
        String sItem = data.get("item").toString();

        try {
            By itemQuantityInp = By.xpath(String.format(sItemQuantity, sItem));
            waitForElement(itemQuantityInp);
            return Long.parseLong(findElement(itemQuantityInp).getAttribute("value"));
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
            Log.error("Checkout. Get Item Quantity: ElementNotFoundException occurred");
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
            Log.error("Checkout. Get Item Quantity: ElementNotVisibleException occurred");
        } catch (TimeoutException e) {
            e.printStackTrace();
            Log.error("Checkout. Get Item Quantity: TimeoutException occurred");
        }

        return 0;
    }

}
