package pageobjects.ContentAdmin.Presentations;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2016-11-08.
 */

public class PresentationEdit extends AbstractPageObject {
    private static By moduleTitle, dateInput, timeHHSelect, timeMMSelect, timeAMSelect, saveAndSubmitButton;
    private static By titleInput, yourPageUrlLabel, changeUrlLink, tagsInput, radEditorFrame, presentationFileInput;
    private static By urlOverrideInput, thumbnailPathImage, thumbnailPathInput;
    private static By sendSlideShareCheckbox, openLinkCheckbox, exlLatestPagesCheckbox, activeCheckbox;
    private static By addNewSpeakersLink, speakerNameInput, speakerPositionInput, cancelSpeakerButton;
    private static By switchToHtml, textArea, seoNameLiteral, updateComments, deleteButton;

    private final String imageFile = "Q4Touch_LtBlue.png";
    private final String presentationFile = "bitcoin.pdf";

    public PresentationEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));

        dateInput = By.xpath(propUIContentAdmin.getProperty("input_PresentationDate"));
        timeHHSelect = By.xpath(propUIContentAdmin.getProperty("select_PresentationTimeHH"));
        timeMMSelect = By.xpath(propUIContentAdmin.getProperty("select_PresentationTimeMM"));
        timeAMSelect = By.xpath(propUIContentAdmin.getProperty("select_PresentationTimeAM"));

        titleInput = By.xpath(propUIContentAdmin.getProperty("input_Title"));
        yourPageUrlLabel = By.id(propUIContentAdmin.getProperty("label_YourPageUrl"));
        changeUrlLink = By.xpath(propUIContentAdmin.getProperty("href_ChangeUrl"));
        tagsInput = By.xpath(propUIContentAdmin.getProperty("input_Tags"));
        radEditorFrame = By.xpath(propUIContentAdmin.getProperty("frame_RadEditor"));
        presentationFileInput = By.xpath(propUIContentAdmin.getProperty("input_PresentationFile"));
        urlOverrideInput = By.xpath(propUIContentAdmin.getProperty("input_UrlOverride"));

        thumbnailPathImage = By.xpath(propUIContentAdmin.getProperty("img_ThumbnailPath"));
        thumbnailPathInput = By.xpath(propUIContentAdmin.getProperty("input_ThumbnailPath"));

        sendSlideShareCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_SendToSlideShare"));
        openLinkCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_OpenLink"));
        exlLatestPagesCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_ExlLatestPages"));
        activeCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_Active"));

        addNewSpeakersLink = By.xpath(propUIContentAdmin.getProperty("href_AddNewSpeakers"));
        speakerNameInput = By.xpath(propUIContentAdmin.getProperty("input_SpeakerName"));
        speakerPositionInput = By.xpath(propUIContentAdmin.getProperty("input_SpeakerPosition"));
        cancelSpeakerButton = By.xpath(propUIContentAdmin.getProperty("btn_SpeakerCancel"));

        switchToHtml = By.className(propUIContentAdmin.getProperty("html_SwitchTo"));
        textArea = By.tagName(propUIContentAdmin.getProperty("frame_Textarea"));
        seoNameLiteral = By.id(propUIContentAdmin.getProperty("span_seoNameLiteral"));
        updateComments = By.xpath(propUIContentAdmin.getProperty("txtarea_UpdateComments"));
        deleteButton = By.xpath(propUIContentAdmin.getProperty("btn_Delete"));

        saveAndSubmitButton = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public Boolean getDateTimeSet() {
        Boolean dateTimeSet = false;

        try {
            waitForElement(dateInput);
            findElements(dateInput);

            waitForElement(timeHHSelect);
            findElements(timeHHSelect);

            waitForElement(timeMMSelect);
            findElements(timeMMSelect);

            waitForElement(timeAMSelect);
            findElement(timeAMSelect);

            dateTimeSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return dateTimeSet;
    }

    public WebElement getTitleInput() {
        WebElement element = null;

        try {
            waitForElement(titleInput);
            element = findElement(titleInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getYourPageuUrlLabel() {
        WebElement element = null;

        try {
            waitForElement(yourPageUrlLabel);
            element = findElement(yourPageUrlLabel);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getChangeUrlLink() {
        WebElement element = null;

        try {
            waitForElement(changeUrlLink);
            element = findElement(changeUrlLink);
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

    public WebElement getRadEditorFrame() {
        WebElement element = null;

        try {
            waitForElement(radEditorFrame);
            element = findElement(radEditorFrame);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getPresentationFileInput() {
        WebElement element = null;

        try {
            waitForElement(presentationFileInput);
            element = findElements(presentationFileInput).get(0);
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
            findElement(thumbnailPathImage);

            waitForElement(thumbnailPathInput);
            findElement(thumbnailPathInput);

            thumbnailSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return thumbnailSet;
    }

    public WebElement getURLOverrideInput() {
        WebElement element = null;

        try {
            waitForElement(urlOverrideInput);
            element = findElement(urlOverrideInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public Boolean getRelatedFilesSet() {
        Boolean relatedFilesSet = false;

        try {
            waitForElement(presentationFileInput);

            findElements(presentationFileInput).get(1);
            findElements(presentationFileInput).get(2);
            findElements(presentationFileInput).get(3);

            relatedFilesSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return relatedFilesSet;
    }

    public Boolean getChkBoxSet() {
        Boolean timeSet = false;

        try {
            waitForElement(sendSlideShareCheckbox);
            findElement(sendSlideShareCheckbox);

            waitForElement(openLinkCheckbox);
            findElement(openLinkCheckbox);

            waitForElement(exlLatestPagesCheckbox);
            findElement(exlLatestPagesCheckbox);

            waitForElement(activeCheckbox);
            findElement(activeCheckbox);

            timeSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return timeSet;
    }

    public Boolean getSpeakersSet() {
        Boolean speakersSet = false;

        try {
            waitForElement(addNewSpeakersLink);
            findElement(addNewSpeakersLink).click();

            waitForElement(cancelSpeakerButton);

            findElement(speakerNameInput);
            findElement(speakerPositionInput);

            findElement(cancelSpeakerButton).click();

            waitForElement(addNewSpeakersLink);

            speakersSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return speakersSet;
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

    public String addNewPresentation(String headline, String date, String hour, String minute, String AMPM, String[] filenames) {

        //wait.until(ExpectedConditions.visibilityOf(findElement(yourPageUrlLabel)));
        waitForElement(yourPageUrlLabel);

        /*
        String newsPageURL = findElement(displayedURL).getText(); // gives URL like http://kinross.q4web.newtest/news-and-investors/news-releases/press-release-details/
        newsPageURL = newsPageURL.substring(0, newsPageURL.lastIndexOf("/"));
        newsPageURL = newsPageURL.substring(0, newsPageURL.lastIndexOf("/")); // substring repetition needed to remove the -details section of URL
        */

        // filling in mandatory date, time, and headline fields
        findElement(dateInput).sendKeys(date);
        findElement(timeHHSelect).sendKeys(hour);
        findElement(timeMMSelect).sendKeys(minute);
        findElement(timeAMSelect).sendKeys(AMPM);
        findElement(titleInput).sendKeys(headline);
        findElement(switchToHtml).click();

        driver.switchTo().frame(2);
        filenames[0] = imageFile;
        findElement(textArea).sendKeys("<p>This is a test of a presentation.</p><p><img src=\"/files/"+filenames[0]+"\" alt=\"\" style=\"\"></p>");
        driver.switchTo().defaultContent();
        pause(1000L);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement elemSrc =  findElement(presentationFileInput);
        filenames[1] = presentationFile;
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "files/"+filenames[1]);

        waitForElement(seoNameLiteral);

        String newsPageURL = findElement(yourPageUrlLabel).getText();

        // adding comments (necessary formality) and submitting
        findElement(updateComments).sendKeys("testing");
        pause(1000L);
        findElement(saveAndSubmitButton).click();

        return newsPageURL;
    }

    public Presentations changeHeadlineTo(String newHeadline) {
        wait.until(ExpectedConditions.visibilityOf(findElement(titleInput)));
        findElement(titleInput).clear();
        findElement(titleInput).sendKeys(newHeadline);
        findElement(updateComments).sendKeys("testing");

        findElement(saveAndSubmitButton).click();

        return new Presentations(getDriver());
    }

    public Presentations deletePresentation(){
        wait.until(ExpectedConditions.visibilityOf(findElement(updateComments)));
        findElement(updateComments).sendKeys("testing");
        findElement(deleteButton).click();

        return new Presentations(getDriver());
    }

}
