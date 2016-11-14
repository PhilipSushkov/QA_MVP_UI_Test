package specs;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import util.BrowserStackCapability;
import org.openqa.selenium.remote.DesiredCapabilities;
import util.BrowserType;
import util.EnvironmentType;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public abstract class AbstractSpec {

// IMPORTANT:
// Determines which environment the test suite will run on but can be overridden by command line
//------------------------------------------------------------------------------
    private static final EnvironmentType DEFAULT_ENVIRONMENT = EnvironmentType.BETA;
    //private static final EnvironmentType DEFAULT_ENVIRONMENT = EnvironmentType.PRODUCTION;
//------------------------------------------------------------------------------

    private static final EnvironmentType activeEnvironment = setupEnvironment();

    private static final String BROWSER_STACK_URL = "http://jencampbell2:6jEURzbszfaWhLJc7XWx@hub.browserstack.com/wd/hub";
    private static final String BUILD_ID = RandomStringUtils.randomAlphanumeric(6);
    public static final long DEFAULT_TIMEOUT = 5L;

    private static URL desktopUrl;
    private static BrowserStackCapability browser;
    protected static WebDriver driver;
    private static boolean setupIsDone = false;
    private static final Logger LOG = Logger.getLogger(AbstractSpec.class.getName());

    @Rule
    public TestName testName = new TestName();

    @Before
    public void init() throws Exception {
        if (!setupIsDone) {
            setupEnvironment();

            desktopUrl = new URL(activeEnvironment.getProtocol() + activeEnvironment.getHost());

            LOG.info("ENV URL: " + desktopUrl);

            browser = new BrowserStackCapability(Platform.WIN8, BrowserType.CHROME, null);
            //browser = new BrowserStackCapability(Platform.WIN8, BrowserType.OPERA, null);
            //browser = new BrowserStackCapability(Platform.WIN8, BrowserType.INTERNET_EXPLORER, null);
            //browser = new BrowserStackCapability(Platform.WIN8, BrowserType.FIREFOX, null);

            setupIsDone = true;
        }

        switch (getActiveEnvironment()) {
            case DEVELOP:
                //setupLocalDriver();
                setupChromeLocalDriver();
                break;
            case BETA:
                //temp code due to temp use of testing environment
                //setupLocalDriver();
                setupChromeLocalDriver();
                break;
            case PRODUCTION:
                setupWebDriver();
                break;
        }
    }

    private void setupLocalDriver() throws UnknownHostException {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setJavascriptEnabled(true);
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {"--web-security=no", "--ignore-ssl-errors=yes", "--load-images=false"});
        driver = new PhantomJSDriver(caps);
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.get(desktopUrl.toString());

        //driver.setJavascriptEnabled(true);
        //driver.manage().window().setSize(new Dimension(1400, 1400));
        //driver.get("https://aestest.s1.q4web.newtest");
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //System.out.println(driver.getPageSource());

    }

    private void setupChromeLocalDriver() {

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(1400, 1400));
        driver.get(desktopUrl.toString());

    }

    private void setupWebDriver() throws Exception {
        String testMethodName = testName.getMethodName();

        DesiredCapabilities capability = browser.toDesiredCapability();
        capability.setCapability("project", getActiveEnvironment().name());
        capability.setCapability("build", getActiveEnvironment().name() + " - " + browser.getPlatformType().name() + " " + browser.getBrowserType().getName() + " " + browser.getBrowserType().getLatestVersion()+ " - " + BUILD_ID);
        capability.setCapability("name", testMethodName);
        capability.setCapability("resolution","1920x1200");
        capability.setCapability("acceptSslCerts", "true");
        capability.setCapability("browserstack.video","false");
        capability.setCapability("browserstack.debug", "false");


        driver = new RemoteWebDriver(new URL(BROWSER_STACK_URL), capability);
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(1400, 1400));

        driver.get(desktopUrl.toString());

    }

    @After
    public void teardownWebDriver() {

        /*
        if (getActiveEnvironment() != EnvironmentType.BETA){
            driver.quit();
        }
        */

        /*
        if (getActiveEnvironment() != EnvironmentType.DEVELOP) {
            if (getActiveEnvironment() != EnvironmentType.BETA) //temp code due to temp use of testing environment

                driver.quit();
        }
        */

        driver.quit();
    }


    public static EnvironmentType getActiveEnvironment() {

        return activeEnvironment;
    }

    private static EnvironmentType setupEnvironment () {
        String overrideEnvironment = System.getProperty("q4inc.specs");
        if (overrideEnvironment != null) {
            return EnvironmentType.valueOf(overrideEnvironment);
        } else {
            return DEFAULT_ENVIRONMENT;
        }
    }
}