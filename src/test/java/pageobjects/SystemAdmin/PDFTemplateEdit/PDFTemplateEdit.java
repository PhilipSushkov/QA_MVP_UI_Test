package pageobjects.SystemAdmin.PDFTemplateEdit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by philipsushkov on 2016-11-14.
 */

public class PDFTemplateEdit extends AbstractPageObject {
    private static By moduleTitle, frameHeaderRadEditor, frameBodyRadEditor, frameFooterRadEditor;

    public PDFTemplateEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISystemAdmin.getProperty("spanModule_Title"));
        frameHeaderRadEditor = By.xpath(propUISystemAdmin.getProperty("frame_HeaderRadEditor"));
        frameBodyRadEditor = By.xpath(propUISystemAdmin.getProperty("frame_BodyRadEditor"));
        frameFooterRadEditor = By.xpath(propUISystemAdmin.getProperty("frame_FooterRadEditor"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getHeaderRadEditor() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(frameHeaderRadEditor)));
            element = findElement(frameHeaderRadEditor);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }


    public WebElement getBodyRadEditor() {
        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(frameBodyRadEditor)));
            element = findElement(frameBodyRadEditor);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }


    public WebElement getFooterRadEditor() {

        WebElement element = null;

        try {
            wait.until(ExpectedConditions.visibilityOf(findElement(frameFooterRadEditor)));
            element = findElement(frameFooterRadEditor);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        }

        return element;
    }


}
