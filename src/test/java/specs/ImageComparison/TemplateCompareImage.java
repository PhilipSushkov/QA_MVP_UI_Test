package specs.ImageComparison;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import util.Functions;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by philipsushkov on 2017-04-17.
 */

public class TemplateCompareImage {
    private WebDriver phDriver;
    private static final long DEFAULT_PAUSE = 1500;

    @Before
    public void setUp() {
        Logger.getLogger(PhantomJSDriverService.class.getName()).setLevel(Level.OFF);

        DesiredCapabilities caps = new DesiredCapabilities();
        String[] phantomArgs = new  String[] {"--web-security=no", "--ignore-ssl-errors=yes", "--webdriver-loglevel=NONE"};
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomArgs);
        phDriver = new PhantomJSDriver(caps);
        phDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }


    @Test
    public void templateCompareImage() throws InterruptedException {
        phDriver.get("http://ir1.euroinvestor.com/asp/ir/AGL/calc.aspx");
        String pathExp = Functions.takeScreenshot(phDriver, "calc.aspx", "Exp/AGL");
        System.out.println(pathExp);

        Thread.sleep(DEFAULT_PAUSE);

        phDriver.get("http://q4eurotestir1.q4web.com/asp/ir/AGL/calc.aspx");
        String pathAct = Functions.takeScreenshot(phDriver, "calc.aspx", "Act/AGL");
        System.out.println(pathAct);

        Thread.sleep(DEFAULT_PAUSE);
    }


    @After
    public void tearDown() {
        phDriver.quit();
    }
}
