package pageobjects.SystemAdmin.PressReleaseFilterList;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISystemAdmin;

public class PressReleaseFilterEdit extends AbstractPageObject {
    private static By moduleTitle, filterNameInp, anyTermsTxt, allTermsTxt, notTermsTxt;
    private static By anyIconPlus, allIconPlus, notIconPlus;

    public PressReleaseFilterEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("h1_Title"));
        filterNameInp = By.xpath(propUISystemAdmin.getProperty("input_PRFilterName"));

        anyTermsTxt = By.xpath(propUISystemAdmin.getProperty("txt_ANYTerms"));
        allTermsTxt = By.xpath(propUISystemAdmin.getProperty("txt_ALLTerms"));
        notTermsTxt = By.xpath(propUISystemAdmin.getProperty("txt_NOTTerms"));

        anyIconPlus = By.xpath(propUISystemAdmin.getProperty("icon_PlusANY"));
        allIconPlus = By.xpath(propUISystemAdmin.getProperty("icon_PlusALL"));
        notIconPlus = By.xpath(propUISystemAdmin.getProperty("icon_PlusNOT"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getFilterNameInp() {
        WebElement element = null;

        try {
            waitForElement(filterNameInp);
            element = findElement(filterNameInp);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getANYTermsTxt() {
        WebElement element = null;

        try {
            waitForElement(anyTermsTxt);
            element = findElement(anyTermsTxt);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getALLTermsTxt() {
        WebElement element = null;

        try {
            waitForElement(allTermsTxt);
            element = findElement(allTermsTxt);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getNOTTermsTxt() {
        WebElement element = null;

        try {
            waitForElement(notTermsTxt);
            element = findElement(notTermsTxt);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getANYIconPlus() {
        WebElement element = null;

        try {
            waitForElement(anyIconPlus);
            element = findElement(anyIconPlus);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getALLIconPlus() {
        WebElement element = null;

        try {
            waitForElement(allIconPlus);
            element = findElement(allIconPlus);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e) {
        }

        return element;
    }

    public WebElement getNOTIconPlus() {
        WebElement element = null;

        try {
            waitForElement(notIconPlus);
            element = findElement(notIconPlus);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e) {
        }

        return element;
    }

}
