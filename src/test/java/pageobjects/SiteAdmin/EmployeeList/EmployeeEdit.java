package pageobjects.SiteAdmin.EmployeeList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by andyp on 2017-05-29.
 */
public class EmployeeEdit extends AbstractPageObject {
    private static By moduleTitle, emailInput, passwordInput, firstNameInput;
    private static By lastNameInput, jobTitleInput, phoneInput, extensionInput;
    private static By cellPhoneInput, locationInput, photoInput;
    private static By saveBtn, activeChk;

    public EmployeeEdit(WebDriver driver) {
        
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));

        emailInput = By.xpath(propUISiteAdmin.getProperty("input_Email"));
        passwordInput = By.xpath(propUISiteAdmin.getProperty("input_Password"));
        firstNameInput = By.xpath(propUISiteAdmin.getProperty("input_FirstName"));
        lastNameInput = By.xpath(propUISiteAdmin.getProperty("input_LastName"));
        jobTitleInput = By.xpath(propUISiteAdmin.getProperty("input_JobTitle"));
        phoneInput = By.xpath(propUISiteAdmin.getProperty("input_Phone"));
        extensionInput = By.xpath(propUISiteAdmin.getProperty("input_Extension"));
        cellPhoneInput = By.xpath(propUISiteAdmin.getProperty("input_CellPhone"));
        locationInput = By.xpath(propUISiteAdmin.getProperty("input_Location"));
        photoInput = By.xpath(propUISiteAdmin.getProperty("input_Photo"));
        activeChk = By.xpath(propUISiteAdmin.getProperty("chk_Active"));

        saveBtn = By.xpath(propUISiteAdmin.getProperty("btn_Save"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getEmailInput() {
        WebElement element = null;

        try {
            waitForElement(emailInput);
            element = findElement(emailInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getPasswordInput() {
        WebElement element = null;

        try {
            waitForElement(passwordInput);
            element = findElement(passwordInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
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
        }

        return element;
    }

    public WebElement getPhoneInput() {
        WebElement element = null;

        try {
            waitForElement(phoneInput);
            element = findElement(phoneInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getExtensionInput() {
        WebElement element = null;

        try {
            waitForElement(extensionInput);
            element = findElement(extensionInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getCellPhoneInput() {
        WebElement element = null;

        try {
            waitForElement(cellPhoneInput);
            element = findElement(cellPhoneInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
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
        }

        return element;
    }

    public WebElement getPhotoInput() {
        WebElement element = null;

        try {
            waitForElement(photoInput);
            element = findElement(photoInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getactiveChk() {
        WebElement element = null;

        try {
            waitForElement(activeChk);
            element = findElement(activeChk);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getSaveButton() {
        WebElement element = null;

        try {
            waitForElement(saveBtn);
            element = findElement(saveBtn);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;

    }

}
