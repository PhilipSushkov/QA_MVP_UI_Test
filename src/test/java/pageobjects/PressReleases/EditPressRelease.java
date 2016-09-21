package pageobjects.PressReleases;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

    private final By insertMenuButton = By.className("rrbTab").linkText("Insert");
    private final By imageManagerButton = By.className("ImageManager");
    private final By pngImages = By.xpath("//li[contains(@class,'rfeThumbList')]/a[contains(@title,'.PNG')]");
    private final By imageFilename = By.id("selectedFileName");
    private final By insertImageButton = By.id("InsertButton");

    private final By selectDocumentButton = By.xpath("//img[contains(@id,'btnDocument')]");
    private final By pdfDocuments = By.xpath("//div[contains(@class,'rfeFileExtension') and contains(@class,'pdf')]");
    private final By insertDocumentButton = By.id("InsertButton");

    private final By updateComments = By.xpath("//textarea[contains(@id,'txtComments')]");
    private final By deleteButton = By.xpath("//input[contains(@id,'btnDelete')]");
    private final By saveAndSubmit = By.xpath("//input[contains(@id,'btnSaveAndSubmit')]");

    public EditPressRelease(WebDriver driver) {
        super(driver);
    }

    public String addNewPressRelease(String headline, String date, String hour, String minute, String AMPM, String[] filenames) {
        // copying displayed URL of news page
        wait.until(ExpectedConditions.visibilityOf(findElement(displayedURL)));
        String newsPageURL = findElement(displayedURL).getText(); // gives URL like http://kinross.q4web.newtest/news-and-investors/news-releases/press-release-details/
        newsPageURL = newsPageURL.substring(0, newsPageURL.lastIndexOf("/"));
        newsPageURL = newsPageURL.substring(0, newsPageURL.lastIndexOf("/")); // substring repetition needed to remove the -details section of URL

        // filling in mandatory date, time, and headline fields
        findElement(pressReleaseDate).sendKeys(date);
        Select hourList = new Select(driver.findElement(pressReleaseHour));
        hourList.selectByVisibleText(hour);
        Select minuteList = new Select(driver.findElement(pressReleaseMinute));
        minuteList.selectByVisibleText(minute);
        Select AMPMList = new Select(driver.findElement(pressReleaseAMPM));
        AMPMList.selectByVisibleText(AMPM);
        findElement(pressReleaseHeadline).sendKeys(headline);

        // adding image
        findElement(insertMenuButton).click();
        findElement(imageManagerButton).click();
        driver.switchTo().frame("Window");
        findElements(pngImages).get(0).click();
        //waiting a second for the image to select
        try{Thread.sleep(1000);}
        catch(InterruptedException e){
            e.printStackTrace();
        }
        filenames[0] = findElement(imageFilename).getText();
        findElement(insertImageButton).click();
        driver.switchTo().defaultContent();

        // entering in body text
        action.sendKeys(Keys.RETURN).perform();
        action.sendKeys(Keys.RETURN).perform();
        action.sendKeys("This is a test of a press release.").perform();

        // adding attachment
        findElement(selectDocumentButton).click();
        driver.switchTo().frame("Window");
        findElements(pdfDocuments).get(0).click();
        //waiting a second for the document to select
        try{Thread.sleep(1000);}
        catch(InterruptedException e){
            e.printStackTrace();
        }
        filenames[1] = findElements(pdfDocuments).get(0).getText();
        findElement(insertDocumentButton).click();
        driver.switchTo().defaultContent();

        // adding comments (necessary formality) and submitting
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
