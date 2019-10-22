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
        sPathToFile = System.getProperty("user.dir") + propUIProdCategory.getProperty("dataPath_BuyItem");
        sDataFileJson = propUIProdCategory.getProperty("json_BuyItemData");
        myAccountRep = RepMyAccount.GetExtent();
    }

    @BeforeMethod
    public void beforeMethod() throws InterruptedException {

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
