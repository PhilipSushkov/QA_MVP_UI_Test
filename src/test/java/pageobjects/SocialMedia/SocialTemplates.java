package pageobjects.SocialMedia;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by jasons on 2016-12-08.
 */
public class SocialTemplates extends AbstractPageObject {

    private final By settingsDialog = By.id("SMSettingBox");
    private final By settingsDialogTitle = By.cssSelector("#SMSettingBox .Title");
    private final By templateText = By.cssSelector("#SMSettingBox input[readonly]");
    private final By editTemplateButton = By.cssSelector("button.EditTemplate");
    private final By editableTemplateText = By.cssSelector(".Edit .TemplateValue");
    private final By saveTemplateButton = By.cssSelector("#SMSettingBox .SaveTemplate");
    private final By closeSocialTemplatesButton = By.className("fancybox-close");

    public SocialTemplates(WebDriver driver) {
        super(driver);
    }

    public boolean linkedInSocialTemplatesAreDisplayed(){
        waitForElement(settingsDialogTitle);
        return findElement(settingsDialog).isDisplayed() && findElement(settingsDialogTitle).getText().equals("LinkedIn Social Templates");
    }

    public boolean facebookSocialTemplatesAreDisplayed(){
        waitForElement(settingsDialogTitle);
        return findElement(settingsDialog).isDisplayed() && findElement(settingsDialogTitle).getText().equals("Facebook Social Templates");
    }

    public String getFirstTemplateText(){
        waitForElement(templateText);
        return findElement(templateText).getAttribute("value");
    }

    public SocialTemplates editFirstTemplate(){
        waitForElement(editTemplateButton);
        findElement(editTemplateButton).click();
        return this;
    }

    // checks that the editable template text is displayed
    public boolean editTemplateIsOpen(){
        waitForElement(editableTemplateText);
        return findElement(editableTemplateText).isDisplayed();
    }

    public String getEditableTemplateText(){
        waitForElement(editableTemplateText);
        return findElement(editableTemplateText).getAttribute("value");
    }

    public SocialTemplates editTemplateTo(String text){
        waitForElement(editableTemplateText);
        findElement(editableTemplateText).clear();
        findElement(editableTemplateText).sendKeys(text);
        return this;
    }

    public SocialTemplates saveTemplate(){
        waitForElement(saveTemplateButton);
        findElement(saveTemplateButton).click();
        pause(3000);
        return this;
    }

    public SocialMediaSummary closeSocialTemplates(){
        waitForElement(closeSocialTemplatesButton);
        findElement(closeSocialTemplatesButton).click();
        pause(3000);
        return new SocialMediaSummary(getDriver());
    }


}
