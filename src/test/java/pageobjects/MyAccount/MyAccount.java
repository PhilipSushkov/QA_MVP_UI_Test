package pageobjects.MyAccount;

import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import util.Log;

/**
 * Created by PSUSHKOV on Oct, 2019
 **/

public class MyAccount extends AbstractPageObject {

    public MyAccount(WebDriver driver) {
        super(driver);

        //productCategoryHref = By.xpath(propUIProdCategory.getProperty("href_ProductCategory"));
    }

    public String getErrorMessage() {
        try {
            //waitForElement(transResultsTbl);
            return driver.getTitle();
        } catch (Exception e) {
            e.printStackTrace();
            Log.error("Get Login Error message: ElementNotFoundException occurred");
        }

        return null;
    }

}
