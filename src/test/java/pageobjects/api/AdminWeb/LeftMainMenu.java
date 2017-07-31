package pageobjects.api.AdminWeb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.ApiAbstractSpec.propAPI;

/**
 * Created by philipsushkov on 2017-07-31.
 */

public class LeftMainMenu extends AbstractPageObject {
    private static final long DEFAULT_PAUSE = 1000;

    public LeftMainMenu(WebDriver driver) {
        super(driver);
    }

    public String getEuroNewsClientListPage() {

        return "";
    }
}
