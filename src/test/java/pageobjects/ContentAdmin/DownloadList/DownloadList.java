package pageobjects.ContentAdmin.DownloadList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by philipsushkov on 2016-12-02.
 */

public class DownloadList extends AbstractPageObject {
    private final By moduleTitle = By.xpath("//span[contains(@class, 'AdminContent')]/h1/span[contains(@id,'ModuleTitle')]");
    private final By grid = By.xpath("//table[contains(@id, 'Reports2_dataGrid')]");
    private final By gridDownloadList = By.xpath("//td[contains(@class,'DataGridItemBorder')]");
    private final By dataGridPager = By.xpath("//tr[contains(@class, 'DataGridPager')]");
    private final By inputFilterByTag = By.xpath("//input[contains(@id, 'TagSelection_txtTags')]");
    private final Integer columnsNumber = 8;

    public DownloadList(WebDriver driver) {
        super(driver);
    }


    public String getUrl() {
        return driver.getCurrentUrl();
    }


    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)) );
        return findElement(moduleTitle).getText();
    }


    public Integer getTitleQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        return findElement(grid).findElements(gridDownloadList).size()/columnsNumber;
    }


    public WebElement getQuickLinksPagination() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(dataGridPager)));
            element = findElement(dataGridPager);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }


    public WebElement getFilterByTag() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(inputFilterByTag)));
            element = findElement(inputFilterByTag);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }
}
