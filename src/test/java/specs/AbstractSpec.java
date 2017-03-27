package specs;

import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
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
    //private static final EnvironmentType DEFAULT_ENVIRONMENT = EnvironmentType.BETA;
    private static final EnvironmentType DEFAULT_ENVIRONMENT = EnvironmentType.PRODUCTION;
//------------------------------------------------------------------------------

    private static final EnvironmentType activeEnvironment = setupEnvironment();

    private static final String BROWSER_STACK_URL = "http://jencampbell2:6jEURzbszfaWhLJc7XWx@hub.browserstack.com/wd/hub";
    private static final String BUILD_ID = RandomStringUtils.randomAlphanumeric(6);
    public static final long DEFAULT_TIMEOUT = 5L;

    public static URL desktopUrl, desktopUrlPublic;
    public static BrowserStackCapability browser;
    protected WebDriver driver;
    private static boolean setupIsDone = false;
    private static final Logger LOG = Logger.getLogger(AbstractSpec.class.getName());
    public static String sessionID = null;

    // Declare Properties files for every section
    private static final String PATHTO_SYSTEMADMIN_PROP = "SystemAdmin/SystemAdminMap.properties";
    public static Properties propUISystemAdmin;
    private static final String PATHTO_SITEADMIN_PROP = "SiteAdmin/SiteAdminMap.properties";
    public static Properties propUISiteAdmin;
    private static final String PATHTO_CONTENTADMIN_PROP = "ContentAdmin/ContentAdminMap.properties";
    public static Properties propUIContentAdmin;
    private static final String PATHTO_EMAILADMIN_PROP = "EmailAdmin/EmailAdminMap.properties";
    public static Properties propUIEmailAdmin;
    private static final String PATHTO_COMMON_PROP = "CommonMap.properties";
    public static Properties propUICommon;
    private static final String PATHTO_PUBLICSITE_PROP = "PublicSite/PublicSiteMap.properties";
    public static Properties propUIPublicSite;
    private static final String PATHTO_SOCIALMEDIA_PROP = "SocialMedia/SocialMediaMap.properties";
    public static Properties propUISocialMedia;
    private static final String PATHTO_PAGEADMIN_PROP = "PageAdmin/PageAdminMap.properties";
    public static Properties propUIPageAdmin;


    @BeforeTest
    public void init(final ITestContext testContext) throws Exception {
        if (!setupIsDone) {
            setupEnvironment();

            desktopUrl = new URL(activeEnvironment.getProtocol() + activeEnvironment.getHost());
            desktopUrlPublic = new URL("http://chicagotest.q4web.release/");

            LOG.info("ENV URL: " + desktopUrl);

            browser = new BrowserStackCapability(Platform.WIN8, BrowserType.CHROME, null);
            //browser = new BrowserStackCapability(Platform.WIN8, BrowserType.OPERA, null);
            //browser = new BrowserStackCapability(Platform.WIN8, BrowserType.INTERNET_EXPLORER, null);
            //browser = new BrowserStackCapability(Platform.WIN8, BrowserType.FIREFOX, null);

            setupIsDone = true;
        }

        switch (getActiveEnvironment()) {
            case DEVELOP:
                setupChromeLocalDriver();
                break;
            case BETA:
                //setupFirefoxLocalDriver();
                setupChromeLocalDriver();
                break;
            case PRODUCTION:
                setupChromeLocalDriver();
                //setupWebDriver(testContext.getName());
                break;
        }

        setupPropUI();
    }

    private void setupChromeLocalDriver() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        options.addArguments("no-sandbox");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(capabilities);

        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS); //Increased to 20 to perhaps reduce timeouts?
        //driver.manage().window().setSize(new Dimension(1400, 1400));
        driver.get(desktopUrl.toString());
    }

    private void setupFirefoxLocalDriver() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
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
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS); //Increased to 20 to perhaps reduce timeouts?
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
        propUISystemAdmin = ConnectToPropUI(PATHTO_SYSTEMADMIN_PROP);
        propUISiteAdmin = ConnectToPropUI(PATHTO_SITEADMIN_PROP);
        propUIContentAdmin = ConnectToPropUI(PATHTO_CONTENTADMIN_PROP);
        propUIEmailAdmin = ConnectToPropUI(PATHTO_EMAILADMIN_PROP);
        propUICommon = ConnectToPropUI(PATHTO_COMMON_PROP);
        propUIPublicSite = ConnectToPropUI(PATHTO_PUBLICSITE_PROP);
        propUISocialMedia = ConnectToPropUI(PATHTO_SOCIALMEDIA_PROP);
        propUIPageAdmin = ConnectToPropUI(PATHTO_PAGEADMIN_PROP);
    }

}