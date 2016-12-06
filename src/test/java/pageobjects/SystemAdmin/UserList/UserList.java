package pageobjects.SystemAdmin.UserList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by philipsushkov on 2016-11-10.
 */

public class UserList extends AbstractPageObject {
    private final By moduleTitle = By.xpath("//span[contains(@class, 'AdminContent')]/h1/span[contains(@id,'ModuleTitle')]");
    private final By grid = By.xpath("//table[contains(@id, 'dataGridUsers')]");
    private final By gridUserName = By.xpath("//td[contains(@class,'DataGridItemBorder')]");
    private final Integer columnsNumber = 4;
    private final By addNewButton = By.className("ButtonAddNew");

    private final By editButton = By.cssSelector(".DataGridItemBorderLeft input");
    private final By userName = By.cssSelector(".DataGridItemBorder:nth-child(2)");
    private final By userActive = By.cssSelector(".DataGridItemBorder:nth-child(4)");

    public UserList(WebDriver driver) {
        super(driver);
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)));
        return findElement(moduleTitle).getText();
    }

    public Integer getUserNameQuantity() {
        wait.until(ExpectedConditions.visibilityOf(findElement(grid)));
        return findElement(grid).findElements(gridUserName).size()/columnsNumber;
    }

    public UserEdit addNewUser(){
        waitForElement(addNewButton);
        findElement(addNewButton).click();
        return new UserEdit(getDriver());
    }

    public int getIndexOfUsername(String desiredUsername){
        waitForElement(userName);
        List<WebElement> usernames = findElements(userName);
        for (int i=0; i<usernames.size(); i++){
            if (usernames.get(i).getText().equalsIgnoreCase(desiredUsername)){
                return i;
            }
        }
        return -1;
    }

    public boolean userIsActive(int index){
        waitForElement(userActive);
        if (findElements(userActive).get(index).getText().equalsIgnoreCase("true")){
            return true;
        }
        else if (findElements(userActive).get(index).getText().equalsIgnoreCase("false")){
            return false;
        }
        else {
            fail("Active status for index "+index+" displayed as invalid value "+findElements(userActive).get(index).getText());
            return false;
        }
    }

    public UserEdit editUser(int index){
        waitForElement(editButton);
        findElements(editButton).get(index).click();
        return new UserEdit(getDriver());
    }

}
