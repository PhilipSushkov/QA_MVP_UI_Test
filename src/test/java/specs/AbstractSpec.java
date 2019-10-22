package specs;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import util.EnvironmentType;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by PSUSHKOV on Aug, 2018
 **/

public abstract class AbstractSpec extends util.Functions {

    // Determines which environment the test suite will run on but can be overridden by command line
    //private static final EnvironmentType DEFAULT_ENVIRONMENT = EnvironmentType.DEV;
    private static final EnvironmentType DEFAULT_ENVIRONMENT = EnvironmentType.QA;
    //private static final EnvironmentType DEFAULT_ENVIRONMENT = EnvironmentType.PROD;

    private static final EnvironmentType activeEnvironment = setupEnvironment();
    public static final long DEFAULT_TIMEOUT = 25L;
    private static final long DEFAULT_PAUSE = 1500;

    public static URL storeUrl;
    protected WebDriver driver;
    private static boolean setupIsDone = false;
    private static final Logger LOG = Logger.getLogger(AbstractSpec.class.getName());

    // Declare Properties files for every section
    private static final String PATHTO_PRODCATEGORY_PROP = "ProductCategory/ProductCategory.properties";
    private static final String PATHTO_MYACCOUNT_PROP = "MyAccount/MyAccount.properties";
    public static Properties propUIProdCategory, propUIMayAccount;

    @BeforeTest
    public void init(final ITestContext testContext) throws Exception {
        if (!setupIsDone) {
            setupEnvironment();

            storeUrl = new URL(activeEnvironment.getProtocol() + activeEnvironment.getHost());
            LOG.info("ENV URL: " + storeUrl);

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
        ChromeOptions ChromeOptions = new ChromeOptions();

        ChromeOptions.addArguments("window-size=1920,1080", "--no-sandbox", "--incognito");

        driver = new ChromeDriver(ChromeOptions);
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS); //Increased to 30 to perhaps reduce timeouts?

        // #1 Open the site store.demoqa.com
        driver.get(storeUrl.toString());
        Thread.sleep(DEFAULT_PAUSE);
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
    public void teardown() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }


    public static EnvironmentType getActiveEnvironment() {
        return activeEnvironment;
    }

    private static EnvironmentType setupEnvironment() {

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

    public static void setupPropUI() {
        propUIProdCategory = ConnectToPropUI(PATHTO_PRODCATEGORY_PROP);
        propUIMayAccount = ConnectToPropUI(PATHTO_MYACCOUNT_PROP);
    }

    public Object[][] genericProvider(String dataType, String sPathToDataFile) {
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToDataFile));
            JSONArray data = (JSONArray) jsonObject.get(dataType);
            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < data.size(); i++) {
                JSONObject pageObj = (JSONObject) data.get(i);
                if (Boolean.parseBoolean(pageObj.get("do_assertions").toString())) {
                    zoom.add(data.get(i));
                }
            }

            Object[][] newData = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                newData[i][0] = zoom.get(i);
            }

            return newData;

        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}
