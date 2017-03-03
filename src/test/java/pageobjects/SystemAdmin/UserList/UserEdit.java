package pageobjects.SystemAdmin.UserList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by jasons on 2016-12-05.
 */
public class UserEdit extends AbstractPageObject {
    private static By moduleTitle, emailField, userNameField, changePasswordCheckbox, passwordField;
    private static By systemAdministratorRole, activeCheckbox, saveButton, deleteButton;
    private static final long DEFAULT_PAUSE = 1000;

    public UserEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("spanModule_Title"));
        emailField = By.xpath(propUISystemAdmin.getProperty("input_Email"));
        userNameField = By.xpath(propUISystemAdmin.getProperty("input_UserName2"));
        changePasswordCheckbox = By.xpath(propUISystemAdmin.getProperty("chk_ChangePassword"));
        passwordField = By.xpath(propUISystemAdmin.getProperty("input_Password"));
        systemAdministratorRole = By.xpath(propUISystemAdmin.getProperty("chk_SystemAdmin"));
        activeCheckbox = By.xpath(propUISystemAdmin.getProperty("chk_Active"));
        saveButton = By.xpath(propUISystemAdmin.getProperty("btn_Save"));
        deleteButton = By.xpath(propUISystemAdmin.getProperty("btn_Delete"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public UserEdit fillNewUser(String username, String password){
        String email = username + "@q4inc.com";
        waitForElement(emailField);
        findElement(emailField).sendKeys(email);
        findElement(userNameField).sendKeys(username);
        findElement(passwordField).sendKeys(password);
        findElement(systemAdministratorRole).click();
        return this;
    }

    public UserEdit deactivateUser(){
        waitForElement(activeCheckbox);
        if (findElement(activeCheckbox).isSelected()){
            findElement(activeCheckbox).click();
        }
        return this;
    }

    public UserEdit reactivateUser(){
        waitForElement(activeCheckbox);
        checkIfUnchecked(activeCheckbox);
        return this;
    }

    public UserEdit changePasswordTo(String password) throws InterruptedException {
        waitForElement(changePasswordCheckbox);
        checkIfUnchecked(changePasswordCheckbox);
        Thread.sleep(DEFAULT_PAUSE);
        findElement(passwordField).sendKeys(password);
        return this;
    }

    public UserList saveUser(){
        waitForElement(saveButton);
        findElement(saveButton).click();
        return new UserList(getDriver());
    }

    public UserList deleteUser(){
        waitForElement(deleteButton);
        findElement(deleteButton).click();
        return new UserList(getDriver());
    }

    public WebElement getEmailInput() {
        WebElement element = null;

        try {
            waitForElement(emailField);
            element = findElement(emailField);
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

    public Boolean createUsers() throws InterruptedException {
        int userNum = 20;

        for(int i=0; i<=userNum; i++) {
            findElement(emailField).clear();
            findElement(emailField).sendKeys("admintest"+Integer.toString(i)+"@gmail.com");

            findElement(userNameField).clear();
            findElement(userNameField).sendKeys("admintest"+Integer.toString(i));

            findElement(passwordField).clear();
            findElement(passwordField).sendKeys("qwerty@01");

            findElement(By.xpath("//input[contains(@id, 'chkRolesList_2')]")).click();
            findElement(By.xpath("//input[contains(@id, 'chkRolesList_3')]")).click();
            findElement(By.xpath("//input[contains(@id, 'chkSiteList_0')]")).click();

            findElement(saveButton).click();
            Thread.sleep(DEFAULT_PAUSE*2);

            waitForElement(By.xpath("//input[contains(@id, 'btnAddNew')]"));
            findElement(By.xpath("//input[contains(@id, 'btnAddNew')]")).click();
        }


        return true;
    }
}
