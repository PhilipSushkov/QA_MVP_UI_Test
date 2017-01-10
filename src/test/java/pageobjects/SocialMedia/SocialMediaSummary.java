package pageobjects.SocialMedia;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;

import static org.junit.Assert.fail;
import static specs.AbstractSpec.propUISocialMedia;

/**
 * Created by jasons on 2016-12-07.
 */
public class SocialMediaSummary extends AbstractPageObject {
    private static By moduleTitle, settingsDialog, linkedInStatusIndicator, linkedInStatusMessage, linkedInAccountName;
    private static By linkedInCompany, linkedInFollowers, linkedInAuthorizeButton, linkedInDeAuthorizeButton;
    private static By linkedInDisableButton, linkedInEnableButton, linkedInReAuthorizeButton, linkedInSettingsButton, linkedInCompanyRadioSelector;
    private static By linkedInCompanySelectButton, facebookStatusIndicator, facebookStatusMessage, facebookAccountName;
    private static By facebookPage, facebookFans, facebookAuthorizeButton, facebookDeAuthorizeButton, facebookDisableButton;
    private static By facebookEnableButton, facebookReAuthorizeButton, facebookSettingsButton, facebookPageRadioSelector, facebookPageSelectButton;

    private static By linkedInSpan, facebookSpan, twitterSpan, stockTwitsSpan, slideShareSpan, googleAPISpan, bitLySpan;
    private static By linkedInDiv, facebookDiv, twitterDiv, stockTwitsDiv, slideShareDiv, googleAPIDiv, bitLyDiv;

