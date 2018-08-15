package pageobjects.ProductCategory.Accessories;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;
import util.Log;

import static specs.AbstractSpec.propUIProdCategory;

public class BuyItem extends AbstractPageObject {
    private static By productCategoryHref, accessoriesHref;


    public BuyItem(WebDriver driver) {
        super(driver);

        productCategoryHref = By.xpath(propUIProdCategory.getProperty("href_ProductCategory"));
        accessoriesHref = By.xpath(propUIProdCategory.getProperty("href_Accessories"));
    }


    public void OpenAccessoriesSection() throws InterruptedException {
        if (openPageFromMenu(productCategoryHref, accessoriesHref)) {
            Log.info("Accessories Section has been open successfully");
        } else {
            Log.error("Accessories Section has not been open!");
        }
    }


}
