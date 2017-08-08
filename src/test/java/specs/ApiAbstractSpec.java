package specs;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import util.EnvironmentType;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by philipsushkov on 2017-03-08.
 */

public abstract class ApiAbstractSpec extends util.Functions {
    private static final String PATHTO_API_PROP = "api/ApiMap.properties";
    public static Properties propAPI;

    // IMPORTANT:
// Determines which environment the test suite will run on but can be overridden by command line
//------------------------------------------------------------------------------
    private static final EnvironmentType DEFAULT_ENV = EnvironmentType.DEVELOP;
    //private static final EnvironmentType DEFAULT_ENV = EnvironmentType.BETA;
    //private static final EnvironmentType DEFAULT_ENV = EnvironmentType.PRODUCTION;
//------------------------------------------------------------------------------

    private static final EnvironmentType activeEnvironment = setupEnvironment();
    private static final Logger LOG = Logger.getLogger(AbstractSpec.class.getName());
    private static boolean setupIsDone = false;
    public static URL adminWebUrl;
    public static final long DEFAULT_TIMEOUT = 5L;
    protected WebDriver driver;
    protected BrowserMobProxy proxy = new BrowserMobProxyServer();

    @BeforeTest
    public void init(final ITestContext testContext) throws Exception {
        if (!setupIsDone) {
            setupEnvironment();
            adminWebUrl = new URL(activeEnvironment.getProtocol() + activeEnvironment.getHost());
            LOG.info("ENV URL: " + adminWebUrl);
            setupIsDone = true;
        }

        switch (getActiveEnvironment()) {
            case DEVELOP:
                setupChromeLocalDriver();
                break;
            case BETA:
                setupChromeLocalDriver();
                break;
            case PRODUCTION:
                setupChromeLocalDriver();
                break;
        }

        setupPropUI();
    }

    private void setupChromeLocalDriver() throws InterruptedException {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        options.addArguments("no-sandbox");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        if (proxy.isStarted()) {
            proxy.stop();
        }

        proxy.start(0);
        System.out.println("Proxy port: "+ proxy.getPort());

        Proxy selProxy = ClientUtil.createSeleniumProxy(proxy);
        capabilities.setCapability(CapabilityType.PROXY, selProxy);
        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

        LoggingPreferences loggingprefs = new LoggingPreferences();
        loggingprefs.enable(LogType.BROWSER, Level.ALL);
        capabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);

        driver = new ChromeDriver(capabilities);

        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS); //Increased to 20 to perhaps reduce timeouts?

        driver.get(adminWebUrl.toString());
    }

    public static void setupPropUI() throws IOException {
        propAPI = ConnectToPropUI(PATHTO_API_PROP);
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

    public static EnvironmentType getActiveEnvironment() {
        return activeEnvironment;
    }

    private static EnvironmentType setupEnvironment () {

        String overrideEnvironment = System.getProperty("environment");
        if (overrideEnvironment != null) {
            if ((overrideEnvironment.equals("PRODUCTION")) || (overrideEnvironment.equals("BETA")) || (overrideEnvironment.equals("DEVELOP"))) {
                return EnvironmentType.valueOf(overrideEnvironment);
            } else {
                return DEFAULT_ENV;
            }
        } else {
            return DEFAULT_ENV;
        }
    }

    @AfterTest(alwaysRun=true)
    public void teardown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
        proxy.stop();
    }

}

