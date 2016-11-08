package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.Dashboard.Dashboard;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by philipsushkov on 2016-11-08.
 */
public class LivePresentations extends AbstractPageObject {

    private final By latestHeadlines = By.xpath("//h1[contains(@class,'ModuleDetailHeadline')]/span[contains(@class,'ModuleDetailHeadlineText')]");
    //private final By latestHeadlineLinks = By.xpath("//a[contains(@id,'hrefDownload')][span]");

    // elements on page of loaded presentation
    private final By presentationImage = By.xpath("//div[@class='ModuleBody']//img");
    private final By documentDownloadLink = By.xpath("//a[contains(@id,'hrefDocument')]");

    public LivePresentations(WebDriver driver) {
        super(driver);
    }

    public Dashboard dashboard(String url) {
        driver.get(url);
        return new Dashboard(getDriver());
    }

    public boolean canFindNewHeadline(String expectedHeadline, boolean desiredState, String[] expectedFilenames){
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

        }

        return foundHeadline;
    }

}