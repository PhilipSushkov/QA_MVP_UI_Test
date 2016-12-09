package pageobjects.SiteAdmin.DomainList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2016-11-23.
 */

public class DomainList extends AbstractPageObject {
    private static By moduleTitle, grid, gridDomainList, hrefDefaultDomain;
    private final Integer columnsNumber = 7;

    public DomainList(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUISiteAdmin.getProperty("table_GridDomain"));
        gridDomainList = By.xpath(propUISiteAdmin.getProperty("table_GridItem"));
        hrefDefaultDomain = By.xpath(propUISiteAdmin.getProperty("href_DefaultDomain"));
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)) );
        return findElement(moduleTitle).getText();
    }

    public Integer getDomainQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        return findElement(grid).findElements(gridDomainList).size()/columnsNumber;
    }

    public WebElement getHrefPublicSite() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(hrefDefaultDomain)));
            element = findElement(hrefDefaultDomain);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

}
