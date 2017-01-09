package pageobjects.EmailAdmin.SystemMessages;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIEmailAdmin;

/**
 * Created by philipsushkov on 2017-01-09.
 */

public class SystemMessageEdit extends AbstractPageObject {

    private static By moduleTitle, systemMessageNameInput, descriptionInput, fromInput, subjectInput;
    private static By hideFooterCheckbox, sysMsgRd, flowEmailRd, resetEmailRd, approvalEmailRd, saveButton;
    private static By formMailRd, checkSocialMediaRd, subscriptionSelect, urlLabel, bodyFrame;

    public SystemMessageEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIEmailAdmin.getProperty("spanModule_Title"));

        systemMessageNameInput = By.xpath(propUIEmailAdmin.getProperty("input_SystemMessageName"));
        descriptionInput = By.xpath(propUIEmailAdmin.getProperty("input_Description"));
        fromInput = By.xpath(propUIEmailAdmin.getProperty("input_From"));
        subjectInput = By.xpath(propUIEmailAdmin.getProperty("input_Subject"));

        hideFooterCheckbox = By.xpath(propUIEmailAdmin.getProperty("chk_HideFooter"));
        sysMsgRd = By.xpath(propUIEmailAdmin.getProperty("rd_SysMsg"));
        flowEmailRd = By.xpath(propUIEmailAdmin.getProperty("rd_FlowEmail"));
        resetEmailRd = By.xpath(propUIEmailAdmin.getProperty("rd_ResetEmail"));
        approvalEmailRd = By.xpath(propUIEmailAdmin.getProperty("rd_ApprovalEmail"));
        formMailRd = By.xpath(propUIEmailAdmin.getProperty("rd_FormMail"));
        checkSocialMediaRd = By.xpath(propUIEmailAdmin.getProperty("rd_CheckSocialMedia"));

        subscriptionSelect = By.xpath(propUIEmailAdmin.getProperty("select_Subscription"));
        urlLabel = By.xpath(propUIEmailAdmin.getProperty("label_Url"));
        bodyFrame = By.xpath(propUIEmailAdmin.getProperty("frame_Body"));

        saveButton = By.xpath(propUIEmailAdmin.getProperty("button_Save"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getSystemMessageNameInput() {
        WebElement element = null;

        try {
            waitForElement(systemMessageNameInput);
            element = findElement(systemMessageNameInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getDescriptionInput() {
        WebElement element = null;

        try {
            waitForElement(descriptionInput);
            element = findElement(descriptionInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getSubjectInput() {
        WebElement element = null;

        try {
            waitForElement(subjectInput);
            element = findElement(subjectInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getFromInput() {
        WebElement element = null;

        try {
            waitForElement(fromInput);
            element = findElement(fromInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public Boolean getChkBoxSet() {
        Boolean chkBoxSet = false;

        try {
            waitForElement(hideFooterCheckbox);
            findElement(hideFooterCheckbox);

            chkBoxSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return chkBoxSet;
    }

    public Boolean getTypeRdSet() {
        Boolean chkBoxSet = false;

        try {
            waitForElement(sysMsgRd);
            findElement(sysMsgRd);

            waitForElement(flowEmailRd);
            findElement(flowEmailRd);

            waitForElement(resetEmailRd);
            findElement(resetEmailRd);

            waitForElement(approvalEmailRd);
            findElement(approvalEmailRd);

            waitForElement(formMailRd);
            findElement(formMailRd);

            waitForElement(checkSocialMediaRd);
            findElement(checkSocialMediaRd);

            chkBoxSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return chkBoxSet;
    }

    public Boolean getSubscriptionSelect() {
        Boolean chkBoxSet = false;

        try {
            waitForElement(subscriptionSelect);
            findElement(subscriptionSelect);

            chkBoxSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return chkBoxSet;
    }

    public Boolean getUrlLabel() {
        Boolean chkBoxSet = false;

        try {
            waitForElement(urlLabel);
            findElement(urlLabel);

            chkBoxSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return chkBoxSet;
    }

    public Boolean getBodyFrame() {
        Boolean chkBoxSet = false;

        try {
            waitForElement(bodyFrame);
            findElement(bodyFrame);

            chkBoxSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return chkBoxSet;
    }

    public WebElement getSaveButton() {
        WebElement element = null;

        try {
            waitForElement(saveButton);
            element = findElement(saveButton);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

}
