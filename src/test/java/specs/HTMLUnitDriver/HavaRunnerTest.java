package specs.HTMLUnitDriver;

/**
 * Created by philipsushkov on 2016-11-29.
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;
import util.LocalDriverFactory;
import util.LocalDriverManager;

import org.junit.Before;

public class HavaRunnerTest extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        //new LoginPage(driver).loginUser();
    }


    @Test
    public void Test1() throws Exception {
        WebDriver driver = LocalDriverManager.getDriver();
        //LocalDriverManager.setWebDriver(driver);
        driver.get("https://www.amazon.com");
        Thread.sleep(2000);
        System.out.println(driver.getTitle());
        driver.quit();
    }

    @Test
    public void Test2() throws Exception {
        WebDriver driver = LocalDriverManager.getDriver();
        //LocalDriverManager.setWebDriver(driver);
        driver.get("https://www.facebook.com");
        Thread.sleep(2000);
        System.out.println(driver.getTitle());
        driver.quit();
    }

    @Test
    public void Test3() throws Exception {
        WebDriver driver = LocalDriverManager.getDriver();
        //LocalDriverManager.setWebDriver(driver);
        driver.get("http://www.cnn.com");
        Thread.sleep(2000);
        System.out.println(driver.getTitle());
        driver.quit();
    }

    @Test
    public void Test4() throws Exception {
        WebDriver driver = LocalDriverManager.getDriver();
        //LocalDriverManager.setWebDriver(driver);
        driver.get("http://www.ebay.com");
        Thread.sleep(2000);
        System.out.println(LocalDriverManager.getDriver().getTitle());
        driver.quit();
    }

    @Test
    public void Test5() throws Exception {
        WebDriver driver = LocalDriverManager.getDriver();
        //LocalDriverManager.setWebDriver(driver);
        driver.get("http://www.cnn.com");
        Thread.sleep(2000);
        System.out.println(driver.getTitle());
        driver.quit();
    }

    @Test
    public void Test6() throws Exception {
        WebDriver driver = LocalDriverManager.getDriver();
        //LocalDriverManager.setWebDriver(driver);
        driver.get("http://www.bing.com");
        Thread.sleep(2000);
        System.out.println(driver.getTitle());
        driver.quit();
    }


}
