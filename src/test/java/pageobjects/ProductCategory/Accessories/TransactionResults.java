package pageobjects.ProductCategory.Accessories;

import org.openqa.selenium.*;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.AbstractPageObject;
import util.Log;

import java.util.HashMap;

import static specs.AbstractSpec.propUIProdCategory;

/**
 * Created by PSUSHKOV on Aug, 2018
 **/

public class TransactionResults extends AbstractPageObject {
    private static By transResultsTbl, itemQuantityTd;
    private static String sItemName = propUIProdCategory.getProperty("td_ItemName");

    public TransactionResults(WebDriver driver) {
        super(driver);
        transResultsTbl = By.xpath(propUIProdCategory.getProperty("tbl_TransResults"));
        itemQuantityTd = By.xpath(propUIProdCategory.getProperty("td_ItemQuantity"));
    }

    public String getTitle() {
        try {
            waitForElement(transResultsTbl);
            return driver.getTitle();
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
            Log.error("Get Transaction Results Title: ElementNotFoundException occurred");
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
            Log.error("Get Transaction Results Title: ElementNotVisibleException occurred");
        } catch (TimeoutException e) {
            e.printStackTrace();
            Log.error("Get Transaction Results Title: TimeoutException occurred");
        }

        return null;
    }

    public HashMap checkOrder(JSONObject data) {
        HashMap results = new HashMap();
        String sItem = data.get("item").toString();

        try {
            By itemNameTd = By.xpath(String.format(sItemName, sItem));
            waitForElement(itemNameTd);
            results.put("itemName", findElement(itemNameTd).getText());
            results.put("itemuQantity", findElement(itemQuantityTd).getText());
            Log.info("Transaction Results. All elements hav been found successfully");
            return results;
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
            Log.error("Transaction Results. Check Order: ElementNotFoundException occurred");
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
            Log.error("Transaction Results. Check Order: ElementNotVisibleException occurred");
        } catch (TimeoutException e) {
            e.printStackTrace();
            Log.error("Transaction Results. Check Order: TimeoutException occurred");
        }

        return results;
    }
}
