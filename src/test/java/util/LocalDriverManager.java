package util;

/**
 * Created by philipsushkov on 2016-11-29.
 */

import net.lightbody.bmp.BrowserMobProxy;
import org.openqa.selenium.WebDriver;

public class LocalDriverManager {
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
    private static ThreadLocal<BrowserMobProxy> proxy = new ThreadLocal<BrowserMobProxy>();

    public static WebDriver getDriver() {
        return webDriver.get();
    }

    public static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }

    public static BrowserMobProxy getProxy() {
        return proxy.get();
    }

    public static void setProxy(BrowserMobProxy bProxy) {
        proxy.set(bProxy);
    }

}
