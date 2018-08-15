package pageobjects.ProductCategory.Accessories;

import org.openqa.selenium.*;
import org.json.simple.JSONObject;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.AbstractPageObject;
import util.Log;
import static specs.AbstractSpec.propUIProdCategory;

/**
 * Created by PSUSHKOV on Aug, 2018
 **/

public class BuyItem extends AbstractPageObject {
    private static By productCategoryHref, accessoriesHref, checkoutHref;
    private static String sAddToCart = propUIProdCategory.getProperty("btn_AddToCart");
    private static String sAddedItem = propUIProdCategory.getProperty("p_AddedItem");

    public BuyItem(WebDriver driver) {
        super(driver);

        productCategoryHref = By.xpath(propUIProdCategory.getProperty("href_ProductCategory"));
        accessoriesHref = By.xpath(propUIProdCategory.getProperty("href_Accessories"));
        checkoutHref = By.xpath(propUIProdCategory.getProperty("href_Checkout"));
    }

    public void OpenAccessoriesSection() throws InterruptedException {
        if (openPageFromMenu(productCategoryHref, accessoriesHref)) {
            Log.info("Accessories Section has been open successfully");
        } else {
            Log.error("Accessories Section has not been open!");
        }
    }

    // Click on Add to Cart for just Magic Mouse
    public void addToCart(JSONObject data) {
        String sItem = data.get("item").toString();

        try {
            // Click on Add To Cart button
            By addToCartBtn = By.xpath(String.format(sAddToCart, sItem));
            waitForElement(addToCartBtn);
            findElement(addToCartBtn).click();

            // Wait the confirmation message that the item has been added
            By addedItemP = By.xpath(String.format(sAddedItem, sItem));
            WebElement addedItemElement = new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(addedItemP));
            //System.out.println(addedItemElement.getText());

            // Click on Checkout link
            findElement(checkoutHref).click();
            //System.out.println(driver.getTitle());
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            System.out.println(driver.getCurrentUrl());
            e.printStackTrace();
        }




        // Confirm that we have 1 Magic Mouse in our Checkout page
        By itemQuantity = By.xpath("//div[@id='checkout_page_container']/.//a[text()='Magic Mouse']/../following-sibling::td//input[@name='quantity'][@type='text']");
        waitForElement(itemQuantity);
        WebElement itemCheckoutElement = findElement(itemQuantity);
        System.out.println("Quantity: " + itemCheckoutElement.getAttribute("value"));

        // Click on Continue button
        By continueBtn = By.xpath("//a/span[text()='Continue']");
        findElement(continueBtn).click();

        // Check if Calculate Shipping Price label is shown
        By calculateH2 = By.xpath("//h2[text()='Calculate Shipping Price']");
        waitForElement(calculateH2);
        System.out.println(findElement(calculateH2).getText());
    }


}
