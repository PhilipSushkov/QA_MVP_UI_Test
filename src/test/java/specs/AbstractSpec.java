package specs;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import util.EnvironmentType;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public abstract class AbstractSpec extends util.Functions {

// Determines which environment the test suite will run on but can be overridden by command line
//------------------------------------------------------------------------------
    //private static final EnvironmentType DEFAULT_ENVIRONMENT = EnvironmentType.DEV;
    private static final EnvironmentType DEFAULT_ENVIRONMENT = EnvironmentType.QA;
    //private static final EnvironmentType DEFAULT_ENVIRONMENT = EnvironmentType.PROD;
//------------------------------------------------------------------------------

    private static final EnvironmentType activeEnvironment = setupEnvironment();
    public static final long DEFAULT_TIMEOUT = 25L;
    private static final long DEFAULT_PAUSE = 1500;

    public static URL desktopUrl;
    protected WebDriver driver;
    private static boolean setupIsDone = false;
    private static final Logger LOG = Logger.getLogger(AbstractSpec.class.getName());

    // Declare Properties files for every section
    private static final String PATHTO_COMMON_PROP = "CommonMap.properties";
    public static Properties propUICommon;

    @BeforeTest
    public void init(final ITestContext testContext) throws Exception {
        if (!setupIsDone) {
            setupEnvironment();

            desktopUrl = new URL(activeEnvironment.getProtocol() + activeEnvironment.getHost());

            LOG.info("ENV URL: " + desktopUrl);

            setupIsDone = true;
        }

        switch (getActiveEnvironment()) {
            case DEV:
                setupChromeDriver();
                break;
            case QA:
                setupChromeDriver();
                break;
            case PROD:
                setupChromeDriver();
                break;
        }

        setupPropUI();
    }

    private void setupChromeDriver() throws InterruptedException {
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
                if ((overrideEnvironment.equals("PROD")) || (overrideEnvironment.equals("QA")) || (overrideEnvironment.equals("DEV"))) {
                    return EnvironmentType.valueOf(overrideEnvironment);
                } else {
                    return DEFAULT_ENVIRONMENT;
                }
            } else {
                return DEFAULT_ENVIRONMENT;
            }
        }

    public static void setupPropUI() throws IOException {
        propUICommon = ConnectToPropUI(PATHTO_COMMON_PROP);
    }

}
