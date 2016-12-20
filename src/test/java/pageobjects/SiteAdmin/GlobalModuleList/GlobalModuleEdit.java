package pageobjects.SiteAdmin.GlobalModuleList;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by philipsushkov on 2016-12-20.
 */

public class GlobalModuleEdit extends AbstractPageObject {
    private static By moduleTitle, moduleTitleField, moduleDefinitionSelect, moduleTypeSelect, regionNameSelect, saveAndSubmitButton;

    public GlobalModuleEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanSection_Title"));
        moduleTitleField = By.xpath(propUISiteAdmin.getProperty("input_ModuleTitle"));
        moduleDefinitionSelect = By.xpath(propUISiteAdmin.getProperty("select_ModuleDefinition"));
        moduleTypeSelect = By.xpath(propUISiteAdmin.getProperty("select_ModuleType"));
        regionNameSelect = By.xpath(propUISiteAdmin.getProperty("select_RegionName"));
        saveAndSubmitButton = By.xpath(propUISiteAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getModuleTitleInput() {
        WebElement element = null;

        try {
            waitForElement(moduleTitleField);
            element = findElement(moduleTitleField);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getModuleDefinitionSelect() {
        WebElement element = null;

        try {
            waitForElement(moduleDefinitionSelect);
            element = findElement(moduleDefinitionSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getModuleTypeSelect() {
        WebElement element = null;

        try {
            waitForElement(moduleTypeSelect);
            element = findElement(moduleTypeSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }

    public WebElement getRegionNameSelect() {
        WebElement element = null;

        try {
            waitForElement(regionNameSelect);
            element = findElement(regionNameSelect);
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
