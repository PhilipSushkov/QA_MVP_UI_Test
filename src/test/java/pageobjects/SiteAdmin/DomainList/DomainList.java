package pageobjects.SiteAdmin.DomainList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by philipsushkov on 2016-11-23.
 */

public class DomainList extends AbstractPageObject {

    private final By moduleTitle = By.xpath("//span[contains(@class, 'AdminContent')]/h1/span[contains(@id,'ModuleTitle')]");
    private final By grid = By.xpath("//table[contains(@id, 'Domain_dataGrid')]");
    private final By gridDomainList = By.xpath("//td[contains(@class,'DataGridItemBorder')]");
    private final By hrefDefaultDomain = By.xpath("//span[contains(@class, 'DefaultDomain')]/a[contains(text(),'Public Site Edit')]");
    private final Integer columnsNumber = 7;

    public DomainList(WebDriver driver) {
        super(driver);
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
