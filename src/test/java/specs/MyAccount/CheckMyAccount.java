package specs.MyAccount;

import com.aventstack.extentreports.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.MyAccount.MyAccount;
import specs.AbstractSpec;
import util.Log;
import java.lang.reflect.Method;


/**
 * Created by PSUSHKOV on Oct, 2019
 **/

public class CheckMyAccount extends AbstractSpec {
    private static MyAccount myAccount;
    private static ExtentReports myAccountRep;
    private static String sPathToFile, sDataFileJson;
    private static final long DEFAULT_PAUSE = 3000;
    private final String DATA = "myAccountData", NAME = "myAccount";


    @BeforeTest
    public void setUp() {
        myAccount = new MyAccount(driver);

        sPathToFile = System.getProperty("user.dir") + propUIMayAccount.getProperty("dataPath_MyAccount");
        sDataFileJson = propUIMayAccount.getProperty("json_MyAccountData");
        myAccountRep = RepMyAccount.GetExtent();
    }

    @BeforeMethod
    public void beforeMethod() throws InterruptedException {
        driver.get(storeUrl+"/my-account/");
        Thread.sleep(DEFAULT_PAUSE);
    }

    @Test(dataProvider = DATA, priority=1)
    public void checkLoginForm(JSONObject data, Method method) throws InterruptedException {
        Log.info(method.getName() + " test is starting.");

        // Create the report
        ExtentTest test = myAccountRep.createTest(data.get("name").toString() +"<br>" + data.get("description").toString());


        // Split test cases for negative and positive
        switch (data.get("test_type").toString()) {
            case "positive":
                Log.info(method.getName() + " positive test has been finished");
                break;

            case "negative":
                String actErrorMessage = myAccount.getErrorMessage();
                String expErrorMessage = data.get("expErrorMessage").toString();

                if (actErrorMessage.equals(expErrorMessage)) {
                    test.log(Status.PASS, "Actual Login Error message equals expected one: <b>" + actErrorMessage + "</b>");
                } else {
                    test.log(Status.FAIL, "Actual Login Error message doesn't equal expected one: <b>" + actErrorMessage + "</b>. " +
                            "Supposed to be: <b>" + expErrorMessage + "</b>");
                }

                Assert.assertTrue(actErrorMessage.equals(expErrorMessage),
                        "Actual Login Error message: " + actErrorMessage + " doesn't match expected one: " + expErrorMessage);

                Log.info(method.getName() + " negative test has been finished");
                break;
        }

    }

    @DataProvider(name = DATA)
    public Object[][] buyItemData() {
        String sPathToDataFile = sPathToFile + sDataFileJson;
        return genericProvider(NAME, sPathToDataFile);
    }

    @AfterMethod
    public void afterMethod() {
    }

    @AfterTest
    public void tearDown(){
        myAccountRep.flush();
    }

}
