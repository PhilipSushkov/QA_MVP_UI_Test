package specs.MyAccount;

import com.aventstack.extentreports.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.ProductCategory.Accessories.*;
import specs.AbstractSpec;
import util.Log;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by PSUSHKOV on Oct, 2019
 **/

public class CheckMyAccount extends AbstractSpec {
    private static ExtentReports myAccountRep;
    private static String sPathToFile, sDataFileJson;
    private static final long DEFAULT_PAUSE = 3000;
    private final String DATA = "myAccountData", NAME = "myAccount";

}
