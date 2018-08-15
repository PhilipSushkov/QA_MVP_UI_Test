package specs.ProductCategory.Accessories;

import com.aventstack.extentreports.ExtentReports;
import org.json.simple.JSONObject;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pageobjects.ProductCategory.Accessories.BuyItem;
import specs.AbstractSpec;

public class CheckBuyItem extends AbstractSpec {
    private static BuyItem buyItem;
    private static ExtentReports buyItemReport;
    private static String sPathToFile, sDataFileJson;

    private final String BUY_ITEM_DATA = "addCustomerData", ITEM_NAME = "addCustomer";

    @BeforeTest
    public void setUp() {
        buyItem = new BuyItem(driver);
    }

    @BeforeMethod
    public void beforeMethod() {
    }

    // Check Buy Item functionality, positive and negative test cases
    @Test(dataProvider=BUY_ITEM_DATA, priority=1)
    public void checkBuyItem(JSONObject data) throws InterruptedException {


    }

    @DataProvider(name=BUY_ITEM_DATA, parallel=false)
    public Object[][] buyItemData() {
        String sPathToDataFile = sPathToFile + sDataFileJson;
        return genericProvider(ITEM_NAME, sPathToDataFile);
    }

    @AfterMethod
    public void afterMethod() {
    }

    @AfterTest
    public void tearDown(){
        buyItemReport.flush();
    }

}
