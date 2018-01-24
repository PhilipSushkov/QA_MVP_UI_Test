package specs;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import util.BrowserStackCapability;
import util.BrowserType;
import util.EnvironmentType;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public abstract class AbstractSpec extends util.Functions {

// IMPORTANT:
// Determines which environment the test suite will run on but can be overridden by command line
//------------------------------------------------------------------------------
    //private static final EnvironmentType DEFAULT_ENVIRONMENT = EnvironmentType.DEVELOP;
    //private static final EnvironmentType DEFAULT_ENVIRONMENT = EnvironmentType.BETA;
    private static final EnvironmentType DEFAULT_ENVIRONMENT = EnvironmentType.PRODUCTION;
//------------------------------------------------------------------------------

    private static final EnvironmentType activeEnvironment = setupEnvironment();

    private static final String BROWSER_STACK_URL = "http://jencampbell2:6jEURzbszfaWhLJc7XWx@hub.browserstack.com/wd/hub";
    private static final String BUILD_ID = RandomStringUtils.randomAlphanumeric(6);
    public static final long DEFAULT_TIMEOUT = 25L;
    private static final long DEFAULT_PAUSE = 1500;

    public static URL desktopUrl, desktopUrlPublic;
    public static BrowserStackCapability browser;
    protected WebDriver driver;
    private static boolean setupIsDone = false;
    private static final Logger LOG = Logger.getLogger(AbstractSpec.class.getName());
    public static String sessionID = null;

    // Declare Properties files for every section
    private static final String PATHTO_COMMON_PROP = "CommonMap.properties";
    public static Properties propUICommon;

    @BeforeTest
    public void init(final ITestContext testContext) throws Exception {
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

        //System.out.println(testContext.getName()); // it prints "Check name test"

        switch (getActiveEnvironment()) {
            case DEVELOP:
                //setupLocalDriver();
                setupChromeLocalDriver();
                break;
            case BETA:
                //temp code due to temp use of testing environment
                //setupLocalDriver();
                //setupFirefoxLocalDriver();
                setupChromeLocalDriver();
                break;
            case PRODUCTION:
                setupChromeLocalDriver();
                //setupFirefoxLocalDriver();
                //setupWebDriver(testContext.getName());
                break;
        }

        setupPropUI();
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

    private void setupChromeLocalDriver() throws InterruptedException {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        options.addArguments("no-sandbox");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(capabilities);

        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS); //Increased to 20 to perhaps reduce timeouts?
        //driver.manage().window().setSize(new Dimension(1400, 1400));
        driver.get(desktopUrl.toString());

        //System.out.println(driver.getCurrentUrl());

        int attempts = 5;
        for (int i=0; i<attempts; i++) {
            if (!driver.getCurrentUrl().contains(desktopUrl.toString())) {
                System.out.println("Home site page didn't download yet: "+desktopUrl.toString());
                //System.out.println(driver.getCurrentUrl());
                driver.navigate().refresh();
                driver.get(desktopUrl.toString());
                Thread.sleep(DEFAULT_PAUSE);
            } else {
                break;
            }
        }

    }

    private void setupFirefoxLocalDriver() {
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        firefoxProfile.setPreference("browser.privatebrowsing.autostart", true);
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS); //Increased to 20 to perhaps reduce timeouts?
        driver.manage().window().setSize(new Dimension(1400, 1400));
        driver.get(desktopUrl.toString());
    }


    private void setupWebDriver(String testName) throws Exception {
        DesiredCapabilities capability = browser.toDesiredCapability();
        capability.setCapability("project", getActiveEnvironment().name());
        capability.setCapability("build", getActiveEnvironment().name() + " - " + browser.getPlatformType().name() + " " + browser.getBrowserType().getName() + " " + browser.getBrowserType().getLatestVersion()+ " - " + BUILD_ID);
        capability.setCapability("name", testName);
        capability.setCapability("resolution","1920x1200");
        capability.setCapability("acceptSslCerts", "true");
        capability.setCapability("browserstack.video","false");
        capability.setCapability("browserstack.debug", "false");

        driver = new RemoteWebDriver(new URL(BROWSER_STACK_URL), capability);
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS); //Increased to 20 to perhaps reduce timeouts?
        //driver.manage().window().setSize(new Dimension(1400, 1400));
        driver.get(desktopUrl.toString());
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {

        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                System.out.println(result.getMethod().getMethodName()+": PASS");
                break;

            case ITestResult.FAILURE:
                System.out.println(result.getMethod().getMethodName()+": FAIL");
                break;

            case ITestResult.SKIP:
                System.out.println(result.getMethod().getMethodName()+": SKIP BLOCKED");
                break;

            default:
                throw new RuntimeException(result.getTestName() + "Invalid status");
        }
    }

    @AfterTest(alwaysRun=true)
    public void teardown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }


    public static EnvironmentType getActiveEnvironment() {
        return activeEnvironment;
    }

    private static EnvironmentType setupEnvironment () {

        String overrideEnvironment = System.getProperty("environment");
            if (overrideEnvironment != null) {
                if ((overrideEnvironment.equals("PRODUCTION")) || (overrideEnvironment.equals("BETA")) || (overrideEnvironment.equals("DEVELOP"))) {
                    return EnvironmentType.valueOf(overrideEnvironment);
                } else {
                    return DEFAULT_ENVIRONMENT;
                }
            } else {
                return DEFAULT_ENVIRONMENT;
            }
        }

    public static String getSessionID() {
        return sessionID;
    }

    public static void setSessionID(String sessionIDCookie) {
        sessionID = sessionIDCookie;
    }

    public static void setupPropUI() throws IOException {
        propUICommon = ConnectToPropUI(PATHTO_COMMON_PROP);
    }

}
