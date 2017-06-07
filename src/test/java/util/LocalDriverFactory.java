package util;

/**
 * Created by philipsushkov on 2016-11-29.
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocalDriverFactory {
    public static final long DEFAULT_TIMEOUT = 5L;

    public static WebDriver createInstance(String browserName) {
        WebDriver phDriver = null;

        switch (browserName) {
            case "chrome":

                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("incognito");
                options.addArguments("no-sandbox");
                capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                phDriver = new ChromeDriver(capabilities);

                phDriver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                phDriver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS); //Increased to 20 to perhaps reduce timeouts?

                return phDriver;

            case "phantom":

                Logger.getLogger(PhantomJSDriverService.class.getName()).setLevel(Level.OFF);

                DesiredCapabilities caps = new DesiredCapabilities();
                String[] phantomArgs = new  String[] {"--web-security=no", "--ignore-ssl-errors=yes", "--webdriver-loglevel=NONE"};
                caps.setJavascriptEnabled(true);
                caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);
                phDriver = new PhantomJSDriver(caps);

                phDriver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                phDriver.manage().timeouts().pageLoadTimeout(45, TimeUnit.SECONDS); //Increased to 20 to perhaps reduce timeouts?

                return phDriver;

            default:
                return phDriver;
        }

    }

}
