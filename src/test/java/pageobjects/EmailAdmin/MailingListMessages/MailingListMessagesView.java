package pageobjects.EmailAdmin.MailingListMessages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.PageObject;

import static specs.AbstractSpec.propUIEmailAdmin;

/**
 * Created by philipsushkov on 2016-12-30.
 */

public class MailingListMessagesView extends AbstractPageObject {
    private static By moduleTitle, grid, gridMailingListMessages, inputSearch, buttonSearch, selectMailingLists, linkYearDataList;
    private final Integer columnsNumber = 5;

    public MailingListMessagesView(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIEmailAdmin.getProperty("spanModule_Title"));
        grid = By.xpath(propUIEmailAdmin.getProperty("table_GridMailingListMessages"));
        gridMailingListMessages = By.xpath(propUIEmailAdmin.getProperty("table_GridItem"));
        inputSearch = By.xpath(propUIEmailAdmin.getProperty("input_Search"));
        buttonSearch = By.xpath(propUIEmailAdmin.getProperty("button_Search"));
        selectMailingLists = By.xpath(propUIEmailAdmin.getProperty("select_MailingLists"));
        linkYearDataList = By.xpath(propUIEmailAdmin.getProperty("link_YearDataList"));
    }


    public String getUrl() {
        return driver.getCurrentUrl();
    }


    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }


    public Integer getTitleQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)) );
        return findElement(grid).findElements(gridMailingListMessages).size()/columnsNumber;
    }


    public WebElement getSearchField() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(inputSearch)));
            element = findElement(inputSearch);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }


    public WebElement getSearchButton() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(buttonSearch)));
            element = findElement(buttonSearch);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getMailingListsSelect() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(selectMailingLists)));
            element = findElement(selectMailingLists);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getYearDataListLink() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(linkYearDataList)));
            element = findElement(linkYearDataList);
        } catch (PageObject.ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

}
