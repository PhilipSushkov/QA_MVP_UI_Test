package pageobjects.SiteAdmin.LookupList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by philipsushkov on 2016-11-21.
 */

public class LookupList extends AbstractPageObject {

    private final By moduleTitle = By.xpath("//span[contains(@class, 'AdminContent')]/h1/span[contains(@id,'ModuleTitle')]");
    private final By grid = By.xpath("//table[contains(@id, 'Lookup_dataGrid')]");
    private final By gridModuleDefinitionList = By.xpath("//td[contains(@class,'DataGridItemBorder')]");
    private final By selectLookupType = By.xpath("//select[contains(@id, 'LookupType')]");
    private final Integer columnsNumber = 8;

    public LookupList(WebDriver driver) {
        super(driver);
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)) );
        return findElement(moduleTitle).getText();
    }

    public Integer getLookupListQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        return findElement(grid).findElements(gridModuleDefinitionList).size()/columnsNumber;
    }

    public WebElement getLookupListLookupType() {
        wait.until(ExpectedConditions.visibilityOf(findElement(selectLookupType)) );
        return findElement(selectLookupType);
    }

}
