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
    private static By togglerBtn, titleH1;
    private static final long DEFAULT_PAUSE = 1000;

    public LeftMainMenu(WebDriver driver) {
        super(driver);

        togglerBtn = By.cssSelector(propAPI.getProperty("toggler_button"));
        titleH1 = By.xpath(propAPI.getProperty("h1_title"));
    }

    public String getEuroNewsClientListPage(String sMenuItem) {
        waitForElement(togglerBtn);
        findElement(togglerBtn).click();

        By menuItem = By.xpath("//a[(@title='"+sMenuItem+"')]");
        waitForElement(menuItem);
        findElement(menuItem).click();

        waitForElement(titleH1);
        findElement(titleH1).click();

        return findElement(titleH1).getText().trim();
    }
}