    public SocialMediaSummary(WebDriver driver) {
        super(driver);

        moduleTitle = By.xpath(propUISocialMedia.getProperty("spanModule_Title"));
        settingsDialog = By.id(propUISocialMedia.getProperty("box_SettingsDialog"));

        // LinkedIn section
        linkedInStatusIndicator = By.cssSelector(propUISocialMedia.getProperty("linkedIn_StatusInd"));
        //css -> background-image contains /unchecked.png (if not setup), /checked.png (if setup), or /disabled.png (if disabled)
        linkedInStatusMessage = By.xpath(propUISocialMedia.getProperty("msg_linkedInStatus"));
        linkedInAccountName = By.cssSelector(propUISocialMedia.getProperty("linkedIn_AccountName"));
        linkedInCompany = By.xpath(propUISocialMedia.getProperty("linkedIn_Company"));
        linkedInFollowers = By.xpath(propUISocialMedia.getProperty("linkedIn_Followers"));
        linkedInAuthorizeButton = By.cssSelector(propUISocialMedia.getProperty("btn_linkedIn_Authorize"));
        linkedInDeAuthorizeButton = By.cssSelector(propUISocialMedia.getProperty("btn_linkedInDeAuthorize"));
        linkedInDisableButton = By.cssSelector(propUISocialMedia.getProperty("btn_linkedInDisable"));
        linkedInEnableButton = By.cssSelector(propUISocialMedia.getProperty("btn_linkedInEnable"));
        linkedInReAuthorizeButton = By.cssSelector(propUISocialMedia.getProperty("btn_linkedInReAuthorize"));
        linkedInSettingsButton = By.cssSelector(propUISocialMedia.getProperty("btn_linkedInSettings"));
        linkedInCompanyRadioSelector = By.xpath(propUISocialMedia.getProperty("sel_linkedInCompanyRadio"));
        linkedInCompanySelectButton = By.cssSelector(propUISocialMedia.getProperty("btn_linkedInCompanySelect"));

        // Facebook section
        facebookStatusIndicator = By.cssSelector(propUISocialMedia.getProperty("facebook_StatusInd"));
        //css -> background-image contains /unchecked.png (if not setup), /checked.png (if setup), or /disabled.png (if disabled)
        facebookStatusMessage = By.xpath(propUISocialMedia.getProperty("msg_facebook_Status"));
        facebookAccountName = By.cssSelector(propUISocialMedia.getProperty("facebook_AccountName"));
        facebookPage = By.xpath(propUISocialMedia.getProperty("facebook_Page"));
        facebookFans = By.xpath(propUISocialMedia.getProperty("facebook_Fans"));
        facebookAuthorizeButton = By.cssSelector(propUISocialMedia.getProperty("btn_facebook_Authorize"));
        facebookDeAuthorizeButton = By.cssSelector(propUISocialMedia.getProperty("btn_facebook_DeAuthorize"));
        facebookDisableButton = By.cssSelector(propUISocialMedia.getProperty("btn_facebook_Disable"));
        facebookEnableButton = By.cssSelector(propUISocialMedia.getProperty("btn_facebook_Enable"));
        facebookReAuthorizeButton = By.cssSelector(propUISocialMedia.getProperty("btn_facebook_ReAuthorize"));
        facebookSettingsButton = By.cssSelector(propUISocialMedia.getProperty("btn_facebook_Settings"));
        facebookPageRadioSelector = By.xpath(propUISocialMedia.getProperty("sel_facebook_PageRadio"));
        facebookPageSelectButton = By.cssSelector(propUISocialMedia.getProperty("btn_facebook_PageSelect"));

        linkedInSpan = By.xpath(propUISocialMedia.getProperty("span_LinkedIn"));
        facebookSpan = By.xpath(propUISocialMedia.getProperty("span_Facebook"));

        stockTwitsSpan = By.xpath(propUISocialMedia.getProperty("span_StockTwits"));
        twitterSpan = By.xpath(propUISocialMedia.getProperty("span_Twitter"));
        slideShareSpan = By.xpath(propUISocialMedia.getProperty("span_SlideShare"));
        googleAPISpan = By.xpath(propUISocialMedia.getProperty("span_GoogleAPI"));
        bitLySpan = By.xpath(propUISocialMedia.getProperty("span_BitLy"));

        linkedInDiv = By.xpath(propUISocialMedia.getProperty("div_LinkedIn"));
        facebookDiv = By.xpath(propUISocialMedia.getProperty("div_Facebook"));
        twitterDiv = By.xpath(propUISocialMedia.getProperty("div_Twitter"));
        stockTwitsDiv = By.xpath(propUISocialMedia.getProperty("div_StockTwits"));
        slideShareDiv = By.xpath(propUISocialMedia.getProperty("div_SlideShare"));
        googleAPIDiv = By.xpath(propUISocialMedia.getProperty("div_GoogleAPI"));
        bitLyDiv = By.xpath(propUISocialMedia.getProperty("div_BitLy"));
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

    // checks that a number of followers of at least zero is displayed
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

    // returns image file path containing /unchecked.png (red X; not setup), /checked.png (green checkmark; setup), or /disabled.png (grey checkmark; disabled)
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

    // This deletes the Facebook session cookie (named "xs") in order to undo the login that occurred during the authorization process
    public SocialMediaSummary logoutFromFacebook(){
        String currentURL = driver.getCurrentUrl();
        driver.get("https://www.facebook.com"); //need to navigate to facebook.com because WebDriver can only delete cookies from current domain
        driver.manage().deleteCookieNamed("xs");
        driver.get(currentURL);
        return this;
    }

    public FacebookLogin reAuthorizeFacebookAccount(){
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

    // checks that a number of fans of at least zero is displayed
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

    // returns image file path containing /unchecked.png (red X; not setup), /checked.png (green checkmark; setup), or /disabled.png (grey checkmark; disabled)
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

    public WebElement getLinkedInSpan() {
        WebElement element = null;

        try {
            waitForElement(linkedInSpan);
            element = findElement(linkedInSpan);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getFacebookSpan() {
        WebElement element = null;

        try {
            waitForElement(facebookSpan);
            element = findElement(facebookSpan);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getTwitterSpan() {
        WebElement element = null;

        try {
            waitForElement(twitterSpan);
            element = findElement(twitterSpan);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getStockTwitsSpan() {
        WebElement element = null;

        try {
            waitForElement(stockTwitsSpan);
            element = findElement(stockTwitsSpan);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getSlideShareSpan() {
        WebElement element = null;

        try {
            waitForElement(slideShareSpan);
            element = findElement(slideShareSpan);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getGoogleAPISpan() {
        WebElement element = null;

        try {
            waitForElement(googleAPISpan);
            element = findElement(googleAPISpan);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getBitLySpan() {
        WebElement element = null;

        try {
            waitForElement(bitLySpan);
            element = findElement(bitLySpan);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getLinkedInBtns() {
        WebElement element = null;

        try {
            waitForElement(linkedInDiv);
            element = findElement(linkedInDiv);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getFacebookBtns() {
        WebElement element = null;

        try {
            waitForElement(facebookDiv);
            element = findElement(facebookDiv);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getTwitterBtns() {
        WebElement element = null;

        try {
            waitForElement(twitterDiv);
            element = findElement(twitterDiv);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getStockTwitsBtns() {
        WebElement element = null;

        try {
            waitForElement(stockTwitsDiv);
            element = findElement(stockTwitsDiv);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getSlideShareBtns() {
        WebElement element = null;

        try {
            waitForElement(slideShareDiv);
            element = findElement(slideShareDiv);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getGoogleAPIBtns() {
        WebElement element = null;

        try {
            waitForElement(googleAPIDiv);
            element = findElement(googleAPIDiv);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

    public WebElement getBitLyBtns() {
        WebElement element = null;

        try {
            waitForElement(bitLyDiv);
            element = findElement(bitLyDiv);
        } catch (ElementNotFoundException e1) {
        } catch (ElementNotVisibleException e2) {
        } catch (TimeoutException e3) {
        }

        return element;
    }

}
