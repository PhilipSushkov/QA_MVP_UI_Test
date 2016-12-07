package pageobjects.SystemAdmin.UserList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by jasons on 2016-12-05.
 */
public class UserEdit extends AbstractPageObject {
    private final By moduleTitle = By.xpath("//span[contains(@class, 'AdminContent')]/h1/span[contains(@id,'ModuleTitle')]");
    private final By emailField = By.xpath("//input[contains(@id,'txtEmail')]");
    private final By userNameField = By.xpath("//input[contains(@id,'txtUserName')]");
    private final By changePasswordCheckbox = By.xpath("//input[contains(@id,'chkChangePassword')]");
    private final By passwordField = By.xpath("//input[contains(@id,'txtPassword')]");
    private final By systemAdministratorRole = By.xpath("//td[contains(.,'System Administrator')]/input");
    private final By activeCheckbox = By.xpath("//input[contains(@id,'chkActive')]");
    private final By saveButton = By.cssSelector("[value=Save]");
    private final By deleteButton = By.cssSelector("[value=Delete]");

    public UserEdit(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)));
        return findElement(moduleTitle).getText();
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

    public UserEdit changePasswordTo(String password){
        waitForElement(changePasswordCheckbox);
        checkIfUnchecked(changePasswordCheckbox);
        pause(500);
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
}
