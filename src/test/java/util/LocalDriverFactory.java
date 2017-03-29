package util;

/**
 * Created by philipsushkov on 2016-11-29.
 */

import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LocalDriverFactory {

    public static WebDriver createInstance() {

        Logger.getLogger(PhantomJSDriverService.class.getName()).setLevel(Level.OFF);

        WebDriver phDriver;

        DesiredCapabilities caps = new DesiredCapabilities();
        String[] phantomArgs = new  String[] {"--web-security=no", "--ignore-ssl-errors=yes", "--webdriver-loglevel=NONE"};
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);
        phDriver = new PhantomJSDriver(caps);

        return phDriver;
    }

}
