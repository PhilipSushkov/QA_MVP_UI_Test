package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;
import pageobjects.Dashboard.Dashboard;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LivePressReleases extends AbstractPageObject{

    private final By latestHeadlines = By.xpath("//a[contains(@id,'hrefHeadline')]/span[contains(@class,'ModuleHeadline')]");

    public LivePressReleases(WebDriver driver) {
        super(driver);
    }

    public Dashboard dashboard(String url) {

        driver.get(url);

        return new Dashboard(getDriver());
    }

    public boolean canFindNewHeadline(String expectedHeadline, boolean desiredState){
        List<WebElement> headlines = findElements(latestHeadlines);
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

            for (WebElement headline : headlines){
                if (headline.getText().equals(expectedHeadline)) {
                    foundHeadline = true;
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
