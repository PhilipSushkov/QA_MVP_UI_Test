package pageobjects.EmailAdmin.CategoryList;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIEmailAdmin;

/**
 * Created by philipsushkov on 2017-01-10.
 */

public class CategoryEdit extends AbstractPageObject {


    private static By moduleTitle, categoryNameInput, activeCheckbox, saveButton;

    public CategoryEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIEmailAdmin.getProperty("spanModule_Title"));

        categoryNameInput = By.xpath(propUIEmailAdmin.getProperty("input_CategoryName"));
        activeCheckbox = By.xpath(propUIEmailAdmin.getProperty("chk_Active"));

        saveButton = By.xpath(propUIEmailAdmin.getProperty("button_Save"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public Boolean getChkBoxSet() {
        Boolean chkBoxSet = false;

        try {
            waitForElement(activeCheckbox);
            findElement(activeCheckbox);

            chkBoxSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return chkBoxSet;
    }

    public WebElement getCategoryNameInput() {
        WebElement element = null;

        try {
            waitForElement(categoryNameInput);
            element = findElement(categoryNameInput);
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
