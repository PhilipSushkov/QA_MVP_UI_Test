package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by jasons on 2016-11-07.
 */
public class HomePage extends AbstractPageObject {

    private final By Q4Logo = By.className("ClientLogo");
    private final By versionNumber = By.className("Version");
    private final By stockInformation = By.linkText("Stock Information");
    private final By stockInformationTitle = By.xpath("//h1[contains(@id,'ModuleTitle')]");
    private final By financialReports = By.linkText("Financial Reports");
    private final By pressReleases = By.linkText("Press Releases");
    private final By events = By.linkText("Events");
    private final By presentations = By.linkText("Presentations");
    private final By secFilings = By.linkText("SEC Filings");
    private final By boardOfDirectors = By.linkText("Board of Directors");
    private final By rssFeeds = By.linkText("RSS Feeds");
    private final By siteMap = By.linkText("Site Map");
    private final By emailAlerts = By.linkText("Email Alerts");
    
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

    public StockInformationPage selectStockInformationFromMenu() {

        try {
            findElement(stockInformation).click();
            //wait.until(ExpectedConditions.visibilityOf(findElement(stockInformationTitle)));
        } catch (TimeoutException e) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }

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

    public EmailAlertsPage selectEmailAlertsFromPage() {

        try {
            driver.get("http://chicagotest.q4web.com/English/Contact-Us/email-alerts/default.aspx");
        } catch (TimeoutException e) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }

        return new EmailAlertsPage(getDriver());
    }

}
