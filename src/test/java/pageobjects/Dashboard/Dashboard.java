package pageobjects.Dashboard;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.PressReleases.EditPressRelease;
import pageobjects.PressReleases.PressReleases;
import pageobjects.Presentations.EditPresentation;
import pageobjects.Presentations.Presentations;
import pageobjects.Events.EditEvent;
import pageobjects.Events.Events;

public class Dashboard extends AbstractPageObject {
    Actions action = new Actions(driver);

    private final By addPressReleaseButton = By.xpath("//a[contains(@id,'hrefPressReleases')]");
    private final By addPresentationButton = By.xpath("//a[contains(@id,'hrefPresentations')]");
    private final By addEventButton = By.xpath("//a[contains(@id,'hrefEvents')]");
    private final By contentAdminMenuButton = By.xpath("//span[contains(text(),'Content Admin')]");
    private final By pressReleasesMenuButton = By.xpath("//a[contains(text(),'Press Releases')]/parent::li");
    private final By presentationsMenuButton = By.xpath("//a[contains(text(),'Presentations')]/parent::li");

    public Dashboard(WebDriver driver) {

        super(driver);
    }

    public String getURL() throws Exception {
        wait.until(ExpectedConditions.elementToBeClickable(addPressReleaseButton));
        return driver.getCurrentUrl();
    }

    public PressReleases pressReleases() {
        action.moveToElement(findElement(contentAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(pressReleasesMenuButton)));
        findElement(pressReleasesMenuButton).click();

        return new PressReleases(getDriver());
    }

    public EditPressRelease newPressRelease() {
        wait.until(ExpectedConditions.elementToBeClickable(addPressReleaseButton));
        findElement(addPressReleaseButton).click();
        return new EditPressRelease(getDriver());
    }

    public EditPresentation newPresentation() {
        wait.until(ExpectedConditions.elementToBeClickable(addPresentationButton));
        findElement(addPresentationButton).click();
        return new EditPresentation(getDriver());
    }

    public EditEvent newEvent() {
        wait.until(ExpectedConditions.elementToBeClickable(addEventButton));
        findElement(addEventButton).click();
        return new EditEvent(getDriver());
    }

    public Presentations presentations() {
        action.moveToElement(findElement(contentAdminMenuButton)).perform();
        wait.until(ExpectedConditions.visibilityOf(findElement(presentationsMenuButton)));
        findElement(presentationsMenuButton).click();
        return new Presentations(getDriver());
    }
}
