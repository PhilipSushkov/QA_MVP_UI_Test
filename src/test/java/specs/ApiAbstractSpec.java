package specs;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import util.EnvironmentType;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by philipsushkov on 2017-03-08.
 */

public abstract class ApiAbstractSpec extends util.Functions {
    private static final String PATHTO_API_PROP = "api/ApiMap.properties";
    public static Properties propAPI;

    // IMPORTANT:
// Determines which environment the test suite will run on but can be overridden by command line
//------------------------------------------------------------------------------
    private static final EnvironmentType DEFAULT_API_ENV = EnvironmentType.API_DEVELOP;
    //private static final EnvironmentType DEFAULT_API_ENV = EnvironmentType.BETA;
    //private static final EnvironmentType DEFAULT_API_ENV = EnvironmentType.PRODUCTION;
//------------------------------------------------------------------------------

    private static final EnvironmentType activeEnvironment = setupEnvironment();

    @BeforeTest
    public void init() throws IOException {
        setupPropUI();
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
            if ((overrideEnvironment.equals("API_PROD")) || (overrideEnvironment.equals("API_BETA")) || (overrideEnvironment.equals("API_DEV"))) {
                return EnvironmentType.valueOf(overrideEnvironment);
            } else {
                return DEFAULT_API_ENV;
            }
        } else {
            return DEFAULT_API_ENV;
        }
    }

}

