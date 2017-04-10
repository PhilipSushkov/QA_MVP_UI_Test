package pageobjects.SystemAdmin.SiteMaintenance;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2017-04-10.
 */

public class FunctionalBtn extends AbstractPageObject {
    private static By btnGoLive;

    public FunctionalBtn(WebDriver driver) {
        super(driver);
    }


    public boolean getGoLiveBtnStatus() {

        return false;
    }

}
