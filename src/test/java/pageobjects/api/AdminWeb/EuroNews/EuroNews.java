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
    private static By moduleTitle, searchInp, clientsDataTable, page4Href, widgetContent, cellDataSpan;

    public EuroNews(WebDriver driver) {
        super(driver);

        moduleTitle = By.xpath(propAPI.getProperty("h1_title"));
        searchInp = By.cssSelector(propAPI.getProperty("inp_Search"));
        clientsDataTable = By.cssSelector(propAPI.getProperty("table_ClientsData"));
        page4Href = By.xpath(propAPI.getProperty("href_Page4"));
        widgetContent = By.xpath(propAPI.getProperty("content_Widget"));
        cellDataSpan = By.xpath(propAPI.getProperty("span_CellData"));
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

    public WebElement getClientsDataTable() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(clientsDataTable)));
            element = findElement(clientsDataTable);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getPage4Href() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(page4Href)));
            element = findElement(page4Href);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getWidgetContent() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(widgetContent)));
            element = findElement(widgetContent);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getCellDataSpan() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(cellDataSpan)));
            element = findElement(cellDataSpan);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return element;
    }

}
