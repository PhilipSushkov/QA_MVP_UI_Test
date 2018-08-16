package specs.ProductCategory.Accessories;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.json.simple.JSONObject;
import org.testng.Assert;
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
    public void checkBuyItem(JSONObject data, Method method) {
        Log.info(method.getName() + " test is starting.");

        // Create the report
        ExtentTest test = buyItemRep.createTest("Item Name: " + data.get("name").toString() +"<br>"
                + data.get("description").toString());

        buyItem.addToCart(data);

        // Could be used SoftAssert option as well
        // Check if we are on Checkout page
        String expCheckoutPageTitle = data.get("expCheckoutTitle").toString();
        String actCheckoutPageTitle = checkout.getTitle();
        if (actCheckoutPageTitle.contains(expCheckoutPageTitle)) {
            test.log(Status.PASS, "Actual Checkout Page Title contains expected one: <b>" + actCheckoutPageTitle + "</b>");
        } else {
            test.log(Status.FAIL, "Actual Checkout Page Title doesn't contains expected one: <b>" + actCheckoutPageTitle + "</b>. " +
                    "Supposed to be: <b>" + expCheckoutPageTitle + "</b>");
        }
        Assert.assertTrue(actCheckoutPageTitle.contains(expCheckoutPageTitle), "Actual Checkout Page Title doesn't contain expected one");

        // Check if we have Magic Mouse item for Checkout
        String expItemName = data.get("item").toString();
        String actItemName = checkout.getItemName(data);
        if (actItemName.equals(expItemName)) {
            test.log(Status.PASS, "Actual Item Name equals to expected: <b>" + actItemName + "</b>");
        } else {
            test.log(Status.FAIL, "Actual Item Name doesn't equal to expected: <b>" + actItemName + "</b>. " +
                    "Supposed to be: <b>" + expItemName + "</b>");
        }
        Assert.assertEquals(actItemName, expItemName, "Actual Item Name doesn't equal to expected");

        // Check if a quantity of Magic Mouse item equals to 1 for Checkout
        long expItemQuantity = Long.parseLong(data.get("expItemQuantity").toString());
        long actItemQuantity = checkout.getItemQuantity(data);
        if (actItemQuantity == expItemQuantity) {
            test.log(Status.PASS, "Actual Item Quantity equals to expected: <b>" + actItemQuantity + "</b>");
        } else {
            test.log(Status.FAIL, "Actual Item Quantity: <b>" + actItemQuantity + "</b> doesn't equal to expected: <b>"
                    + expItemQuantity + "</b>");
        }
        Assert.assertTrue(actItemQuantity == expItemQuantity, "Actual Item Quantity: " + actItemQuantity
                + " doesn't equal to expected: " + expItemQuantity);

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
