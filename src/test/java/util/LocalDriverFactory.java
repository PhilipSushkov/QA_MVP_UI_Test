package util;

/**
 * Created by philipsushkov on 2016-11-29.
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import specs.AbstractSpec;

public class LocalDriverFactory {
    public static WebDriver createInstance() {
        WebDriver driver = null;
        //driver = new ChromeDriver();
        //return driver;

        /*
        if (AbstractSpec.browser.getBrowserType() != null) {
            System.out.println(AbstractSpec.browser.getBrowserType());
        }
        */


        if (AbstractSpec.browser.getBrowserType() == BrowserType.FIREFOX) {
            driver = new FirefoxDriver();
            return driver;
        } else
        if (AbstractSpec.browser.getBrowserType() == BrowserType.CHROME) {
            driver = new ChromeDriver();
            return driver;
        } else
        if (AbstractSpec.browser.getBrowserType() == BrowserType.INTERNET_EXPLORER) {
            driver = new InternetExplorerDriver();
            return driver;
        } else
        if (AbstractSpec.browser.getBrowserType() == BrowserType.OPERA) {
            driver = new OperaDriver();
            return driver;
        } else {
            driver = new FirefoxDriver();
            return driver;
        }


    }


}
