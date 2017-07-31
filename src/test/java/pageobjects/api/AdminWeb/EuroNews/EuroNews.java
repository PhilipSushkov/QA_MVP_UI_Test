package pageobjects.api.AdminWeb.EuroNews;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.PageObject;

import static specs.ApiAbstractSpec.propAPI;

/**
 * Created by philipsushkov on 2017-07-31.
 */

public class EuroNews extends AbstractPageObject {
    private static By moduleTitle, searchInp;

    public EuroNews(WebDriver driver) {
        super(driver);

        moduleTitle = By.xpath(propAPI.getProperty("h1_title"));
        searchInp = By.cssSelector(propAPI.getProperty("inp_Search"));
    }


    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getSearchInput() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(searchInp)));
            element = findElement(searchInp);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }


}
