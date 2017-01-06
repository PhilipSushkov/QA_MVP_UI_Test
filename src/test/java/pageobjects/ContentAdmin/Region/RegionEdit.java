package pageobjects.ContentAdmin.Region;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2017-01-06.
 */

public class RegionEdit extends AbstractPageObject {

    private static By moduleTitle, regionNameInput, globalOperationSelect, saveAndSubmitButton;
    private static By tagsInput, bodyFrame, hideReportsCheckbox, activeCheckbox;

    public RegionEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("span_Title"));

        regionNameInput = By.xpath(propUIContentAdmin.getProperty("input_RegionName"));
        globalOperationSelect = By.xpath(propUIContentAdmin.getProperty("select_GlobalOperation"));
        tagsInput = By.xpath(propUIContentAdmin.getProperty("input_Tags"));
        bodyFrame = By.xpath(propUIContentAdmin.getProperty("frame_Body"));

        hideReportsCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_HideReports"));
        activeCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_Active"));

        saveAndSubmitButton = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getRegionNameInput() {
        WebElement element = null;

        try {
            waitForElement(regionNameInput);
            element = findElement(regionNameInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getGlobalOperationSelect() {
        WebElement element = null;

        try {
            waitForElement(globalOperationSelect);
            element = findElement(globalOperationSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
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
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getBodyFrame() {
        WebElement element = null;

        try {
            waitForElement(bodyFrame);
            element = findElement(bodyFrame);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public Boolean getChkBoxSet() {
        Boolean chkBoxSet = false;

        try {
            waitForElement(hideReportsCheckbox);
            findElement(hideReportsCheckbox);

            waitForElement(activeCheckbox);
            findElement(activeCheckbox);

            chkBoxSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return chkBoxSet;
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
