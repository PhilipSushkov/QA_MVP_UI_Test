package pageobjects.ContentAdmin.DownloadList;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2017-01-04.
 */
public class DownloadEdit extends AbstractPageObject {

    private static By moduleTitle, downloadDateInput, downloadTypeSelect, downloadTitleTextarea, downloadDescriptionTextarea;
    private static By tagsInput, downloadPathRadio, downloadUrlRadio, downloadPathInput, saveAndSubmitButton;
    private static By thumbnailPathImage, thumbnailPathInput, iconPathImage, iconPathInput;

    public DownloadEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        downloadDateInput = By.xpath(propUIContentAdmin.getProperty("input_DownloadDate"));
        downloadTypeSelect = By.xpath(propUIContentAdmin.getProperty("select_DownloadType"));
        saveAndSubmitButton = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
        downloadTitleTextarea = By.xpath(propUIContentAdmin.getProperty("txtarea_DownloadTitle"));
        downloadDescriptionTextarea = By.xpath(propUIContentAdmin.getProperty("txtarea_DownloadDescription"));
        tagsInput = By.xpath(propUIContentAdmin.getProperty("input_Tags"));
        downloadPathRadio = By.xpath(propUIContentAdmin.getProperty("rdo_DownloadPath"));
        downloadUrlRadio = By.xpath(propUIContentAdmin.getProperty("rdo_DownloadUrl"));
        downloadPathInput = By.xpath(propUIContentAdmin.getProperty("input_DownloadPath"));

        thumbnailPathImage = By.xpath(propUIContentAdmin.getProperty("img_ImagePath"));
        thumbnailPathInput = By.xpath(propUIContentAdmin.getProperty("input_TxtPath"));
        iconPathImage = By.xpath(propUIContentAdmin.getProperty("img_ImagePath"));
        iconPathInput = By.xpath(propUIContentAdmin.getProperty("input_TxtPath"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getDownloadDateInput() {
        WebElement element = null;

        try {
            waitForElement(downloadDateInput);
            element = findElement(downloadDateInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getDownloadTypeSelect() {
        WebElement element = null;

        try {
            waitForElement(downloadTypeSelect);
            element = findElement(downloadTypeSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getDownloadTitleTextarea() {
        WebElement element = null;

        try {
            waitForElement(downloadTitleTextarea);
            element = findElement(downloadDateInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getDownloadDescriptionTextarea() {
        WebElement element = null;

        try {
            waitForElement(downloadDescriptionTextarea);
            element = findElement(downloadDescriptionTextarea);
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

    public Boolean getRadioDownloadTypeSet() {
        Boolean radioDownloadTypeSet = false;

        try {
            waitForElement(downloadPathRadio);
            findElement(downloadPathRadio);

            waitForElement(downloadUrlRadio);
            findElement(downloadUrlRadio);

            radioDownloadTypeSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return radioDownloadTypeSet;
    }

    public WebElement getDownloadPathInput() {
        WebElement element = null;

        try {
            waitForElement(downloadPathInput);
            element = findElement(downloadPathInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public Boolean getThumbnailSet() {
        Boolean thumbnailSet = false;

        try {
            waitForElement(thumbnailPathImage);
            findElements(thumbnailPathImage).get(0);

            waitForElement(thumbnailPathInput);
            findElements(thumbnailPathInput).get(0);

            thumbnailSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return thumbnailSet;
    }

    public Boolean getIconSet() {
        Boolean thumbnailSet = false;

        try {
            waitForElement(iconPathImage);
            findElements(iconPathImage).get(1);

            waitForElement(iconPathInput);
            findElements(iconPathInput).get(1);

            thumbnailSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return thumbnailSet;
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
