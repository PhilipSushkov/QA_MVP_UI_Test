package pageobjects.PageAdmin;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIPageAdmin;

/**
 * Created by philipsushkov on 2017-01-12.
 */

public class PageEdit extends AbstractPageObject {
    private static By moduleTitle, sectionWrapBtn, securityWrapBtn, sectionTitleInput, saveAndSubmitButton;
    private static By yourPageUrlLabel, changeUrlLink, pageTemplateSelect, parentPageSelect, saveAsTemplateBtn;
    private static By internalRd, externalRd, showInNavChk, openInNewWindowChk, templateNameInput;

    public PageEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIPageAdmin.getProperty("spanModule_Title"));
        saveAndSubmitButton = By.xpath(propUIPageAdmin.getProperty("btn_SaveAndSubmit"));
        sectionWrapBtn = By.xpath(propUIPageAdmin.getProperty("span_TabStripWrapSection"));
        securityWrapBtn = By.xpath(propUIPageAdmin.getProperty("span_TabStripWrapSecurity"));
        sectionTitleInput = By.xpath(propUIPageAdmin.getProperty("input_SectionTitle"));
        yourPageUrlLabel = By.xpath(propUIPageAdmin.getProperty("span_YourPageUrl"));
        changeUrlLink = By.xpath(propUIPageAdmin.getProperty("href_ChangeUrl"));
        pageTemplateSelect = By.xpath(propUIPageAdmin.getProperty("select_PageTemplate"));
        parentPageSelect = By.xpath(propUIPageAdmin.getProperty("select_ParentPage"));
        internalRd = By.xpath(propUIPageAdmin.getProperty("rd_Internal"));
        externalRd = By.xpath(propUIPageAdmin.getProperty("rd_External"));
        showInNavChk = By.xpath(propUIPageAdmin.getProperty("chk_ShowInNav"));
        openInNewWindowChk = By.xpath(propUIPageAdmin.getProperty("chk_OpenInNewWindow"));
        templateNameInput = By.xpath(propUIPageAdmin.getProperty("input_TemplateName"));
        saveAsTemplateBtn = By.xpath(propUIPageAdmin.getProperty("btn_SaveAsTemplate"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getSectionWrapBtn() {
        WebElement element = null;

        try {
            waitForElement(sectionWrapBtn);
            element = findElement(sectionWrapBtn);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getSecurityWrapBtn() {
        WebElement element = null;

        try {
            waitForElement(securityWrapBtn);
            element = findElement(securityWrapBtn);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getSectionTitleInput() {
        WebElement element = null;

        try {
            waitForElement(sectionTitleInput);
            element = findElement(sectionTitleInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getYourPageUrlLabel() {
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

    public WebElement getPageTemplateSelect() {
        WebElement element = null;

        try {
            waitForElement(pageTemplateSelect);
            element = findElement(pageTemplateSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getParentPageSelect() {
        WebElement element = null;

        try {
            waitForElement(parentPageSelect);
            element = findElement(parentPageSelect);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public Boolean getPageTypeRdSet() {
        Boolean timeSet = false;

        try {
            waitForElement(internalRd);
            findElement(internalRd);

            waitForElement(externalRd);
            findElement(externalRd);

            timeSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return timeSet;
    }

    public Boolean getChkBoxSet() {
        Boolean timeSet = false;

        try {
            waitForElement(showInNavChk);
            findElement(showInNavChk);

            waitForElement(openInNewWindowChk);
            findElement(openInNewWindowChk);

            timeSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return timeSet;
    }

    public WebElement getTemplateNameInput() {
        WebElement element = null;

        try {
            waitForElement(templateNameInput);
            element = findElement(templateNameInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getSaveAsTemplateBtn() {
        WebElement element = null;

        try {
            waitForElement(saveAsTemplateBtn);
            element = findElement(saveAsTemplateBtn);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
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

}
