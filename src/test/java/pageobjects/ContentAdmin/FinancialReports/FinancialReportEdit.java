package pageobjects.ContentAdmin.FinancialReports;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2017-01-03.
 */

public class FinancialReportEdit extends AbstractPageObject {

    private static By moduleTitle, reportYearSelect, reportTypeSelect, coverImageInput, saveAndSubmitButton;
    private static By filingDateInput, tagsInput, activeCheckbox, addNewRelatedDocLink, documentsTable, saveOrderImage;

    public FinancialReportEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        reportYearSelect = By.xpath(propUIContentAdmin.getProperty("select_ReportYear"));
        reportTypeSelect = By.xpath(propUIContentAdmin.getProperty("select_ReportType"));
        coverImageInput = By.xpath(propUIContentAdmin.getProperty("input_CoverImage"));
        filingDateInput = By.xpath(propUIContentAdmin.getProperty("input_FilingDate"));
        tagsInput = By.xpath(propUIContentAdmin.getProperty("input_Tags"));
        activeCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_Active"));

        addNewRelatedDocLink = By.xpath(propUIContentAdmin.getProperty("href_AddNewRelatedDoc"));
        documentsTable = By.xpath(propUIContentAdmin.getProperty("table_Documents"));
        saveOrderImage = By.xpath(propUIContentAdmin.getProperty("img_SaveOrder"));

        saveAndSubmitButton = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getReportYearSelect() {
        WebElement element = null;

        try {
            waitForElement(reportYearSelect);
            element = findElement(reportYearSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getReportTypeSelect() {
        WebElement element = null;

        try {
            waitForElement(reportTypeSelect);
            element = findElement(reportTypeSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getCoverImageInput() {
        WebElement element = null;

        try {
            waitForElement(coverImageInput);
            element = findElement(coverImageInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getFilingDateInput() {
        WebElement element = null;

        try {
            waitForElement(filingDateInput);
            element = findElement(filingDateInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getTagsInput() {
        WebElement element = null;

        try {
            waitForElement(tagsInput);
            element = findElement(tagsInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getActiveCheckbox() {
        WebElement element = null;

        try {
            waitForElement(activeCheckbox);
            element = findElement(activeCheckbox);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getAddNewRelatedDocLink() {
        WebElement element = null;

        try {
            waitForElement(addNewRelatedDocLink);
            element = findElement(addNewRelatedDocLink);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getDocumentsTable() {
        WebElement element = null;

        try {
            waitForElement(documentsTable);
            element = findElement(documentsTable);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getSaveOrderImage() {
        WebElement element = null;

        try {
            waitForElement(saveOrderImage);
            element = findElement(saveOrderImage);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
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
        }

        return element;
    }

}
