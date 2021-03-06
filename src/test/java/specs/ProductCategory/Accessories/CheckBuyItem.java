package specs.ProductCategory.Accessories;

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
 * Created by PSUSHKOV on Aug, 2018
 **/

public class CheckBuyItem extends AbstractSpec {
    private static BuyItem buyItem;
    private static Checkout checkout;
    private static TransactionResults transactionResults;
    private static ExtentReports buyItemRep;
    private static String sPathToFile, sDataFileJson;
    private static final long DEFAULT_PAUSE = 3000;
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
    public void checkBuyItem(JSONObject data, Method method) throws InterruptedException {
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

                // #7 Check if we are on Transaction Results page
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

                HashMap results = transactionResults.checkOrder(data);

                // #7 Check if we have Magic Mouse item for Order
                String expOrderItemName = data.get("item").toString();
                String actOrderItemName = results.get("itemName").toString();
                if (actOrderItemName.equals(expOrderItemName)) {
                    test.log(Status.PASS, "Actual Order Item Name equals to expected: <b>" + actOrderItemName + "</b>");
                } else {
                    test.log(Status.FAIL, "Actual OrderItem Name doesn't equal to expected: <b>" + actOrderItemName + "</b>. " +
                            "Supposed to be: <b>" + expOrderItemName + "</b>");
                }
                Assert.assertEquals(actOrderItemName, expOrderItemName, "Actual Order Item Name doesn't equal to expected");

                // #7 Check if a quantity of Magic Mouse item equals to 1 for Order
                long expOrderItemQuantity = Long.parseLong(data.get("expItemQuantity").toString());
                long actOrderItemQuantity = Long.parseLong(results.get("itemQuantity").toString());
                if (actOrderItemQuantity == expOrderItemQuantity) {
                    test.log(Status.PASS, "Actual Order Item Quantity equals to expected: <b>" + actOrderItemQuantity + "</b>");
                } else {
                    test.log(Status.FAIL, "Actual Order Item Quantity: <b>" + actOrderItemQuantity + "</b> doesn't equal to expected: <b>"
                            + expOrderItemQuantity + "</b>");
                }
                Assert.assertTrue(actOrderItemQuantity == expOrderItemQuantity, "Actual Order Item Quantity: " + actOrderItemQuantity
                        + " doesn't equal to expected: " + expOrderItemQuantity);

                Log.info(method.getName() + " positive test has been finished");
                break;

            case "negative":
                // #6 Enter test data needed for email, billing/contact details
                checkout.fillUpData((JSONObject) data.get("contacts"));

                // #6 Click on Purchase button
                checkout.clickPurchase();
                Thread.sleep(DEFAULT_PAUSE);

                // #7 Check if we didn't fill up all required data we should stay on Checkout page
                String expTitle = data.get("expCheckoutTitle").toString();
                String actTitle = driver.getTitle();
                if (actTitle.contains(expTitle)) {
                    test.log(Status.PASS, "Actual Page Title contains expected one: <b>" + actTitle + "</b>");
                } else {
                    test.log(Status.FAIL, "Actual Page Title doesn't contains expected one: <b>" + actTitle + "</b>. " +
                            "Supposed to be: <b>" + expTitle + "</b>");
                }
                Assert.assertTrue(actTitle.contains(expTitle),
                        "Actual Page Title: " + actTitle + " doesn't contain expected one: " + expTitle);

                Log.info(method.getName() + " negative test has been finished");
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
