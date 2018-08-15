package specs.ProductCategory.Accessories;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.json.simple.JSONObject;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pageobjects.ProductCategory.Accessories.BuyItem;
import pageobjects.ProductCategory.Accessories.Checkout;
import specs.AbstractSpec;
import util.Log;

import java.lang.reflect.Method;

/**
 * Created by PSUSHKOV on Aug, 2018
 **/

public class CheckBuyItem extends AbstractSpec {
    private static BuyItem buyItem;
    private static Checkout checkout;
    private static ExtentReports buyItemRep;
    private static String sPathToFile, sDataFileJson;

    private final String BUY_ITEM_DATA = "buyItemData", ITEM_NAME = "buyItem";

    @BeforeTest
    public void setUp() {
        buyItem = new BuyItem(driver);
        checkout = new Checkout(driver);

        sPathToFile = System.getProperty("user.dir") + propUIProdCategory.getProperty("dataPath_BuyItem");
        sDataFileJson = propUIProdCategory.getProperty("json_BuyItemData");
        buyItemRep = RepBuyItem.GetExtent();
    }

    @BeforeMethod
    public void beforeMethod() throws InterruptedException {
        buyItem.OpenAccessoriesSection();
    }

    // Check Buy Item functionality, positive and negative test cases
    @Test(dataProvider=BUY_ITEM_DATA, priority=1)
    public void checkBuyItem(JSONObject data, Method method) throws InterruptedException {
        Log.info(method.getName() + " test is starting.");

        // Create the report
        ExtentTest test = buyItemRep.createTest("Item Name: " + data.get("name").toString() +"<br>" + data.get("description").toString());

        System.out.println(driver.getTitle());

        buyItem.addToCart(data);

        // Split test cases for negative and positive
        switch (data.get("test_type").toString()) {
            case "positive":


                break;
            case "negative":

                break;
        }


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
