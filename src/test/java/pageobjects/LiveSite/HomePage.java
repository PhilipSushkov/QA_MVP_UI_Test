package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by jasons on 2016-11-07.
 */
public class HomePage extends AbstractPageObject {

    private final By Q4Logo = By.className("ClientLogo");
    private final By versionNumber = By.className("Version");
    private final By stockInformation = By.linkText("Stock Information");
    private final By financialReports = By.linkText("Financial Reports");
    private final By pressReleases = By.linkText("Press Releases");
    private final By events = By.linkText("Events");
    private final By presentations = By.linkText("Presentations");
    private final By secFilings = By.linkText("SEC Filings");
    private final By boardOfDirectors = By.linkText("Board of Directors");
    private final By rssFeeds = By.linkText("RSS Feeds");
    private final By siteMap = By.linkText("Site Map");
<<<<<<< Updated upstream
    private final By emailAlerts = By.linkText("Email Alerts");
=======
    private final By emailAlerts = By.linkText("Email Alerts"); //By.id("_ctrl0_ctl45_SiteMapRepeater_ctl15_hrefLink"); //Exists/isDislpayed once siteMap is clicked

    // ^^ Currently the above is within a <ul></ul> which is an unordered list. Perhaps attempt to get the X element of that list with x-path to get the emailAlerts as it is not working as of now

>>>>>>> Stashed changes

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean logoIsPresent(){

        return doesElementExist(Q4Logo);
    }

    public void clickSiteMap(){
        findElement(siteMap).click();
    }

    public String getVersionNumber(){

        return findElement(versionNumber).getText();
    }

    public StockInformationPage selectStockInformationFromMenu(){
        findVisibleElement(stockInformation).click();
        return new StockInformationPage(getDriver());
    }

    public FinancialReportsPage selectFinancialReportsFromMenu(){
        findVisibleElement(financialReports).click();
        return new FinancialReportsPage(getDriver());
    }

    public LivePressReleases selectPressReleasesFromMenu(){
        findVisibleElement(pressReleases).click();
        return new LivePressReleases(getDriver());
    }

    public LiveEvents selectEventsFromMenu(){
        findVisibleElement(events).click();
        return new LiveEvents(getDriver());
    }

    public LivePresentations selectPresentationsFromMenu(){
        findVisibleElement(presentations).click();
        return new LivePresentations(getDriver());
    }

    public SECFilingsPage selectSECFilingsFromMenu(){
        findVisibleElement(secFilings).click();
        return new SECFilingsPage(getDriver());
    }

    public BoardOfDirectorsPage selectBoardOfDirectorsFromMenu(){
        findVisibleElement(boardOfDirectors).click();
        return new BoardOfDirectorsPage(getDriver());
    }

    public RSSFeedsPage selectRSSFeedsFromMenu(){
        findVisibleElement(rssFeeds).click();
        return new RSSFeedsPage(getDriver());
    }

<<<<<<< Updated upstream
    public RSSFeedsPage emailAlertsFromMap(){
        findVisibleElement(siteMap).click();
        pause(1000L);
        findVisibleElement(emailAlerts).click();
        return new RSSFeedsPage(getDriver());
    }
=======
    public EmailAlertsPage selectEmailAlertsFromPage() { //perhaps just go to URL instead of clicking on elements?
    /*
        findVisibleElement(siteMap).click();
        pause(500L);
        driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        wait.until(ExpectedConditions.visibilityOf(findElement(emailAlerts)));
        findElement(emailAlerts).click();
*/
// /*
        driver.get("http://chicagotest.q4web.com/English/Contact-Us/email-alerts/default.aspx");
        driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
        driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        ((JavascriptExecutor)driver).executeScript("return window.stop");
//*/
        return new EmailAlertsPage(getDriver());
    }

>>>>>>> Stashed changes
}
