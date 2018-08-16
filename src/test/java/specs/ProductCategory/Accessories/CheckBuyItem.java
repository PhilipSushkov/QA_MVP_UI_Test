package specs.ProductCategory.Accessories;

import com.aventstack.extentreports.*;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pageobjects.ProductCategory.Accessories.*;
import specs.AbstractSpec;
import util.Log;

import java.lang.reflect.Method;

/**
 * Created by PSUSHKOV on Aug, 2018
 **/

public class CheckBuyItem extends AbstractSpec {
    private static BuyItem buyItem;
    private static Checkout checkout;
    private static TransactionResults transactionResults;
    private static ExtentReports buyItemRep;
    private static String sPathToFile, sDataFileJson;

    private final String BUY_ITEM_DATA = "buyItemData", ITEM_NAME = "buyItem";

    @BeforeTest
    public void setUp() {
        buyItem = new BuyItem(driver);
        checkout = new Checkout(driver);
        transactionResults = new TransactionResults(driver);

        sPathToFile = System.getProperty("user.dir") + propUIProdCategory.getProperty("dataPath_BuyItem");
        sDataFileJson = propUIProdCategory.getProperty("json_BuyItemData");
        buyItemRep = RepBuyItem.GetExtent();
    }

    @BeforeMethod
    public void beforeMethod() throws InterruptedException {
        // #2 Open Accessories Section on the website
        buyItem.OpenAccessoriesSection();
    }

    // Check Buy Item functionality, positive and negative test cases
    @Test(dataProvider=BUY_ITEM_DATA, priority=1)
    public void checkBuyItem(JSONObject data, Method method) {
        Log.info(method.getName() + " test is starting.");

        // Create the report
        ExtentTest test = buyItemRep.createTest("Item Name: " + data.get("name").toString() +"<br>"
                + data.get("description").toString());

        // #3 Click on Add to Cart button
        buyItem.addToCart(data);

        // #4 Could be used SoftAssert option as well
        // #4 Check if we are on Checkout page
        String expCheckoutPageTitle = data.get("expCheckoutTitle").toString();
        String actCheckoutPageTitle = checkout.getTitle();
        if (actCheckoutPageTitle.contains(expCheckoutPageTitle)) {
            test.log(Status.PASS, "Actual Checkout Page Title contains expected one: <b>" + actCheckoutPageTitle + "</b>");
        } else {
            test.log(Status.FAIL, "Actual Checkout Page Title doesn't contains expected one: <b>" + actCheckoutPageTitle + "</b>. " +
                    "Supposed to be: <b>" + expCheckoutPageTitle + "</b>");
        }
        Assert.assertTrue(actCheckoutPageTitle.contains(expCheckoutPageTitle), "Actual Checkout Page Title doesn't contain expected one");

        // #4 Check if we have Magic Mouse item for Checkout
        String expItemName = data.get("item").toString();
        String actItemName = checkout.getItemName(data);
        if (actItemName.equals(expItemName)) {
            test.log(Status.PASS, "Actual Item Name equals to expected: <b>" + actItemName + "</b>");
        } else {
            test.log(Status.FAIL, "Actual Item Name doesn't equal to expected: <b>" + actItemName + "</b>. " +
                    "Supposed to be: <b>" + expItemName + "</b>");
        }
        Assert.assertEquals(actItemName, expItemName, "Actual Item Name doesn't equal to expected");

        // #4 Check if a quantity of Magic Mouse item equals to 1 for Checkout
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

        // #5 Click on Continue button
        checkout.clickContinue();

        // Split test cases for negative and positive
        switch (data.get("test_type").toString()) {
            case "positive":
                // #6 Enter test data needed for email, billing/contact details
                checkout.fillUpData((JSONObject) data.get("contacts"));

                // #6 Click on Purchase button
                checkout.clickPurchase();

                // #4 Check if we are on Transaction Results page
                String expTransactionResultsTitle = data.get("expTransactionResultsTitle").toString();
                String actTransactionResultsTitle = transactionResults.getTitle();
                if (actTransactionResultsTitle.contains(expTransactionResultsTitle)) {
                    test.log(Status.PASS, "Actual Transaction Results Page Title contains expected one: <b>" + actTransactionResultsTitle + "</b>");
                } else {
                    test.log(Status.FAIL, "Actual Transaction Results Page Title doesn't contains expected one: <b>" + actTransactionResultsTitle + "</b>. " +
                            "Supposed to be: <b>" + expTransactionResultsTitle + "</b>");
                }
                Assert.assertTrue(actTransactionResultsTitle.contains(expTransactionResultsTitle),
                        "Actual Transaction Results Page Title doesn't contain expected one");

                transactionResults.checkOrder(data);
                System.out.println("Done");

                break;
            case "negative":
                // #6 Click on Purchase button
                checkout.clickPurchase();

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
