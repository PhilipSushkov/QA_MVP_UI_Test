package pageobjects.LiveSite;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static specs.AbstractSpec.propUIPublicSite;

/**
 * Created by jasons on 2016-11-11.
 */
public class SECFilingsPage extends AbstractPageObject {
    private static final long DEFAULT_PAUSE = 2000;
    private final By filingDate;
    private final By yearLink_Sec;
    private final By pdfIcon;
    private final By noFilingsMessage = By.xpath("//div[contains(@class,'ModuleNotFound') and contains(string(),'No items found')]");
    private final By FilterDropdown = By.xpath("//option[contains(string(),'All Form Types')]//..");
    private final By detailsPageModuleTitle = By.xpath("//span[contains(@class,'ModuleTitle') and contains(string(),'SEC Filings Details')]");
    private final By detailsPageFilingName = By.xpath("//span[contains(@class,'ModuleFilingDescription')]");
    private final By detailsPageFilingDate = By.xpath("//span[contains(@class,'ModuleDate')]");
    String[] filter = {"All Form Types", "Annual Filings", "Quarterly Filings","Current Reports","Proxy Filings","Registration Statements","Section 16 Filings","Other Filings"};


    public SECFilingsPage(WebDriver driver) {
        super(driver);
        filingDate = By.className(propUIPublicSite.getProperty("filingDate"));
        yearLink_Sec = By.className(propUIPublicSite.getProperty("yearLink_Sec"));
        pdfIcon = By.cssSelector(propUIPublicSite.getProperty("pdfIcon"));
    }

    public boolean doFilingsExist() throws InterruptedException {
        //point of this test is to run before all the other tests
        //So if there isn't any filings, it doesn't fail all of our tests
        boolean FailedTest;
        waitForAjaxLoader();
        Thread.sleep(DEFAULT_PAUSE);
        if(!doesElementExist(filingDate)) {

            try {
                findElement(noFilingsMessage);
                FailedTest=false;
            } catch(Exception e){
                //adding in a double check
                if(!doesElementExist(filingDate)) {
                    FailedTest = true;
                }
                else{
                    FailedTest = false;
                }
            }
            Assert.assertFalse("No SEC Filings but missing error message", FailedTest);
            return false;
        }
        else{
            findElement(filingDate);
            return true;
        }
    }

    public boolean filingsAreDisplayed() throws InterruptedException {
        Thread.sleep(DEFAULT_PAUSE);
        return doesElementExist(filingDate) && findElement(filingDate).isDisplayed();
    }

    //So far, for the tests we have, I don't think it's necessary to have a version of this method for filters
    public boolean filingsAreAllFromYear(String year){
        boolean allFromYear = true;
        List<WebElement> filingDates = findElements(filingDate);
        for (int i=0; i<filingDates.size(); i++){
            if (!filingDates.get(i).getText().contains(year)){
                System.out.println("Filing with date: "+filingDates.get(i).getText()+" is not in year "+year);
                allFromYear = false;
            }
        }
        return allFromYear;
    }

    //This test doesn't do a great check for Other Filings
    public boolean checkAllFilters() throws InterruptedException {
        int totalNumberOfSECFiles=0;
        String[] expectedTitles = {"","Annual","Quarterly","Current","Proxy","Registration","Beneficial Ownership",""};
        //All Forms will be counting the rest and THEN check
        //Check Quarterly Filings
        for(int i = 1; i < filter.length; i++) {
            switchFilterTo(filter[i]);
            Thread.sleep(DEFAULT_PAUSE);
            if(doFilingsExist()) {
                //files exist, so we loop to see if each file is correct
                //nextFile is so we know if there is another SEC File for that filter
                boolean nextFile = true;
                int numOfFiles = 1;
                while(nextFile) {

                    try{
                        //checking if the files have the right filter
                        //if they match, loop will check the next file
                        if (!(getSECTitle(numOfFiles).contains(expectedTitles[i]))) {
                            return false;
                        }
                        else {
                            totalNumberOfSECFiles++;
                            numOfFiles++;
                        }
                    }
                    catch(NullPointerException e){
                        //There is no more SEC Files (hit the end)
                        nextFile = false;
                        break;
                    }
                }
            }
        }

        //check for the "All Form Types" should be the total number of files together (AFT = All Filter Types)
        switchFilterTo(filter[0]);
        boolean AFTLoop = true;
        int AFTCount = 0;
        while(AFTLoop){
            //search for files
            if (!(getSECTitle(AFTCount+1)==null)) {
                AFTCount++;
            }
            else {
                //out of files
                AFTLoop = false;
                break;
            }
        }

        if(!(AFTCount == totalNumberOfSECFiles)){
            return false;
        }

        return true;
    }

