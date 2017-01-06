package pageobjects.ContentAdmin.JobPostingList;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2017-01-06.
 */

public class JobPostingEdit extends AbstractPageObject {

    private static By moduleTitle, regionSelect, countrySelect, locationInput, saveAndSubmitButton;
    private static By divisionSelect, jobTitleInput, jobTypeSelect, jobFunctionInput, refNoInput;
    private static By managerEmailInput, openingDateInput, closingDateInput, summaryFrame, documentPathInput;

    public JobPostingEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));

        regionSelect = By.xpath(propUIContentAdmin.getProperty("select_Region"));
        countrySelect = By.xpath(propUIContentAdmin.getProperty("select_Country"));
        locationInput = By.xpath(propUIContentAdmin.getProperty("input_Location"));
        divisionSelect = By.xpath(propUIContentAdmin.getProperty("select_Division"));
        jobTitleInput = By.xpath(propUIContentAdmin.getProperty("input_JobTitle"));
        jobTypeSelect = By.xpath(propUIContentAdmin.getProperty("select_JobType"));
        jobFunctionInput = By.xpath(propUIContentAdmin.getProperty("input_JobFunction"));
        refNoInput = By.xpath(propUIContentAdmin.getProperty("input_RefNo"));
        managerEmailInput = By.xpath(propUIContentAdmin.getProperty("input_ManagerEmail"));
        openingDateInput = By.xpath(propUIContentAdmin.getProperty("input_OpeningDate"));
        closingDateInput = By.xpath(propUIContentAdmin.getProperty("input_ClosingDate"));
        summaryFrame = By.xpath(propUIContentAdmin.getProperty("frame_Summary"));
        documentPathInput = By.xpath(propUIContentAdmin.getProperty("input_DocumentPath"));

        saveAndSubmitButton = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getRegionSelect() {
        WebElement element = null;

        try {
            waitForElement(regionSelect);
            element = findElement(regionSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getCountrySelect() {
        WebElement element = null;

        try {
            waitForElement(countrySelect);
            element = findElement(countrySelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getLocationInput() {
        WebElement element = null;

        try {
            waitForElement(locationInput);
            element = findElement(locationInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getDivisionSelect() {
        WebElement element = null;

        try {
            waitForElement(divisionSelect);
            element = findElement(divisionSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getJobTitleInput() {
        WebElement element = null;

        try {
            waitForElement(jobTitleInput);
            element = findElement(jobTitleInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getJobTypeSelect() {
        WebElement element = null;

        try {
            waitForElement(jobTypeSelect);
            element = findElement(jobTypeSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getJobFunctionInput() {
        WebElement element = null;

        try {
            waitForElement(jobFunctionInput);
            element = findElement(jobFunctionInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getRefNoInput() {
        WebElement element = null;

        try {
            waitForElement(refNoInput);
            element = findElement(refNoInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getManagerEmailInput() {
        WebElement element = null;

        try {
            waitForElement(managerEmailInput);
            element = findElement(managerEmailInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getOpeningDateInput() {
        WebElement element = null;

        try {
            waitForElement(openingDateInput);
            element = findElement(openingDateInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getClosingDateInput() {
        WebElement element = null;

        try {
            waitForElement(closingDateInput);
            element = findElement(closingDateInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getSummaryFrame() {
        WebElement element = null;

        try {
            waitForElement(summaryFrame);
            element = findElement(summaryFrame);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getDocumentPathInput() {
        WebElement element = null;

        try {
            waitForElement(documentPathInput);
            element = findElement(documentPathInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getSaveAndSubmitButton() {
        WebElement element = null;

        try {
            waitForElement(saveAndSubmitButton);
            element = findElement(saveAndSubmitButton);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

}
