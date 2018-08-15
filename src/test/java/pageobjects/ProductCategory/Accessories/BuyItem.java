package pageobjects.ProductCategory.Accessories;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;
import static specs.AbstractSpec.propUIProdCategory;

public class BuyItem extends AbstractPageObject {
    private static By productCategoryHref, accessoriesHref;


    public BuyItem(WebDriver driver) {
        super(driver);

        productCategoryHref = By.xpath(propUIProdCategory.getProperty("href_ProductCategory"));
        accessoriesHref = By.xpath(propUIProdCategory.getProperty("href_Accessories"));
    }


    public void OpenAccessoriesSection() throws InterruptedException {
        try {
            openPageFromMenu(productCategoryHref, accessoriesHref);
            /*
            waitForElement(productCategoryHref);
            findElement(productCategoryHref).click();
            waitForElement(accessoriesHref);
            findElement(accessoriesHref).click();
            */
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }


}
