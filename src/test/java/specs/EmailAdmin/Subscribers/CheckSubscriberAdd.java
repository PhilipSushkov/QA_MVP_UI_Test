package specs.EmailAdmin.Subscribers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.Dashboard.Dashboard;
import pageobjects.EmailAdmin.Subscribers.SubscriberAdd;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by charleszheng on 2017-10-17.
 */

public class CheckSubscriberAdd extends AbstractSpec{

    private static By emailAdminMenuButton, subscriberMenuItem, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static SubscriberAdd subscriberAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", SUBSCRIBER_NAME="email_address";

    @BeforeTest
    public void setUp() throws Exception {
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        subscriberMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_Subscribers"));
        addNewLink = By.xpath(propUIEmailAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        subscriberAdd = new SubscriberAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUIEmailAdmin.getProperty("dataPath_Subscriber");
        sDataFileJson = propUIEmailAdmin.getProperty("json_SubscriberData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception{
        dashboard.openPageFromMenu(emailAdminMenuButton, subscriberMenuItem);
    }

    @Test(priority = 1)
    public void checkTitle() throws Exception{
        final String expectedTitle = "Subscriber Edit";
        Assert.assertEquals(subscriberAdd.getTitle(), expectedTitle, "Actual Subscriber Edit page Title doesn't match to expected");
    }


    @Test(dataProvider = DATA, priority = 2)
    public void checkSaveSubscriber(JSONObject data) throws Exception{
        String sSubscriberName = data.get(SUBSCRIBER_NAME).toString();
        Assert.assertNotNull(subscriberAdd.saveSubscriber(data, sSubscriberName), "New "+SUBSCRIBER_NAME+" didn't save properly");
        Assert.assertTrue(subscriberAdd.checkSubscriber(data, sSubscriberName),"New "+SUBSCRIBER_NAME+" Check fails (After Save)");
    }

    @Test(dataProvider = DATA, priority = 3)
    public void checkEditSubscriber(JSONObject data) throws Exception {
        String sSubscriberName = data.get(SUBSCRIBER_NAME).toString();
        Assert.assertNotNull(subscriberAdd.editSubscriber(data, sSubscriberName),"New "+SUBSCRIBER_NAME+" Edit fails" );
        Assert.assertTrue(subscriberAdd.checkSubscriberCh(data,sSubscriberName),"New "+SUBSCRIBER_NAME+" Check fails (After Edit) " +
                "NOTE: KNOWN BUG REGARDING COUNTRY FAIL : https://q4websystems.atlassian.net/browse/WEB-13163");
    }

    @Test(dataProvider = DATA, priority = 4)
    public void checkDeleteSubscriber(JSONObject data) throws Exception {
        String sSubscriberName = data.get(SUBSCRIBER_NAME).toString();
        Assert.assertNotNull(subscriberAdd.deleteSubscriber(data, sSubscriberName),"New "+SUBSCRIBER_NAME+" Delete fails" );
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("subscriber");
            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject pageObj = (JSONObject) jsonArray.get(i);
                if (Boolean.parseBoolean(pageObj.get("do_assertions").toString())) {
                    zoom.add(jsonArray.get(i));
                }
            }

            Object[][] data = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                data[i][0] = zoom.get(i);
            }

            return data;

        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}
