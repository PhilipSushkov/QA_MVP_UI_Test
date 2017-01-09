package pageobjects.EmailAdmin.Subscribers;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIEmailAdmin;

/**
 * Created by philipsushkov on 2017-01-09.
 */

public class SubscriberEdit extends AbstractPageObject {

    private static By moduleTitle, emailAddressInput, firstNameInput, lastNameInput, companyInput;
    private static By titleInput, activeCheckbox, validatedCheckbox, address1Input, saveButton, categoriesChk;
    private static By address2Input, cityInput, provinceInput, postalCodeInput, countrySelect, mailingListsChk;
    private static By regionInput, telephoneNoInput, faxNoInput, heardOfFromSelect, notesTextarea;

    public SubscriberEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIEmailAdmin.getProperty("spanDivModule_Title"));

        emailAddressInput = By.xpath(propUIEmailAdmin.getProperty("input_EmailAddress"));
        firstNameInput = By.xpath(propUIEmailAdmin.getProperty("input_FirstName"));
        lastNameInput = By.xpath(propUIEmailAdmin.getProperty("input_LastName"));
        companyInput = By.xpath(propUIEmailAdmin.getProperty("input_Company"));
        titleInput = By.xpath(propUIEmailAdmin.getProperty("input_Title"));

        activeCheckbox = By.xpath(propUIEmailAdmin.getProperty("chk_Active"));
        validatedCheckbox = By.xpath(propUIEmailAdmin.getProperty("chk_Validated"));

        titleInput = By.xpath(propUIEmailAdmin.getProperty("input_Title"));
        titleInput = By.xpath(propUIEmailAdmin.getProperty("input_Title"));
        titleInput = By.xpath(propUIEmailAdmin.getProperty("input_Title"));
        titleInput = By.xpath(propUIEmailAdmin.getProperty("input_Title"));
        titleInput = By.xpath(propUIEmailAdmin.getProperty("input_Title"));

        address1Input = By.xpath(propUIEmailAdmin.getProperty("input_Address1"));
        address2Input = By.xpath(propUIEmailAdmin.getProperty("input_Address2"));
        cityInput = By.xpath(propUIEmailAdmin.getProperty("input_City"));
        provinceInput = By.xpath(propUIEmailAdmin.getProperty("input_Province"));
        postalCodeInput = By.xpath(propUIEmailAdmin.getProperty("input_PostalCode"));

        countrySelect = By.xpath(propUIEmailAdmin.getProperty("select_Country"));
        regionInput = By.xpath(propUIEmailAdmin.getProperty("input_Region"));
        telephoneNoInput = By.xpath(propUIEmailAdmin.getProperty("input_TelephoneNo"));
        faxNoInput = By.xpath(propUIEmailAdmin.getProperty("input_FaxNo"));
        heardOfFromSelect = By.xpath(propUIEmailAdmin.getProperty("select_HeardOfFrom"));

        notesTextarea = By.xpath(propUIEmailAdmin.getProperty("txtarea_Notes"));
        mailingListsChk = By.xpath(propUIEmailAdmin.getProperty("chk_MailingLists"));
        categoriesChk = By.xpath(propUIEmailAdmin.getProperty("chk_Category"));

        saveButton = By.xpath(propUIEmailAdmin.getProperty("button_Save"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getEmailAddressInput() {
        WebElement element = null;

        try {
            waitForElement(emailAddressInput);
            element = findElement(emailAddressInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getFirstNameInput() {
        WebElement element = null;

        try {
            waitForElement(firstNameInput);
            element = findElement(firstNameInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getLastNameInput() {
        WebElement element = null;

        try {
            waitForElement(lastNameInput);
            element = findElement(lastNameInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getCompanyInput() {
        WebElement element = null;

        try {
            waitForElement(companyInput);
            element = findElement(companyInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getTitleInput() {
        WebElement element = null;

        try {
            waitForElement(titleInput);
            element = findElement(titleInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public Boolean getActiveValidChkSet() {
        Boolean chkBoxSet = false;

        try {
            waitForElement(activeCheckbox);
            findElement(activeCheckbox);

            waitForElement(validatedCheckbox);
            findElement(validatedCheckbox);

            chkBoxSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return chkBoxSet;
    }

    public WebElement getAddress1Input() {
        WebElement element = null;

        try {
            waitForElement(address1Input);
            element = findElement(address1Input);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getAddress2Input() {
        WebElement element = null;

        try {
            waitForElement(address2Input);
            element = findElement(address2Input);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }
    public WebElement getCityInput() {
        WebElement element = null;

        try {
            waitForElement(cityInput);
            element = findElement(cityInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getProvinceInput() {
        WebElement element = null;

        try {
            waitForElement(provinceInput);
            element = findElement(provinceInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getPostalCodeInput() {
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

    public WebElement getCountrySelect() {
        WebElement element = null;

        try {
            waitForElement(postalCodeInput);
            element = findElement(postalCodeInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getRegionInput() {
        WebElement element = null;

        try {
            waitForElement(regionInput);
            element = findElement(regionInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getTelephoneNoInput() {
        WebElement element = null;

        try {
            waitForElement(telephoneNoInput);
            element = findElement(telephoneNoInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getFaxNoInput() {
        WebElement element = null;

        try {
            waitForElement(faxNoInput);
            element = findElement(faxNoInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getHeardOfFromSelect() {
        WebElement element = null;

        try {
            waitForElement(heardOfFromSelect);
            element = findElement(heardOfFromSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getNotesTextarea() {
        WebElement element = null;

        try {
            waitForElement(notesTextarea);
            element = findElement(notesTextarea);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getMailingListsChk() {
        WebElement element = null;

        try {
            waitForElement(mailingListsChk);
            element = findElement(mailingListsChk);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getCategoriesChk() {
        WebElement element = null;

        try {
            waitForElement(categoriesChk);
            element = findElement(categoriesChk);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getSaveButton() {
        WebElement element = null;

        try {
            waitForElement(saveButton);
            element = findElement(saveButton);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

}
