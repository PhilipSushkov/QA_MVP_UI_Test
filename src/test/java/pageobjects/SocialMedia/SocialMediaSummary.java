package pageobjects.SocialMedia;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static org.junit.Assert.fail;

/**
 * Created by jasons on 2016-12-07.
 */
public class SocialMediaSummary extends AbstractPageObject {

    private final By moduleTitle = By.xpath("//span[contains(@class, 'AdminContent')]/h1/span[contains(@id,'ModuleTitle')]");
    private final By settingsDialog = By.id("SMSettingBox");

    // LinkedIn section
    private final By linkedInStatusIndicator = By.cssSelector(".LinkedIn .ActiveAccount");
        //css -> background-image contains /unchecked.png (if not setup), /checked.png (if setup), or /disabled.png (if disabled)
    private final By linkedInStatusMessage = By.xpath("//span[contains(@id,'lblLinkedInMessage')]");
    private final By linkedInAccountName = By.cssSelector(".LinkedIn .ProfileLink");
    private final By linkedInCompany = By.xpath("//a[contains(@id,'hpLinkedInCompany')]");
    private final By linkedInFollowers = By.xpath("//span[contains(@id,'lblLinkedInCompanyFollowers')]");
    private final By linkedInAuthorizeButton = By.cssSelector(".LinkedIn [value=Authorize]");
    private final By linkedInDeAuthorizeButton = By.cssSelector(".LinkedIn .DeAuthorize");
    private final By linkedInDisableButton = By.cssSelector(".LinkedIn [value=Disable]");
    private final By linkedInEnableButton = By.cssSelector(".LinkedIn [value=Enable]");
    private final By linkedInReAuthorizeButton = By.cssSelector(".LinkedIn [value=Re-Authorize]");
    private final By linkedInSettingsButton = By.cssSelector(".LinkedIn .Settings");
    private final By linkedInCompanyRadioSelector = By.xpath("//input[contains(@id,'rblLinkedInCompanyList')]");
    private final By linkedInCompanySelectButton = By.cssSelector(".LinkedIn [value=Select]");

    // Facebook section
    private final By facebookStatusIndicator = By.cssSelector(".Facebook .ActiveAccount");
    //css -> background-image contains /unchecked.png (if not setup), /checked.png (if setup), or /disabled.png (if disabled)
    private final By facebookStatusMessage = By.xpath("//span[contains(@id,'lblFacebookMessage')]");
    private final By facebookAccountName = By.cssSelector(".Facebook .ProfileLink");
    private final By facebookPage = By.xpath("//a[contains(@id,'hpFacebookPage')]");
    private final By facebookFans = By.xpath("//span[contains(@id,'lblFacebookLikesCount')]");
    private final By facebookAuthorizeButton = By.cssSelector(".Facebook [value=Authorize]");
    private final By facebookDeAuthorizeButton = By.cssSelector(".Facebook .DeAuthorize");
    private final By facebookDisableButton = By.cssSelector(".Facebook [value=Disable]");
    private final By facebookEnableButton = By.cssSelector(".Facebook [value=Enable]");
    private final By facebookReAuthorizeButton = By.cssSelector(".Facebook [value=Re-Authorize]");
    private final By facebookSettingsButton = By.cssSelector(".Facebook .Settings");
    private final By facebookPageRadioSelector = By.xpath("//input[contains(@id,'rblFacebookPages')]");
    private final By facebookPageSelectButton = By.cssSelector(".Facebook [value=Select]");

    public SocialMediaSummary(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        wait.until(ExpectedConditions.visibilityOf(findElement(moduleTitle)));
        return findElement(moduleTitle).getText();
    }

    public boolean socialTemplatesIsClosed(){
        waitForElement(settingsDialog);
        return !findElement(settingsDialog).isDisplayed();
    }

    // LINKEDIN SECTION METHODS
    
    public String getLinkedInStatusMessage(){
        if (!doesElementExist(linkedInStatusMessage) || !findElement(linkedInStatusMessage).isDisplayed()){
            return "";
        }
        return findElement(linkedInStatusMessage).getText();
    }

