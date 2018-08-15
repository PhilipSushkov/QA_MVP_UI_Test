package specs.ProductCategory.Accessories;

import com.aventstack.extentreports.ExtentReports;
import org.json.simple.JSONObject;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pageobjects.ProductCategory.Accessories.BuyItem;
import specs.AbstractSpec;

public class CheckBuyItem extends AbstractSpec {
    private static BuyItem buyItem;
    private static ExtentReports buyItemRep;
    private static String sPathToFile, sDataFileJson;

    private final String BUY_ITEM_DATA = "buyItemData", ITEM_NAME = "buyItem";

    @BeforeTest
    public void setUp() {
        buyItem = new BuyItem(driver);

        sPathToFile = System.getProperty("user.dir") + propUIProdCategory.getProperty("dataPath_BuyItem");
        sDataFileJson = propUIProdCategory.getProperty("json_BuyItemData");
        buyItemRep = RepBuyItem.GetExtent();
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
        buyItemRep.flush();
    }

}
