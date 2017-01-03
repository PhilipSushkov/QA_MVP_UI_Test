package pageobjects.ContentAdmin.Events;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2017-01-03.
 */

public class EventWebcastEdit extends AbstractPageObject {

    private static By moduleTitle, eventBtnWrap, webcastParticipantsBtnWrap, saveAndSubmitButton;
    private static By startDateInput, startTimeHHSelect, startTimeMMSelect, startTimeAMSelect, startTimeZoneSelect;
    private static By endDateInput, endTimeHHSelect, endTimeMMSelect, endTimeAMSelect;
    private static By titleInput, yourPageuUrlLabel, changeUrlLink, tagsInput, locationInput, switchToHtml, radEditorFrame;
    private static By isWebcastCheckbox, openLinkCheckbox, exlLatestPagesCheckbox, activeCheckbox;
    private static By urlOverrideInput, relatedPressReleaseSelect, relatedFinancialReportSelect, financialPeriodQSelect, financialPeriodYSelect, relatedPresentationsSelect, relatedWebcastInput;
    private static By addNewSpeakersLink, speakerNameInput, speakerPositionInput, cancelSpeakerButton;
    private static By addNewAttachmentsLink, attachmentTitleInput, attachmentTypeListSelect, attachmentPathInput, cancelAttachmentButton;
    private static By textArea, seoNameLiteral, updateComments, deleteButton;
    private final String imageFile = "Q4Touch_LtBlue.png";

