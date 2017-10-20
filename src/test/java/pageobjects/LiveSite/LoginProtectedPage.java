package pageobjects.LiveSite;


import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;

import java.util.ArrayList;

import static specs.AbstractSpec.propUIPublicSite;

/**
 * Created by charleszheng on 2017-10-19.
 */

/* Designed for chicagotest.s3.q4web.com/admin/
* Before running automation, please setup admin site
* Instructions given in:
* /QA-WebCMS-Test/src/test/java/specs/PublicSite/login_protected_page.feature
*/

public class LoginProtectedPage extends AbstractPageObject{

    private final String ORIGINPAGE = "Test";
    private final String TARGETPAGE = "Stock Information";
    private final String LOGINNAME = "test@q4inc.com";
    private final String LOGINPASSWORD = "q4pass1234!";

    private static final long DEFAULT_PAUSE = 2500;

    private final By loginName;
    private final By loginPassword;
    private final By loginBtn;
    private final By logoutBtn;
    private final By originPageTab;
    private final By originPageEdit;
    private final By targetPageTab;
    private final By targetPageEdit;
    private final By pageUrl;

    public LoginProtectedPage(WebDriver driver){
        super(driver);
        loginName = By.xpath(propUIPublicSite.getProperty("loginName"));
        loginPassword = By.xpath(propUIPublicSite.getProperty("loginPassword"));
        loginBtn = By.xpath(propUIPublicSite.getProperty("loginBtn"));
        logoutBtn = By.xpath(propUIPublicSite.getProperty("logoutBtn"));
        originPageTab = By.xpath("//span[text()='" + ORIGINPAGE + "']");
        targetPageTab = By.xpath("//span[text()='Investors']");
        originPageEdit = By.xpath("//td[text()='- " + ORIGINPAGE + "']/parent::tr/td/input[contains(@id, 'Imagebutton')]");
        targetPageEdit = By.xpath("//td[text()='-- " + TARGETPAGE + "']/parent::tr/td/input[contains(@id, 'Imagebutton')]");
        pageUrl = By.xpath(propUIPublicSite.getProperty("pageUrl"));
    }

    public boolean targetPageAccessible(String targetPageUrl) throws InterruptedException{
        ((JavascriptExecutor)driver).executeScript("window.open();");

        Thread.sleep(DEFAULT_PAUSE);

        ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));


        try {
            driver.get(targetPageUrl + "/default.aspx");
            Thread.sleep(DEFAULT_PAUSE);
        } catch (TimeoutException e) {
            driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
        }

        if (driver.getTitle().contains(TARGETPAGE)){
            driver.switchTo().window(tabs.get(1)).close();
            driver.switchTo().window(tabs.get(0));

            System.out.println(TARGETPAGE + "Target Page is accessible");
            return true;
        }  else {
            driver.switchTo().window(tabs.get(1)).close();
            driver.switchTo().window(tabs.get(0));
            System.out.println(TARGETPAGE + "Target Page is not accessible");
            return false;
        }
    }

    public void loginToTargetPage(String originPageUrl) throws InterruptedException{
        ((JavascriptExecutor)driver).executeScript("window.open();");

        Thread.sleep(DEFAULT_PAUSE);

        ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        try {
            driver.get(originPageUrl);
            waitForElement(loginName);
            findElement(loginName).sendKeys(LOGINNAME);
            findElement(loginPassword).sendKeys(LOGINPASSWORD);
            findElement(loginBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
        } catch (NoSuchElementException e)
        {
            e.printStackTrace();
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        driver.switchTo().window(tabs.get(1)).close();
        driver.switchTo().window(tabs.get(0));
    }

    public void logoutFromTargetPage(String originPageUrl) throws InterruptedException{
        ((JavascriptExecutor)driver).executeScript("window.open();");

        Thread.sleep(DEFAULT_PAUSE);

        ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));


        try {
            driver.get(originPageUrl);
            waitForElement(logoutBtn);
            findElement(logoutBtn).click();
        } catch (NoSuchElementException e)
        {
            e.printStackTrace();
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        driver.switchTo().window(tabs.get(1)).close();
        driver.switchTo().window(tabs.get(0));
    }

    public String getOriginPageUrl() throws InterruptedException{
        waitForElement(originPageTab);
        findElement(originPageTab).click();
        waitForElement(originPageEdit);
        findElement(originPageEdit).click();
        waitForElement(pageUrl);
        Thread.sleep(DEFAULT_PAUSE);
        return findElement(pageUrl).getText();
    }

    public String getTargetPageUrl() throws InterruptedException{
        waitForElement(targetPageTab);
        findElement(targetPageTab).click();
        waitForElement(targetPageEdit);
        findElement(targetPageEdit).click();
        waitForElement(pageUrl);
        Thread.sleep(DEFAULT_PAUSE);
        return findElement(pageUrl).getText();
    }

}
