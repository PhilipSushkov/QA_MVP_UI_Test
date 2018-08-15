package pageobjects.ProductCategory.Accessories;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;
import static specs.AbstractSpec.propUIProdCategory;

public class BuyItem extends AbstractPageObject {
    private static By productCategoryHref;


    public BuyItem(WebDriver driver) {
        super(driver);

        productCategoryHref = By.xpath(propUIProdCategory.getProperty("href_ProductCategory"));
    }


    public void OpenAccessoriesSection() {
        try {
            waitForElement(productCategoryHref);
            findElement(productCategoryHref).click();
        } catch (ElementNotFoundException e) {
            e.printStackTrace();
        } catch (ElementNotVisibleException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }


}
