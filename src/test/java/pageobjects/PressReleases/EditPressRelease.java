package pageobjects.PressReleases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import pageobjects.AbstractPageObject;
import pageobjects.PressReleases.PressReleases;
import org.openqa.selenium.support.ui.Select;

public class EditPressRelease extends AbstractPageObject {
    Actions action = new Actions(driver);

    private final By displayedURL = By.id("ParentUrl");
    private final By pressReleaseDate = By.id("txtPressReleaseDate");
    private final By pressReleaseHour = By.xpath("//select[contains(@id,'ddlHour')]");
    private final By pressReleaseMinute = By.xpath("//select[contains(@id,'ddlMinute')]");
    private final By pressReleaseAMPM = By.xpath("//select[contains(@id,'ddlAMPM')]");
    private final By pressReleaseHeadline = By.id("txtHeadline");
    private final By switchToHtml = By.className("reMode_html");
    private final By htmlTextBox = By.xpath("//td[contains(@id,'RADeditor1Center')]");
    private final By updateComments = By.xpath("//textarea[contains(@id,'txtComments')]");
    private final By deleteButton = By.xpath("//input[contains(@id,'btnDelete')]");
    private final By saveAndSubmit = By.xpath("//input[contains(@id,'btnSaveAndSubmit')]");

    public EditPressRelease(WebDriver driver) {
        super(driver);
    }

    public String addNewPressRelease(String headline, String date, String hour, String minute, String AMPM) {
        wait.until(ExpectedConditions.visibilityOf(findElement(displayedURL)));
        String newsPageURL = findElement(displayedURL).getText(); // gives URL like http://kinross.q4web.newtest/news-and-investors/news-releases/press-release-details/
        newsPageURL = newsPageURL.substring(0, newsPageURL.lastIndexOf("/"));
        newsPageURL = newsPageURL.substring(0, newsPageURL.lastIndexOf("/")); // substring repetition needed to remove the -details section of URL
        findElement(pressReleaseDate).sendKeys(date);
        Select hourList = new Select(driver.findElement(pressReleaseHour));
        hourList.selectByVisibleText(hour);
        Select minuteList = new Select(driver.findElement(pressReleaseMinute));
        minuteList.selectByVisibleText(minute);
        Select AMPMList = new Select(driver.findElement(pressReleaseAMPM));
        AMPMList.selectByVisibleText(AMPM);
        findElement(pressReleaseHeadline).sendKeys(headline);
        findElement(switchToHtml).click();
        findElement(htmlTextBox).click();
        action.sendKeys("This is a test of a press release.").perform();
        findElement(updateComments).sendKeys("testing");

        findElement(saveAndSubmit).click();

        return newsPageURL;
    }

    public PressReleases changeHeadlineTo(String newHeadline) {
        wait.until(ExpectedConditions.visibilityOf(findElement(pressReleaseHeadline)));
        findElement(pressReleaseHeadline).clear();
        findElement(pressReleaseHeadline).sendKeys(newHeadline);
        findElement(updateComments).sendKeys("testing");

        findElement(saveAndSubmit).click();

        return new PressReleases(getDriver());
    }

    public PressReleases deletePressRelease(){
        wait.until(ExpectedConditions.visibilityOf(findElement(updateComments)));
        findElement(updateComments).sendKeys("testing");
        findElement(deleteButton).click();

        return new PressReleases(getDriver());
    }

    }
