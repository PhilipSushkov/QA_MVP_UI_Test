package pageobjects.ContentAdmin.Events;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

/**
 * Created by philipsushkov on 2016-11-09.
 */
public class EditEvent extends AbstractPageObject {

    private final By displayedURL = By.id("PageUrl");
    private final By seoNameLiteral = By.id("seoNameLiteral");

    private final By eventStartDate = By.xpath("//input[contains(@id, 'txtEventStartDate')]");
    private final By eventEndDate = By.xpath("//input[contains(@id, 'txtEventEndDate')]");
    private final By eventHours = By.xpath("//select[contains(@id,'ddlHour')]");
    private final By eventMinutes = By.xpath("//select[contains(@id,'ddlMinute')]");
    private final By eventAMPMs = By.xpath("//select[contains(@id,'ddlAMPM')]");
    private final By eventTimeZone = By.xpath("//select[contains(@id,'dlTimeZone')]");
    private final By eventTags = By.xpath("//input[contains(@id,'txtTags')]");
    private final By eventLocation = By.xpath("//input[contains(@id,'txtLocation')]");

    private final By eventHeadline = By.id("txtTitle");

    private final By switchToHtml = By.className("reMode_html");
    private final By textArea = By.tagName("textarea");

    private final String imageFile = "Q4Touch_LtBlue.png";

    private final By updateComments = By.xpath("//textarea[contains(@id,'txtComments')]");
    private final By deleteButton = By.xpath("//input[contains(@id,'btnDelete')]");
    private final By saveAndSubmit = By.xpath("//input[contains(@id,'btnSaveAndSubmit')]");

    public EditEvent(WebDriver driver) {
        super(driver);
    }

    public String addNewEvent(String headline, String date, String tommorrow, String hour, String minute, String AMPM, String timeZone, String tags, String location, String[] filenames) {

        wait.until(ExpectedConditions.visibilityOf(findElement(displayedURL)));


        findElement(eventStartDate).sendKeys(date);
        findElement(eventEndDate).sendKeys(tommorrow);
        findElements(eventHours).get(0).sendKeys(hour);
        findElements(eventHours).get(1).sendKeys(hour);
        findElements(eventMinutes).get(0).sendKeys(minute);
        findElements(eventMinutes).get(1).sendKeys(minute);
        findElement(eventTimeZone).sendKeys(timeZone);
        findElements(eventAMPMs).get(0).sendKeys(AMPM);
        findElements(eventAMPMs).get(1).sendKeys(AMPM);
        findElement(eventHeadline).sendKeys(headline);
        findElement(eventTags).sendKeys(tags);
        findElement(eventLocation).sendKeys(location);

        findElement(switchToHtml).click();

        driver.switchTo().frame(2);
        filenames[0] = imageFile;
        findElement(textArea).sendKeys("<p>This is a test of an event.</p><p><img src=\"/files/"+filenames[0]+"\" alt=\"\" style=\"\"></p>");
        driver.switchTo().defaultContent();
        pause(1000L);

        wait.until(ExpectedConditions.visibilityOf(findElement(seoNameLiteral)));

        // gives URL like http://chicagotest.q4web.com/English/Investors/Events/Event-Details/{year}/Test-Event---AAAA
        String newsPageURL = findElement(displayedURL).getText();

        // adding comments (necessary formality) and submitting
        findElement(updateComments).sendKeys("testing");
        pause(1000L);
        findElement(saveAndSubmit).click();

        return newsPageURL;

    }

    public Events changeHeadlineTo(String newHeadline) {
        wait.until(ExpectedConditions.visibilityOf(findElement(eventHeadline)));
        findElement(eventHeadline).clear();
        findElement(eventHeadline).sendKeys(newHeadline);
        findElement(updateComments).sendKeys("testing");

        findElement(saveAndSubmit).click();

        return new Events(getDriver());
    }

    public Events deleteEvent(){
        wait.until(ExpectedConditions.visibilityOf(findElement(updateComments)));
        findElement(updateComments).sendKeys("testing");
        findElement(deleteButton).click();

        return new Events(getDriver());
    }

}
