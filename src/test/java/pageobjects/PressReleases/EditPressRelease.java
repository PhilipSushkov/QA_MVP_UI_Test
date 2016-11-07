package pageobjects.PressReleases;

import org.openqa.selenium.*;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
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
    //private final By radEContentBordered = By.className(" RadEContentBordered");
    //private final By htmlTextBox = By.xpath("//td[contains(@id,'RADeditor1Center')]");

    //private final By insertMenuButton = By.className("rrbTab").linkText("Insert");
    //private final By imageManagerButton = By.className("ImageManager");
    //private final By pngImages = By.xpath("//li[contains(@class,'rfeThumbList')]/a[contains(@title,'.PNG')]");
    //private final By imageFilename = By.id("selectedFileName");
    //private final By insertImageButton = By.id("InsertButton");
    private final By textArea = By.tagName("textarea");

    //private final By selectDocumentButton = By.xpath("//img[contains(@id,'btnDocument')]");
    //private final By pdfDocuments = By.xpath("//div[contains(@class,'rfeFileExtension') and contains(@class,'pdf')]");
    //private final By insertDocumentButton = By.id("InsertButton");
    private final By relatedDocument = By.xpath("//input[contains(@name,'txtDocument')]");

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
        //Select hourList = new Select(driver.findElement(pressReleaseHour));
        //hourList.selectByVisibleText(hour);
        driver.findElement(pressReleaseHour).sendKeys(hour);
        //pause(2000L);
        //Select minuteList = new Select(driver.findElement(pressReleaseMinute));
        //minuteList.selectByVisibleText(minute);
        driver.findElement(pressReleaseMinute).sendKeys(minute);
        //pause(2000L);
        //Select AMPMList = new Select(driver.findElement(pressReleaseAMPM));
        //AMPMList.selectByVisibleText(AMPM);
        driver.findElement(pressReleaseAMPM).sendKeys(AMPM);
        //pause(2000L);

        findElement(pressReleaseHeadline).sendKeys(headline);

        findElement(switchToHtml).click();

        driver.switchTo().frame(2);
        filenames[0] = "Q4Touch_LtBlue.png";
        findElement(textArea).sendKeys("<p>This is a test of a press release.</p><p><img src=\"/files/"+filenames[0]+"\" alt=\"\" style=\"\"></p>");
        driver.switchTo().defaultContent();
        pause(1000L);

        /*

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

        */

        /*
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
        */


        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement elemSrc =  driver.findElement(relatedDocument);
        filenames[1] = "bitcoin.pdf";
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "files/"+filenames[1]);

        // adding comments (necessary formality) and submitting
        findElement(updateComments).sendKeys("testing");
        pause(1000L);
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
