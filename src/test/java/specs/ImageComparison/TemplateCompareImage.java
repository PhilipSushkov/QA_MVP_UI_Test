package specs.ImageComparison;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
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
    private By byCopyright = By.xpath("//td[contains(text(), 'Copyright')]");

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
        Thread.sleep(DEFAULT_PAUSE);

        WebElement eCopyrightExp = phDriver.findElement(byCopyright);
        hideElement(eCopyrightExp, phDriver);

        String pathExp = Functions.takeScreenshot(phDriver, "calc.aspx", "Exp/AGL");
        System.out.println(pathExp);

        phDriver.get("http://q4eurotestir1.q4web.com/asp/ir/AGL/calc.aspx");
        Thread.sleep(DEFAULT_PAUSE);

        WebElement eCopyrightCur = phDriver.findElement(byCopyright);
        hideElement(eCopyrightCur, phDriver);

        String pathAct = Functions.takeScreenshot(phDriver, "calc.aspx", "Cur/AGL");
        System.out.println(pathAct);

        String pathDiff = System.getProperty("user.dir") + "/src/test/java/specs/ImageComparison/ScreenShots/" + "Cur/AGL" + "/" + "diff_calc.aspx.png";
        System.out.println(pathDiff);
        Assert.assertTrue(Functions.compareImages(pathExp, pathAct, pathDiff));
    }


    public void  hideElement(WebElement e, WebDriver d) {
        ((JavascriptExecutor)d).executeScript("arguments[0].style.visibility='hidden'", e);
    }

    @After
    public void tearDown() {
        phDriver.quit();
    }
}
