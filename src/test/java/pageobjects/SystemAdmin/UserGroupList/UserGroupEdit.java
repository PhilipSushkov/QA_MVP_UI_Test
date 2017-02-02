package pageobjects.SystemAdmin.UserGroupList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2016-12-19.
 */

public class UserGroupEdit extends AbstractPageObject {
    private static By moduleTitle, userGroupNameField, copyPermissionsFrom, saveButton;

    public UserGroupEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("spanModule_Title"));
        userGroupNameField = By.xpath(propUISystemAdmin.getProperty("input_UserGroupName"));
        copyPermissionsFrom = By.xpath(propUISystemAdmin.getProperty("select_CopyPermissionsFrom"));
        saveButton = By.xpath(propUISystemAdmin.getProperty("btn_Save"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getUserGroupNameInput() {
        WebElement element = null;

        try {
            waitForElement(userGroupNameField);
            element = findElement(userGroupNameField);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getCopyPermissionsFromSelect() {
        WebElement element = null;

        try {
            waitForElement(copyPermissionsFrom);
            element = findElement(copyPermissionsFrom);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
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
        }

        return element;
    }
}
