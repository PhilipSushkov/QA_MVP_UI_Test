package specs.MyAccount;

import com.aventstack.extentreports.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import specs.AbstractSpec;
import util.Log;
import java.lang.reflect.Method;


/**
 * Created by PSUSHKOV on Oct, 2019
 **/

public class CheckMyAccount extends AbstractSpec {
    private static ExtentReports myAccountRep;
    private static String sPathToFile, sDataFileJson;
    private static final long DEFAULT_PAUSE = 3000;
    private final String DATA = "myAccountData", NAME = "myAccount";


    @BeforeTest
    public void setUp() {
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
