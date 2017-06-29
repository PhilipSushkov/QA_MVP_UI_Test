package pageobjects.ContentAdmin.PressReleases;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIContentAdmin;

public class PressReleaseEdit extends AbstractPageObject {
    private static By moduleTitle, dateInput, timeHHSelect, timeMMSelect, timeAMSelect, saveAndSubmitButton;
    private static By headlineInput, yourPageUrlLabel, changeUrlLink, categorySelect, tagsInput, radEditorFrame;
    private static By relatedDocInput, relatedProjSelect, urlOverrideInput, thumbnailPathImage, thumbnailPathInput;
    private static By openLinkCheckbox, exlLatestPagesCheckbox, activeCheckbox;
    private static By switchToHtml, textArea, seoNameLiteral, workflowState, updateComments, deleteButton;

    private final String imageFile = "Q4Touch_LtBlue.png";
    private final String relatedFile = "bitcoin.pdf";

    public PressReleaseEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));

        dateInput = By.xpath(propUIContentAdmin.getProperty("input_PressReleaseDate"));
        timeHHSelect = By.xpath(propUIContentAdmin.getProperty("select_PressReleaseTimeHH"));
        timeMMSelect = By.xpath(propUIContentAdmin.getProperty("select_PressReleaseTimeMM"));
        timeAMSelect = By.xpath(propUIContentAdmin.getProperty("select_PressReleaseTimeAM"));

        headlineInput = By.xpath(propUIContentAdmin.getProperty("input_Headline"));
        yourPageUrlLabel = By.xpath(propUIContentAdmin.getProperty("span_YourPageUrl"));
        changeUrlLink = By.xpath(propUIContentAdmin.getProperty("href_ChangeUrl"));
        categorySelect = By.xpath(propUIContentAdmin.getProperty("input_Category"));
        tagsInput = By.xpath(propUIContentAdmin.getProperty("input_Tags"));
        radEditorFrame = By.xpath(propUIContentAdmin.getProperty("frame_RadEditor"));
        relatedDocInput = By.xpath(propUIContentAdmin.getProperty("input_RelatedDoc"));

        thumbnailPathImage = By.xpath(propUIContentAdmin.getProperty("img_ThumbnailPath"));
        thumbnailPathInput = By.xpath(propUIContentAdmin.getProperty("input_ThumbnailPath"));

        relatedProjSelect = By.xpath(propUIContentAdmin.getProperty("select_RelatedProj"));
        urlOverrideInput = By.xpath(propUIContentAdmin.getProperty("input_UrlOverride"));

        openLinkCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_OpenLink"));
        exlLatestPagesCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_ExlLatestPages"));
        activeCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_Active"));

        switchToHtml = By.className(propUIContentAdmin.getProperty("html_SwitchTo"));
        textArea = By.tagName(propUIContentAdmin.getProperty("frame_Textarea"));
        seoNameLiteral = By.xpath(propUIContentAdmin.getProperty("span_seoNameLiteral"));
        updateComments = By.xpath(propUIContentAdmin.getProperty("txtarea_UpdateComments"));
        deleteButton = By.xpath(propUIContentAdmin.getProperty("btn_Delete"));


        workflowState = By.xpath(propUIContentAdmin.getProperty("span_WorkflowState"));
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
            waitForElement(headlineInput);
            element = findElement(headlineInput);
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

    public WebElement getCategorySelect() {
        WebElement element = null;

        try {
            waitForElement(categorySelect);
            element = findElement(categorySelect);
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

    public WebElement getRelatedDocInput() {
        WebElement element = null;

        try {
            waitForElement(relatedDocInput);
            element = findElement(relatedDocInput);
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

    public WebElement getRelatedProjSelect() {
        WebElement element = null;

        try {
            waitForElement(relatedProjSelect);
            element = findElement(relatedProjSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
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

    public Boolean getChkBoxSet() {
        Boolean timeSet = false;

        try {
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

    public WebElement getWorkflowState() {
        try {
            waitForElement(workflowState);
            return findElement(workflowState);
        } catch (ElementNotFoundException e) {
        } catch (ElementNotVisibleException e) {
        } catch (TimeoutException e) {
        }

        return null;
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

    public String addNewPressRelease(String headline, String date, String hour, String minute, String AMPM, String[] filenames) {
        // copying displayed URL of news page
        //wait.until(ExpectedConditions.visibilityOf(findElement(displayedURL)));
        waitForElement(yourPageUrlLabel);
        //String newsPageURL = findElement(yourPageUrlLabel).getText(); // gives URL like http://kinross.q4web.newtest/news-and-investors/news-releases/press-release-details/
        //newsPageURL = newsPageURL.substring(0, newsPageURL.lastIndexOf("/"));
        //newsPageURL = newsPageURL.substring(0, newsPageURL.lastIndexOf("/")); // substring repetition needed to remove the -details section of URL

        // filling in mandatory date, time, and headline fields
        findElement(timeHHSelect).sendKeys(hour);
        findElement(dateInput).sendKeys(date);
        findElement(timeMMSelect).sendKeys(minute);
        findElement(timeAMSelect).sendKeys(AMPM);
        findElement(headlineInput).sendKeys(headline);

        findElement(switchToHtml).click();

        driver.switchTo().frame(2);
        filenames[0] = imageFile;
        findElement(textArea).sendKeys("<p>This is a test of a press release.</p><p><img src=\"/files/"+filenames[0]+"\" alt=\"\" style=\"\"></p>");
        driver.switchTo().defaultContent();
        pause(1000L);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement elemSrc =  driver.findElement(relatedDocInput);
        filenames[1] = relatedFile;
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "files/"+filenames[1]);

        waitForElement(seoNameLiteral);

        String newsPageURL = findElement(yourPageUrlLabel).getText();

        // adding comments (necessary formality) and submitting
        findElement(updateComments).sendKeys("testing");
        pause(1000L);
        findElement(saveAndSubmitButton).click();

        return newsPageURL;
    }

    public PressReleases changeHeadlineTo(String newHeadline) {
        waitForElement(headlineInput);
        findElement(headlineInput).clear();
        findElement(headlineInput).sendKeys(newHeadline);
        findElement(updateComments).sendKeys("testing");
        findElement(saveAndSubmitButton).click();

        return new PressReleases(getDriver());
    }

    public PressReleases deletePressRelease(){
        waitForElement(updateComments);
        findElement(updateComments).sendKeys("testing");
        findElement(deleteButton).click();

        return new PressReleases(getDriver());
    }

}
