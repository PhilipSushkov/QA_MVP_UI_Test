package pageobjects.ContentAdmin.RegulatoryFiling;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2017-01-05.
 */

public class RegulatoryFilingEdit extends AbstractPageObject {

    private static By moduleTitle, dateInput, formInput, formDescInput, saveAndSubmitButton;
    private static By issuerInput, formGroupInput, sizeInput, pagesInput;
    private static By htmlLinkInput, docLinkInput, pdfLinkInput, xlsLinkInput;

    public RegulatoryFilingEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));

        dateInput = By.xpath(propUIContentAdmin.getProperty("input_Date"));
        formInput = By.xpath(propUIContentAdmin.getProperty("input_Form"));
        formDescInput = By.xpath(propUIContentAdmin.getProperty("input_FormDesc"));
        issuerInput = By.xpath(propUIContentAdmin.getProperty("input_Issuer"));
        formGroupInput = By.xpath(propUIContentAdmin.getProperty("input_FormGroup"));
        sizeInput = By.xpath(propUIContentAdmin.getProperty("input_Size"));
        pagesInput = By.xpath(propUIContentAdmin.getProperty("input_Pages"));

        htmlLinkInput = By.xpath(propUIContentAdmin.getProperty("input_HtmlLink"));
        docLinkInput = By.xpath(propUIContentAdmin.getProperty("input_DocLink"));
        pdfLinkInput = By.xpath(propUIContentAdmin.getProperty("input_PdfLink"));
        xlsLinkInput = By.xpath(propUIContentAdmin.getProperty("input_XlsLink"));

        saveAndSubmitButton = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getDateInput() {
        WebElement element = null;

        try {
            waitForElement(dateInput);
            element = findElement(dateInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getFormInput() {
        WebElement element = null;

        try {
            waitForElement(formInput);
            element = findElement(formInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getFormDescInput() {
        WebElement element = null;

        try {
            waitForElement(formDescInput);
            element = findElement(formDescInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getIssuerInput() {
        WebElement element = null;

        try {
            waitForElement(issuerInput);
            element = findElement(issuerInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getFormGroupInput() {
        WebElement element = null;

        try {
            waitForElement(formGroupInput);
            element = findElement(formGroupInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getSizeInput() {
        WebElement element = null;

        try {
            waitForElement(sizeInput);
            element = findElement(sizeInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getPagesInput() {
        WebElement element = null;

        try {
            waitForElement(pagesInput);
            element = findElement(pagesInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public Boolean getLinkInputSet() {
        Boolean linkSet = false;

        try {
            waitForElement(htmlLinkInput);
            findElement(htmlLinkInput);

            waitForElement(docLinkInput);
            findElement(docLinkInput);

            waitForElement(pdfLinkInput);
            findElement(pdfLinkInput);

            waitForElement(xlsLinkInput);
            findElement(xlsLinkInput);

            linkSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return linkSet;
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
