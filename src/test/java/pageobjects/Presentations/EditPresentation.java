package pageobjects.Presentations;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import pageobjects.AbstractPageObject;

/**
 * Created by philipsushkov on 2016-11-08.
 */
public class EditPresentation extends AbstractPageObject {
    //Actions action = new Actions(driver);

    private final By displayedURL = By.id("PageUrl");

    private final By presentationDate = By.id("txtPresentationDate");
    private final By presentationHour = By.xpath("//select[contains(@id,'ddlHour')]");
    private final By presentationMinute = By.xpath("//select[contains(@id,'ddlMinute')]");
    private final By presentationAMPM = By.xpath("//select[contains(@id,'ddlAMPM')]");
    private final By presentationHeadline = By.id("txtTitle");

    private final By switchToHtml = By.className("reMode_html");
    private final By textArea = By.tagName("textarea");

    private final By relatedDocument = By.xpath("//input[contains(@name,'txtDocument')]");

    private final By updateComments = By.xpath("//textarea[contains(@id,'txtComments')]");
    private final By deleteButton = By.xpath("//input[contains(@id,'btnDelete')]");
    private final By saveAndSubmit = By.xpath("//input[contains(@id,'btnSaveAndSubmit')]");

    private final String imageFile = "Q4Touch_LtBlue.png";
    private final String presentationFile = "bitcoin.pdf";


    public EditPresentation(WebDriver driver) {
        super(driver);
    }

    public String addNewPresentation(String headline, String date, String hour, String minute, String AMPM, String[] filenames) {

        wait.until(ExpectedConditions.visibilityOf(findElement(displayedURL)));

        /*
        String newsPageURL = findElement(displayedURL).getText(); // gives URL like http://kinross.q4web.newtest/news-and-investors/news-releases/press-release-details/

        newsPageURL = newsPageURL.substring(0, newsPageURL.lastIndexOf("/"));
        newsPageURL = newsPageURL.substring(0, newsPageURL.lastIndexOf("/")); // substring repetition needed to remove the -details section of URL
        */



        // filling in mandatory date, time, and headline fields
        findElement(presentationDate).sendKeys(date);
        driver.findElement(presentationHour).sendKeys(hour);
        driver.findElement(presentationMinute).sendKeys(minute);
        driver.findElement(presentationAMPM).sendKeys(AMPM);
        findElement(presentationHeadline).sendKeys(headline);
        findElement(switchToHtml).click();

        driver.switchTo().frame(2);
        filenames[0] = imageFile;
        findElement(textArea).sendKeys("<p>This is a test of a presentation.</p><p><img src=\"/files/"+filenames[0]+"\" alt=\"\" style=\"\"></p>");
        driver.switchTo().defaultContent();
        pause(1000L);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement elemSrc =  driver.findElement(relatedDocument);
        filenames[1] = presentationFile;
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "files/"+filenames[1]);

        String newsPageURL = findElement(displayedURL).getText();

        // adding comments (necessary formality) and submitting
        findElement(updateComments).sendKeys("testing");
        pause(1000L);
        findElement(saveAndSubmit).click();

        return newsPageURL;
    }

    public Presentations changeHeadlineTo(String newHeadline) {
        wait.until(ExpectedConditions.visibilityOf(findElement(presentationHeadline)));
        findElement(presentationHeadline).clear();
        findElement(presentationHeadline).sendKeys(newHeadline);
        findElement(updateComments).sendKeys("testing");

        findElement(saveAndSubmit).click();

        return new Presentations(getDriver());
    }

    public Presentations deletePresentation(){
        wait.until(ExpectedConditions.visibilityOf(findElement(updateComments)));
        findElement(updateComments).sendKeys("testing");
        findElement(deleteButton).click();

        return new Presentations(getDriver());
    }

}
