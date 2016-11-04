package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.Dashboard.Dashboard;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LivePressReleases extends AbstractPageObject{

    private final By latestHeadlines = By.xpath("//a[contains(@id,'hrefHeadline')]/span[contains(@class,'ModuleHeadline')]");
    private final By latestHeadlineLinks = By.xpath("//a[contains(@id,'hrefHeadline')][span]");

    // elements on page of loaded press release
    private final By pressReleaseImage = By.xpath("//div[@class='ModuleBody']//img");
    private final By documentDownloadLink = By.xpath("//a[contains(@id,'hrefDownload')]");

    public LivePressReleases(WebDriver driver) {

        super(driver);
    }

    public Dashboard dashboard(String url) {
        driver.get(url);
        return new Dashboard(getDriver());
    }

    public boolean canFindNewHeadline(String expectedHeadline, boolean desiredState, String[] expectedFilenames){
        List<WebElement> headlines;
        List<WebElement> headlineLinks;
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
                driver.navigate().refresh();
            } catch (TimeoutException e) {
                driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
            }

            headlines = findElements(latestHeadlines);
            headlineLinks = findElements(latestHeadlineLinks);

            for (int i=0; i<headlines.size(); i++){
                System.out.println("HEADLINE: "+headlines.get(i).getText());
                if (headlines.get(i).getText().equalsIgnoreCase(expectedHeadline)) {
                    foundHeadline = true;
                    if(desiredState){
                        try {
                            headlineLinks.get(i).click();
                        } catch (TimeoutException e) {
                            driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
                        }
                        wait.until(ExpectedConditions.visibilityOf(findElements(pressReleaseImage).get(0)));
                        String[] foundFilenames = new String[2];
                        foundFilenames[0] = findElements(pressReleaseImage).get(0).getAttribute("src");
                        foundFilenames[0] = foundFilenames[0].substring(foundFilenames[0].indexOf("files/")+6);
                        System.out.println("Found image file: "+foundFilenames[0]);
                        if (!foundFilenames[0].equals(expectedFilenames[0])){
                            foundHeadline = false;
                            System.out.println("ERROR: Image filename doesn't match.");
                        }
                        foundFilenames[1] = findElements(documentDownloadLink).get(0).getAttribute("href");
                        foundFilenames[1] = foundFilenames[1].substring(foundFilenames[1].indexOf("files/")+6);
                        System.out.println("Found attached document: "+foundFilenames[1]);
                        if (!foundFilenames[1].equals(expectedFilenames[1])){
                            foundHeadline = false;
                            System.out.println("ERROR: Attached document filename doesn't match.");
                        }
                    }
                    break;
                }
                else {
                    foundHeadline = false;
                }
            }
        }

        return foundHeadline;
    }
}
