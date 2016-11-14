package pageobjects.SystemAdmin.PDFTemplateEdit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by philipsushkov on 2016-11-14.
 */

public class PDFTemplateEdit extends AbstractPageObject {
    private final By moduleTitle = By.xpath("//span[contains(@class, 'AdminContent')]/h1/span[contains(@id,'ModuleTitle')]");
    private final By frameHeaderRadEditor = By.xpath("//iframe[contains(@id, 'HeaderRadEditor')]");
    private final By frameBodyRadEditor = By.xpath("//iframe[contains(@id, 'BodyRadEditor')]");
    private final By frameFooterRadEditor = By.xpath("//iframe[contains(@id, 'FooterRadEditor')]");

    public PDFTemplateEdit(WebDriver driver) {
        super(driver);
    }

    public String getUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)));
        return findElement(moduleTitle).getText();
    }

    public WebElement getHeaderRadEditor() {
        wait.until(ExpectedConditions.visibilityOf(findElement(frameHeaderRadEditor)));
        return findElement(frameHeaderRadEditor);
    }

    public WebElement getBodyRadEditor() {
        wait.until(ExpectedConditions.visibilityOf(findElement(frameBodyRadEditor)));
        return findElement(frameBodyRadEditor);
    }

    public WebElement getFooterRadEditor() {
        wait.until(ExpectedConditions.visibilityOf(findElement(frameFooterRadEditor)));
        return findElement(frameFooterRadEditor);
    }

}
