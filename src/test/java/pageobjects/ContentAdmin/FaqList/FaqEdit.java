package pageobjects.ContentAdmin.FaqList;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by philipsushkov on 2017-01-05.
 */

public class FaqEdit extends AbstractPageObject {
    private static By moduleTitle, questionsLabel, faqNameInput, activeCheckbox, saveAndSubmitButton;

    public FaqEdit(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));

        faqNameInput = By.xpath(propUIContentAdmin.getProperty("input_FaqName"));
        questionsLabel = By.xpath(propUIContentAdmin.getProperty("label_Questions"));

        activeCheckbox = By.xpath(propUIContentAdmin.getProperty("chk_Active"));

        saveAndSubmitButton = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
    }

    public String getTitle() {
        waitForElement(moduleTitle);
        return getText(moduleTitle);
    }

    public WebElement getFaqNameInput() {
        WebElement element = null;

        try {
            waitForElement(faqNameInput);
            element = findElement(faqNameInput);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public Boolean getChkBoxSet() {
        Boolean timeSet = false;

        try {
            waitForElement(activeCheckbox);
            findElement(activeCheckbox);

            timeSet = true;
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return timeSet;
    }

    public WebElement getQuestionsLabel() {
        WebElement element = null;

        try {
            waitForElement(questionsLabel);
            element = findElement(questionsLabel);
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
