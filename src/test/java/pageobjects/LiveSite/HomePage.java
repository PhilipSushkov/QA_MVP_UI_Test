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
    private final By investmentCalculator = By.linkText("Investment Calculator");
    private final By faq = By.linkText("FAQ");
    
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
        try{
            findVisibleElement(financialReports).click();
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        return new FinancialReportsPage(getDriver());
    }

    public LivePressReleases selectPressReleasesFromMenu(){
        try {
            findVisibleElement(pressReleases).click();
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        return new LivePressReleases(getDriver());
    }

    public LiveEvents selectEventsFromMenu(){
        try {
            findVisibleElement(events).click();
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        return new LiveEvents(getDriver());
    }

    public LivePresentations selectPresentationsFromMenu(){
        try {
            findVisibleElement(presentations).click();
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        return new LivePresentations(getDriver());
    }

    public SECFilingsPage selectSECFilingsFromMenu(){
        try {
            findVisibleElement(secFilings).click();
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        return new SECFilingsPage(getDriver());
    }

    public BoardOfDirectorsPage selectBoardOfDirectorsFromMenu(){
        try {
            findVisibleElement(boardOfDirectors).click();
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        return new BoardOfDirectorsPage(getDriver());
    }

    public RSSFeedsPage selectRSSFeedsFromMenu(){
       try {
           findVisibleElement(rssFeeds).click();
       }catch (TimeoutException e){
           driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
       }
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

    public InvestmentCalculatorPage selectInvestmentCalculatorFromMenu(){
        try {
            findVisibleElement(investmentCalculator).click();
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        return new InvestmentCalculatorPage(getDriver());
    }

    public FAQPage selectFAQFromMenu(){
        try {
            findVisibleElement(faq).click();
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        return new FAQPage(getDriver());
    }

}