    public LinkedInAuthorization authorizeLinkedInAccount(){
        waitForElement(linkedInAuthorizeButton);
        findElement(linkedInAuthorizeButton).click();
        return new LinkedInAuthorization(getDriver());
    }

    public LinkedInAuthorization reAuthorizeLinkedInAccount(){
        waitForElement(linkedInReAuthorizeButton);
        findElement(linkedInReAuthorizeButton).click();
        return new LinkedInAuthorization(getDriver());
    }

    public SocialMediaSummary chooseFirstLinkedInCompany(){
        waitForElement(linkedInCompanyRadioSelector);
        findElement(linkedInCompanyRadioSelector).click();
        findElement(linkedInCompanySelectButton).click();
        return this;
    }

    public SocialMediaSummary disableLinkedInAccount(){
        waitForElement(linkedInDisableButton);
        findElement(linkedInDisableButton).click();
        return this;
    }

    public SocialMediaSummary enableLinkedInAccount(){
        waitForElement(linkedInEnableButton);
        findElement(linkedInEnableButton).click();
        return this;
    }

    public SocialMediaSummary deAuthorizeLinkedInAccount(){
        waitForElement(linkedInDeAuthorizeButton);
        findElement(linkedInDeAuthorizeButton).click();
        Alert confirmation = driver.switchTo().alert();
        confirmation.accept();
        driver.switchTo().defaultContent();
        return this;
    }

    public SocialTemplates openLinkedInSettings(){
        waitForElement(linkedInSettingsButton);
        findElement(linkedInSettingsButton).click();
        pause(3000);
        return new SocialTemplates(getDriver());
    }

    public String getLinkedInAccountName(){
        if (!doesElementExist(linkedInAccountName) || !findElement(linkedInAccountName).isDisplayed()){
            fail("LinkedIn account name is not displayed.");
        }
        return findElement(linkedInAccountName).getText();
    }

    public String getLinkedInCompany(){
        if (!doesElementExist(linkedInCompany) || !findElement(linkedInCompany).isDisplayed()){
            fail("LinkedIn company is not displayed.");
        }
        return findElement(linkedInCompany).getText();
    }

