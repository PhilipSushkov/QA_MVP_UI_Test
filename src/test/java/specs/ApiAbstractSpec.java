package specs;

import org.testng.ITestResult;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by philipsushkov on 2017-03-08.
 */

public abstract class ApiAbstractSpec extends util.Functions {
    private static final String PATHTO_API_PROP = "api/ApiMap.properties";
    public static Properties propAPI;
    private static final String PATHTO_EUROAPI_PROP = "euroApi/EuroApiMap.properties";
    public static Properties propEuroAPI;

    @BeforeTest
    public void init() throws IOException {
        setupPropUI();
    }

    public static void setupPropUI() throws IOException {
        propAPI = ConnectToPropUI(PATHTO_API_PROP);
        propEuroAPI = ConnectToPropUI(PATHTO_EUROAPI_PROP);
    }

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
}

