package pageobjects.ProductCategory.Accessories;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;
import util.Log;

import static specs.AbstractSpec.propUIProdCategory;

/**
 * Created by PSUSHKOV on Aug, 2018
 **/


public class Checkout extends AbstractPageObject {
    private static By continueBtn;

    public Checkout(WebDriver driver) {
        super(driver);

        continueBtn = By.xpath(propUIProdCategory.getProperty("href_Continue"));
    }


    public String getTitle() {
        waitForElement(continueBtn);
        return driver.getTitle();
    }

}