    public EventWebcastEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));

        eventBtnWrap = By.xpath(propUIContentAdmin.getProperty("btn_EventWrap"));
        webcastParticipantsBtnWrap = By.xpath(propUIContentAdmin.getProperty("btn_WebcastParticipantsWrap"));

        startDateInput = By.xpath(propUIContentAdmin.getProperty("input_StartDate"));
        startTimeHHSelect = By.xpath(propUIContentAdmin.getProperty("select_StartTimeHH"));
        startTimeMMSelect = By.xpath(propUIContentAdmin.getProperty("select_StartTimeMM"));
        startTimeAMSelect = By.xpath(propUIContentAdmin.getProperty("select_StartTimeAM"));
        startTimeZoneSelect = By.xpath(propUIContentAdmin.getProperty("select_StartTimeZone"));

        endDateInput = By.xpath(propUIContentAdmin.getProperty("input_EndDate"));
        endTimeHHSelect = By.xpath(propUIContentAdmin.getProperty("select_EndTimeHH"));
        endTimeMMSelect = By.xpath(propUIContentAdmin.getProperty("select_EndTimeMM"));
        endTimeAMSelect = By.xpath(propUIContentAdmin.getProperty("select_EndTimeAM"));

        titleInput = By.xpath(propUIContentAdmin.getProperty("input_Title"));
        yourPageuUrlLabel = By.id(propUIContentAdmin.getProperty("label_YourPageuUrl"));
        changeUrlLink = By.xpath(propUIContentAdmin.getProperty("href_ChangeUrl"));
        tagsInput = By.xpath(propUIContentAdmin.getProperty("input_Tags"));
        locationInput = By.xpath(propUIContentAdmin.getProperty("input_Location"));
        switchToHtml = By.className(propUIContentAdmin.getProperty("html_SwitchTo"));
        radEditorFrame = By.xpath(propUIContentAdmin.getProperty("frame_RadEditor"));

        isWebcastCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_IsWebcast"));
        openLinkCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_OpenLink"));
        exlLatestPagesCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_ExlLatestPages"));
        activeCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_Active"));

        urlOverrideInput = By.xpath(propUIContentAdmin.getProperty("input_UrlOverride"));
        relatedPressReleaseSelect = By.xpath(propUIContentAdmin.getProperty("select_RelatedPressRelease"));
        relatedFinancialReportSelect = By.xpath(propUIContentAdmin.getProperty("select_RelatedFinancialReport"));
        financialPeriodQSelect = By.xpath(propUIContentAdmin.getProperty("select_FinancialPeriodQ"));
        financialPeriodYSelect = By.xpath(propUIContentAdmin.getProperty("select_FinancialPeriodY"));
        relatedPresentationsSelect = By.xpath(propUIContentAdmin.getProperty("select_RelatedPresentations"));
        relatedWebcastInput = By.xpath(propUIContentAdmin.getProperty("input_RelatedWebcast"));

        addNewSpeakersLink = By.xpath(propUIContentAdmin.getProperty("href_AddNewSpeakers"));
        speakerNameInput = By.xpath(propUIContentAdmin.getProperty("input_SpeakerName"));
        speakerPositionInput = By.xpath(propUIContentAdmin.getProperty("input_SpeakerPosition"));
        cancelSpeakerButton = By.xpath(propUIContentAdmin.getProperty("btn_SpeakerCancel"));

        addNewAttachmentsLink = By.xpath(propUIContentAdmin.getProperty("href_AddNewAttachments"));
        attachmentTitleInput = By.xpath(propUIContentAdmin.getProperty("input_AttachmentTitle"));
        attachmentTypeListSelect = By.xpath(propUIContentAdmin.getProperty("select_AttachmentTypeList"));
        attachmentPathInput = By.xpath(propUIContentAdmin.getProperty("input_AttachmentPath"));
        cancelAttachmentButton = By.xpath(propUIContentAdmin.getProperty("btn_AttachmentCancel"));

        saveAndSubmitButton = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));

        textArea = By.tagName(propUIContentAdmin.getProperty("frame_Textarea"));
        seoNameLiteral = By.id(propUIContentAdmin.getProperty("span_seoNameLiteral"));
        updateComments = By.xpath(propUIContentAdmin.getProperty("txtarea_UpdateComments"));
        deleteButton = By.xpath(propUIContentAdmin.getProperty("btn_Delete"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getEventBtnWrap() {
        WebElement element = null;

        try {
            waitForElement(eventBtnWrap);
            element = findElement(eventBtnWrap);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getWebcastParticipantsBtnWrap() {
        WebElement element = null;

        try {
            waitForElement(webcastParticipantsBtnWrap);
            element = findElement(webcastParticipantsBtnWrap);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getStartDateInput() {
        WebElement element = null;

        try {
            waitForElement(startDateInput);
            element = findElement(startDateInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public Boolean getStartTimeSet() {
        Boolean timeSet = false;

        try {
            waitForElement(startTimeHHSelect);
            findElements(startTimeHHSelect).get(0);

            waitForElement(startTimeMMSelect);
            findElements(startTimeMMSelect).get(0);

            waitForElement(startTimeAMSelect);
            findElements(startTimeAMSelect).get(0);

            waitForElement(startTimeZoneSelect);
            findElement(startTimeZoneSelect);

            timeSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return timeSet;
    }

    public WebElement getEndDateInput() {
        WebElement element = null;

        try {
            waitForElement(endDateInput);
            element = findElement(endDateInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public Boolean getEndTimeSet() {
        Boolean timeSet = false;

        try {
            waitForElement(endTimeHHSelect);
            findElements(endTimeHHSelect).get(1);

            waitForElement(endTimeMMSelect);
            findElements(endTimeMMSelect).get(1);

            waitForElement(endTimeAMSelect);
            findElements(endTimeAMSelect).get(1);

            timeSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return timeSet;
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
            waitForElement(yourPageuUrlLabel);
            element = findElement(yourPageuUrlLabel);
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

    public WebElement getLocationInput() {
        WebElement element = null;

        try {
            waitForElement(locationInput);
            element = findElement(locationInput);
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

    public Boolean getChkBoxSet() {
        Boolean timeSet = false;

        try {
            waitForElement(isWebcastCheckbox);
            findElement(isWebcastCheckbox);

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

    public WebElement getUrlOverrideInput() {
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

    public Boolean getRelatedSet() {
        Boolean relatedSet = false;

        try {
            waitForElement(relatedPressReleaseSelect);
            findElement(relatedPressReleaseSelect);

            waitForElement(relatedFinancialReportSelect);
            findElement(relatedFinancialReportSelect);

            waitForElement(financialPeriodQSelect);
            findElement(financialPeriodQSelect);

            waitForElement(financialPeriodYSelect);
            findElement(financialPeriodYSelect);

            waitForElement(relatedPresentationsSelect);
            findElement(relatedPresentationsSelect);

            waitForElement(relatedWebcastInput);
            findElement(relatedWebcastInput);

            relatedSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return relatedSet;
    }

    public Boolean getSpeakersSet() {
        Boolean speakersSet = false;

        //try {
            //System.out.println(findElement(By.xpath("//a[contains(text(), 'Add New')]/parent::div/parent::h2/parent::div[contains(@ng-controller, 'Speaker')]")).getText());
            waitForElement(addNewSpeakersLink);
            findElement(addNewSpeakersLink).click();

            waitForElement(cancelSpeakerButton);

            findElement(speakerNameInput);
            findElement(speakerPositionInput);

            findElement(cancelSpeakerButton).click();

            waitForElement(addNewSpeakersLink);

            speakersSet = true;
        /* } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        } */

        return speakersSet;
    }

    public Boolean getAttachmentsSet() {
        Boolean attachmentsSet = false;

        try {
            waitForElement(addNewAttachmentsLink);
            findElement(addNewAttachmentsLink).click();

            waitForElement(cancelAttachmentButton);

            findElement(attachmentTitleInput);
            findElement(attachmentTypeListSelect);
            findElement(attachmentPathInput);

            findElement(cancelAttachmentButton).click();

            waitForElement(addNewAttachmentsLink);

            attachmentsSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return attachmentsSet;
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

    public String addNewEvent(String headline, String date, String tommorrow, String hour, String minute, String AMPM, String timeZone, String tags, String location, String[] filenames) {

        wait.until(ExpectedConditions.visibilityOf(findElement(yourPageuUrlLabel)));


        findElement(startDateInput).sendKeys(date);
        findElement(endDateInput).sendKeys(tommorrow);
        findElements(startTimeHHSelect).get(0).sendKeys(hour);
        findElements(endTimeHHSelect).get(1).sendKeys(hour);
        findElements(startTimeMMSelect).get(0).sendKeys(minute);
        findElements(endTimeMMSelect).get(1).sendKeys(minute);
        findElement(startTimeZoneSelect).sendKeys(timeZone);
        findElements(startTimeAMSelect).get(0).sendKeys(AMPM);
        findElements(endTimeAMSelect).get(1).sendKeys(AMPM);
        findElement(titleInput).sendKeys(headline);
        findElement(tagsInput).sendKeys(tags);
        findElement(locationInput).sendKeys(location);

        findElement(switchToHtml).click();

        driver.switchTo().frame(2);
        filenames[0] = imageFile;
        findElement(textArea).sendKeys("<p>This is a test of an event.</p><p><img src=\"/files/"+filenames[0]+"\" alt=\"\" style=\"\"></p>");
        driver.switchTo().defaultContent();
        pause(1000L);

        wait.until(ExpectedConditions.visibilityOf(findElement(seoNameLiteral)));

        // gives URL like http://chicagotest.q4web.com/English/Investors/Events/Event-Details/{year}/Test-Event---AAAA
        String newsPageURL = findElement(yourPageuUrlLabel).getText();

        // adding comments (necessary formality) and submitting
        findElement(updateComments).sendKeys("testing");
        pause(1000L);
        findElement(saveAndSubmitButton).click();

        return newsPageURL;

    }

    public Events changeHeadlineTo(String newHeadline) {
        wait.until(ExpectedConditions.visibilityOf(findElement(titleInput)));
        findElement(titleInput).clear();
        findElement(titleInput).sendKeys(newHeadline);
        findElement(updateComments).sendKeys("testing");

        findElement(saveAndSubmitButton).click();

        return new Events(getDriver());
    }

    public Events deleteEvent() {
        wait.until(ExpectedConditions.visibilityOf(findElement(updateComments)));
        findElement(updateComments).sendKeys("testing");
        findElement(deleteButton).click();

        return new Events(getDriver());
    }

}
