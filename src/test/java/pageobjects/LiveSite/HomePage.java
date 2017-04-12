package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIPublicSite;

/**
 * Created by jasons on 2016-11-07.
 */
public class HomePage extends AbstractPageObject {

    private final By Q4Logo;
    private final By versionNumber;
    private final By stockInformation;
    private final By stockInformationTitle;
    private final By financialReports;
    private final By pressReleases;
    private final By events;
    private final By presentations;
    private final By secFilings;
    private final By boardOfDirectors;
    private final By rssFeeds;
    private final By siteMap;
    private final By emailAlerts;
    private final By investmentCalculator;
    private final By faq;
    private final By jobApplications;
    private final By sitemap;

    public HomePage(WebDriver driver) {
        super(driver);



        Q4Logo = By.className(propUIPublicSite.getProperty("Q4Logo"));
        versionNumber = By.className(propUIPublicSite.getProperty("versionNumber"));
        stockInformation = By.linkText(propUIPublicSite.getProperty("stockInformation"));
        stockInformationTitle = By.xpath(propUIPublicSite.getProperty("stockInformationTitle"));
        financialReports = By.linkText(propUIPublicSite.getProperty("financialReports"));
        pressReleases = By.linkText(propUIPublicSite.getProperty("pressReleases"));
        events = By.linkText(propUIPublicSite.getProperty("events"));
        presentations = By.linkText(propUIPublicSite.getProperty("presentations"));
        secFilings = By.linkText(propUIPublicSite.getProperty("secFilings"));
        boardOfDirectors = By.linkText(propUIPublicSite.getProperty("boardOfDirectors"));
        rssFeeds = By.linkText(propUIPublicSite.getProperty("rssFeeds"));
        siteMap = By.linkText(propUIPublicSite.getProperty("siteMap"));
        emailAlerts = By.linkText(propUIPublicSite.getProperty("emailAlerts"));
        investmentCalculator = By.linkText(propUIPublicSite.getProperty("investmentCalculator"));
        faq = By.linkText(propUIPublicSite.getProperty("faq"));
        jobApplications = By.xpath("/html/body[@class='BodyBackground']//ul[@class='level2']/li[6]/a");
        sitemap = By.xpath("//a[contains(string(),'Site Map')]");
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

    public EmailAlertsPage selectEmailAlertsFromMenu() {

        try {
            findVisibleElement(emailAlerts).click();
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

    public JobApplicationsPage selectJobApplicationFromMenu(){
        try {
            findVisibleElement(jobApplications).click();
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        return new JobApplicationsPage(getDriver());
    }

    public SiteMapPage selectSiteMapFromFooter(){
        try {
            findVisibleElement(sitemap).click();
        }catch (TimeoutException e){
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
        return new SiteMapPage(getDriver());
    }

}