    public boolean numberOfLinkedInFollowersIsDisplayed(){
        if (!doesElementExist(linkedInFollowers) || !findElement(linkedInFollowers).isDisplayed()){
            fail("Number of LinkedIn followers is not displayed.");
        }
        try {
            return Integer.parseInt(findElement(linkedInFollowers).getText()) >= 0;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public String getLinkedInStatusIndicator(){
        if (!doesElementExist(linkedInStatusIndicator) || !findElement(linkedInStatusIndicator).isDisplayed()){
            fail("LinkedIn status indicator is not displayed.");
        }
        return findElement(linkedInStatusIndicator).getCssValue("background-image");
    }

    public boolean linkedInSettingsButtonIsDisplayed(){
        return doesElementExist(linkedInSettingsButton) && findElement(linkedInSettingsButton).isDisplayed();
    }

    public boolean linkedInDeAuthorizeButtonIsDisplayed(){
        return doesElementExist(linkedInDeAuthorizeButton) && findElement(linkedInDeAuthorizeButton).isDisplayed();
    }

    public boolean linkedInDisableButtonIsDisplayed(){
        return doesElementExist(linkedInDisableButton) && findElement(linkedInDisableButton).isDisplayed();
    }

    public boolean linkedInEnableButtonIsDisplayed(){
        return doesElementExist(linkedInEnableButton) && findElement(linkedInEnableButton).isDisplayed();
    }

    public boolean linkedInReAuthorizeButtonIsDisplayed(){
        return doesElementExist(linkedInReAuthorizeButton) && findElement(linkedInReAuthorizeButton).isDisplayed();
    }

    public boolean linkedInAuthorizeButtonIsDisplayed(){
        return doesElementExist(linkedInAuthorizeButton) && findElement(linkedInAuthorizeButton).isDisplayed();
    }

    // FACEBOOK SECTION METHODS

    public String getFacebookStatusMessage(){
        if (!doesElementExist(facebookStatusMessage) || !findElement(facebookStatusMessage).isDisplayed()){
            return "";
        }
        return findElement(facebookStatusMessage).getText();
    }

    public FacebookLogin authorizeFacebookAccount(){
        waitForElement(facebookAuthorizeButton);
        findElement(facebookAuthorizeButton).click();
        return new FacebookLogin(getDriver());
    }

    public FacebookLogin reAuthorizeFacebookAccount(){
        // deleting Facebook session cookie so that you are not logged in to Facebook
        String currentURL = driver.getCurrentUrl();
        driver.get("https://www.facebook.com");
        driver.manage().deleteCookieNamed("xs");
        driver.get(currentURL);

        waitForElement(facebookReAuthorizeButton);
        findElement(facebookReAuthorizeButton).click();
        return new FacebookLogin(getDriver());
    }

    public SocialMediaSummary chooseFirstFacebookPage(){
        waitForElement(facebookPageRadioSelector);
        findElement(facebookPageRadioSelector).click();
        findElement(facebookPageSelectButton).click();
        return this;
    }

    public SocialMediaSummary disableFacebookAccount(){
        waitForElement(facebookDisableButton);
        findElement(facebookDisableButton).click();
        return this;
    }

    public SocialMediaSummary enableFacebookAccount(){
        waitForElement(facebookEnableButton);
        findElement(facebookEnableButton).click();
        return this;
    }

    public SocialMediaSummary deAuthorizeFacebookAccount(){
        waitForElement(facebookDeAuthorizeButton);
        findElement(facebookDeAuthorizeButton).click();
        Alert confirmation = driver.switchTo().alert();
        confirmation.accept();
        driver.switchTo().defaultContent();
        return this;
    }

    public SocialTemplates openFacebookSettings(){
        waitForElement(facebookSettingsButton);
        findElement(facebookSettingsButton).click();
        pause(3000);
        return new SocialTemplates(getDriver());
    }

    public String getFacebookAccountName(){
        if (!doesElementExist(facebookAccountName) || !findElement(facebookAccountName).isDisplayed()){
            fail("Facebook account name is not displayed.");
        }
        return findElement(facebookAccountName).getText();
    }

    public String getFacebookPage(){
        if (!doesElementExist(facebookPage) || !findElement(facebookPage).isDisplayed()){
            fail("Facebook page name is not displayed.");
        }
        return findElement(facebookPage).getText();
    }

    public boolean numberOfFacebookFansIsDisplayed(){
        if (!doesElementExist(facebookFans) || !findElement(facebookFans).isDisplayed()){
            fail("Number of Facebook fans is not displayed.");
        }
        try {
            return Integer.parseInt(findElement(facebookFans).getText().substring(6)) >= 0;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public String getFacebookStatusIndicator(){
        if (!doesElementExist(facebookStatusIndicator) || !findElement(facebookStatusIndicator).isDisplayed()){
            fail("Facebook status indicator is not displayed.");
        }
        return findElement(facebookStatusIndicator).getCssValue("background-image");
    }

    public boolean facebookSettingsButtonIsDisplayed(){
        return doesElementExist(facebookSettingsButton) && findElement(facebookSettingsButton).isDisplayed();
    }

    public boolean facebookDeAuthorizeButtonIsDisplayed(){
        return doesElementExist(facebookDeAuthorizeButton) && findElement(facebookDeAuthorizeButton).isDisplayed();
    }

    public boolean facebookDisableButtonIsDisplayed(){
        return doesElementExist(facebookDisableButton) && findElement(facebookDisableButton).isDisplayed();
    }

    public boolean facebookEnableButtonIsDisplayed(){
        return doesElementExist(facebookEnableButton) && findElement(facebookEnableButton).isDisplayed();
    }

    public boolean facebookReAuthorizeButtonIsDisplayed(){
        return doesElementExist(facebookReAuthorizeButton) && findElement(facebookReAuthorizeButton).isDisplayed();
    }

    public boolean facebookAuthorizeButtonIsDisplayed(){
        return doesElementExist(facebookAuthorizeButton) && findElement(facebookAuthorizeButton).isDisplayed();
    }
}
