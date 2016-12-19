package pageobjects.LiveSite;

import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;
import pageobjects.Dashboard.Dashboard;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static specs.AbstractSpec.propUIPublicSite;

/**
 * Created by philipsushkov on 2016-11-08.
 */
public class LivePresentations extends AbstractPageObject {

    private final By latestHeadlines;
    private final By latestHeadlineLinks;
    private final By presentationTitle;
    private final By presentationDate;
    private final By presentationLink;
    private final By yearLink;

    // elements on page of loaded presentation
    private final By presentationImage;
    private final By documentDownloadLink;

    public LivePresentations(WebDriver driver) {
        super(driver);
        latestHeadlines = By.xpath(propUIPublicSite.getProperty("latestHeadlines"));
        latestHeadlineLinks = By.xpath(propUIPublicSite.getProperty("latestHeadlineLinks"));
        presentationTitle = By.className(propUIPublicSite.getProperty("presentationTitle"));
        presentationDate = By.className(propUIPublicSite.getProperty("presentationDate"));
        presentationLink = By.partialLinkText(propUIPublicSite.getProperty("presentationLink"));
        yearLink = By.className(propUIPublicSite.getProperty("yearLink"));

        // elements on page of loaded presentation
        presentationImage = By.xpath(propUIPublicSite.getProperty("presentationImage"));
        documentDownloadLink = By.xpath(propUIPublicSite.getProperty("documentDownloadLink"));

    }

    public Dashboard dashboard(String url) {
        driver.get(url);
        return new Dashboard(getDriver());
    }

    public boolean canFindNewHeadline(String expectedHeadline, boolean desiredState, String[] expectedFilenames) throws InterruptedException {
        WebElement headlines;
        int refreshAttempts = 0;
        boolean foundHeadline = !desiredState;
        long startTime = System.currentTimeMillis();
        long time = System.currentTimeMillis();

        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

        while (foundHeadline!=desiredState && time-startTime<120000){
            try{Thread.sleep(5000);}
            catch(InterruptedException e){
                e.printStackTrace();
            }
            refreshAttempts++;
            System.out.println("Now performing refresh "+refreshAttempts);
            time = System.currentTimeMillis();

            try {
                driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
                Thread.sleep(1000);
                driver.navigate().refresh();
            } catch (TimeoutException e) {
                driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
            }

            try {
                driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
                headlines = driver.findElement(latestHeadlines);

                System.out.println("Found Head Line: "+headlines.getText());
                if (!headlines.getText().equals(expectedHeadline)){
                    foundHeadline = false;
                    System.out.println("ERROR: Head Line doesn't match.");
                } else {
                    foundHeadline = true;
                }

                String[] foundFilenames = new String[2];
                foundFilenames[0] = findElement(presentationImage).getAttribute("src");
                System.out.println("Found image file: "+foundFilenames[0]);
                if (!foundFilenames[0].contains(expectedFilenames[0])){
                    foundHeadline = false;
                    System.out.println("ERROR: Image filename doesn't match.");
                }

                foundFilenames[1] = findElement(documentDownloadLink).getAttribute("src");
                System.out.println("Found image file: "+foundFilenames[0]);
                if (!foundFilenames[0].contains(expectedFilenames[0])){
                    foundHeadline = false;
                    System.out.println("ERROR: Attached document filename doesn't match.");
                }

            } catch (ElementNotFoundException e2) {
                foundHeadline = false;
            } catch (TimeoutException e3) {
                driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
                foundHeadline = false;
            } catch (NoSuchElementException e4) {
                driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
                foundHeadline = false;
            }




            /*
            headlines = findElement(latestHeadlines);

            System.out.println("Found Head Line: "+headlines.getText());
            if (!headlines.getText().equals(expectedHeadline)){
                foundHeadline = false;
                System.out.println("ERROR: Head Line doesn't match.");
            } else {
                foundHeadline = true;
            }


            String[] foundFilenames = new String[2];
            foundFilenames[0] = findElement(presentationImage).getAttribute("src");
            System.out.println("Found image file: "+foundFilenames[0]);
            if (!foundFilenames[0].contains(expectedFilenames[0])){
                foundHeadline = false;
                System.out.println("ERROR: Image filename doesn't match.");
            }

            foundFilenames[1] = findElement(documentDownloadLink).getAttribute("href");
            System.out.println("Found attached document: "+foundFilenames[1]);
            if (!foundFilenames[1].contains(expectedFilenames[1])){
                foundHeadline = false;
                System.out.println("ERROR: Attached document filename doesn't match.");
            }

            */

        }

        return foundHeadline;
    }

    // NEW METHODS CREATED FOR PUBLIC SITE SMOKE TEST

    public boolean presentationsAreDisplayed(){
        return doesElementExist(presentationLink) && findElement(presentationLink).isDisplayed();
    }

    public boolean presentationsAreAllFromYear(String year){
        boolean allFromYear = true;
        List<WebElement> titles = findElements(presentationTitle);
        List<WebElement> presentationDates = findElements(presentationDate);
        for (int i=0; i<presentationDates.size(); i++){
            if (!presentationDates.get(i).getText().contains(year)){
                System.out.println("Press release with headline: "+titles.get(i).getText()+"\n\thas date "+presentationDates.get(i).getText()+" not in "+year);
                allFromYear = false;
            }
        }
        return allFromYear;
    }

    public void switchYearTo(String year){
        List<WebElement> yearLinks = findElements(yearLink);
        for (int i=0; i<yearLinks.size(); i++){
            if (yearLinks.get(i).getText().equals(year)){
                yearLinks.get(i).click();
                return;
            }
        }
    }

    public boolean presentationLinksAreLinks(){
        boolean allLinks = true;

        List<WebElement> presentationTitles = findElements(presentationTitle);
        List<WebElement> presentationLinks = findElements(presentationLink);
        for (int i=0; i<presentationLinks.size(); i++){
            if (!presentationLinks.get(i).getAttribute("href").contains("//")){
                System.out.println("Report '"+presentationTitles.get(i).getText()+"' does not have valid link.\n\thref = "+presentationLinks.get(i).getAttribute("href"));
                allLinks = false;
            }
        }

        return allLinks;
    }

    public boolean pdfLinkIsPresent(){
        List<WebElement> presentationLinks = findElements(presentationLink);
        for (int i=0; i<presentationLinks.size(); i++){
            if (presentationLinks.get(i).getAttribute("href").endsWith(".pdf")){
                return true;
            }
        }
        return false;
    }

}