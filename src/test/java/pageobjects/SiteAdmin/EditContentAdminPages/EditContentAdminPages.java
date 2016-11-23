package pageobjects.SiteAdmin.EditContentAdminPages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.PageObject;

/**
 * Created by philipsushkov on 2016-11-23.
 */

public class EditContentAdminPages extends AbstractPageObject {

    private final By moduleTitle = By.xpath("//span[contains(@class, 'AdminContent')]/div/h1[contains(text(),'Edit Content Admin Pages')]");
    private final By grid = By.xpath("//table[contains(@id, 'ContentAdminPages')]");
    private final By gridTitleList = By.xpath("//td[contains(@class,'DataGridItemBorder')]");
    private final By gridShowInNav = By.xpath("//tr/td/input[contains(@name,'lblIsVisible')]");
    private final Integer columnsNumber = 2;

    public EditContentAdminPages(WebDriver driver) {
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
        return findElement(grid).findElements(gridTitleList).size()/columnsNumber;
    }

    public Integer getShowInNavCkbQuantity() {
        Integer ckbSize = 0;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(gridShowInNav)));
            ckbSize = findElement(grid).findElements(gridShowInNav).size();
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return ckbSize;
    }

}