    public boolean checkAllYears() throws InterruptedException {
        int listNum;
        boolean files = false;

        List<WebElement> yearLinks = findElements(yearLink_Sec);

        for (int i = 1;  i <= yearLinks.size(); i++){
            listNum = i;
            By yearList = By.xpath("//div[contains(@class,'YearNavContainer')]//a["+listNum+"][contains(@class,'ModuleYearLink')]");
            String year = findElement(yearList).getText();
            findElement(yearList).click();
            Thread.sleep(DEFAULT_PAUSE);
            //check if there are filings for that year
            files = doFilingsExist();

            //if there are SEC Files
            if(files){
                //if the files are NOT from the correct year, method returns false, test fails
                //else, continue the loop and check though the years
                if(!filingsAreAllFromYear(year)){
                    return false;
                }
            }
        }
        return true;
    }

    public void switchYearTo(String year) throws InterruptedException {
        List<WebElement> yearLinks = findElements(yearLink_Sec);
        for (int i=0; i<yearLinks.size(); i++){
            if (yearLinks.get(i).getText().equals(year)){
                yearLinks.get(i).click();
                Thread.sleep(DEFAULT_PAUSE);
                return;
            }
        }
    }

    public void switchFilterTo(String type) throws InterruptedException {
        String filter = type;
        By DropdownOption = By.xpath("//option[contains(string(),'"+filter+"')]");

        findVisibleElement(FilterDropdown).click();
        Thread.sleep(DEFAULT_PAUSE);

        findVisibleElement(DropdownOption).click();
        Thread.sleep(DEFAULT_PAUSE);
    }

    public boolean pdfIconsLinkToPDF(){
        boolean allLinks = true;

        List<WebElement> filingDates = findElements(filingDate);
        List<WebElement> pdfIcons = findElements(pdfIcon);
        for (int i=0; i<pdfIcons.size(); i++){
            if (!pdfIcons.get(i).getAttribute("href").endsWith(".pdf")){
                System.out.println("Filing with date: "+filingDates.get(i).getText()+" has a pdf icon that does not link to a .pdf file.\n\thref = "+pdfIcons.get(i).getAttribute("href"));
                allLinks = false;
            }
        }

        return allLinks;
    }

    //getting the nth number of SEC Filing's date
    public String getSECDate(int num){
        By SECFilingDate = By.xpath("//div[contains(@class,'RegulatoryFilingContainer')]//div[contains(@class,'ModuleItemRow')]["+num+"]//span[contains(@class,'ItemDate')]");
        String date;

        try{
            date = findElement(SECFilingDate).getText();
        }
        catch(Exception e){
            date = null;
        }

        return date;

    }

    //getting the nth number of SEC Filings's title
    public String getSECTitle(int num){
        By SECFilingTitle = By.xpath("//div[contains(@class,'RegulatoryFilingContainer')]//div[contains(@class,'ModuleItemRow')]["+num+"]//span[contains(@class,'ItemDescription')]");
        String title;

        try{
            title = findElement(SECFilingTitle).getText();
        }
        catch(Exception e){
            title = null;
        }

        return title;
    }

    public void clickSECFiling(int num){
        By SECFilingLink = By.xpath("//div[contains(@class,'RegulatoryFilingContainer')]//div[contains(@class,'ModuleItemRow')]["+num+"]//a");
        findVisibleElement(SECFilingLink).click();
    }

    public boolean detailsPageAppears(){
        try{
            findElement(detailsPageModuleTitle);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean detailsPageCorrectInfo(String firstTitle, String firstDateUnparsed){
        SimpleDateFormat firstFormat = new SimpleDateFormat("MMM dd, yyyy");
        SimpleDateFormat secondFormat = new SimpleDateFormat("MM/dd/yyy");
        try {
            Date firstDateParsed = firstFormat.parse(firstDateUnparsed);

            String secondDateUnparsed = findElement(detailsPageFilingDate).getText();

            Date secondDateParsed = secondFormat.parse(secondDateUnparsed);

            if(!firstDateParsed.equals(secondDateParsed)){
                //date's are NOT the same
                return false;
            }
            //if the dates match then the rest of the program will run

        } catch (ParseException e) {
            //if there is a problem with the parsing, return false;
            e.printStackTrace();
            return false;
        }

        String secondTitle = findElement(detailsPageFilingName).getText();

        if(firstTitle.equals(secondTitle)){
            //titles match
            return true;
        }
        else{
            //titles do not match
            return false;
        }

    }
}