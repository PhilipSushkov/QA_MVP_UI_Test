package specs.PublicSite;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.LiveSite.*;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by easong on 1/23/17.
 */
public class CheckEmailAlertPage extends AbstractSpec {

    //// WHEN ADDING A TEST TO THIS CLASS, ADD A ENTRY TO IT IN CheckSitePr.java \\\\

    private static HomePage homePage;
    private static EmailAlertsPage emailAlertsPage;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;
    private final String DATA="getData";

    @BeforeTest
    public void setUp() {
        sPathToFile = System.getProperty("user.dir") + propUIPublicSite.getProperty("dataPath_LiveSite");
        sDataFileJson = propUIPublicSite.getProperty("json_EmailAlertData");

        parser = new JSONParser();

        driver.get("http://chicagotest.q4web.com/English/Investors/");
        homePage = new HomePage(driver);
        emailAlertsPage = new EmailAlertsPage(driver);

    }


    @Test(dataProvider = DATA, priority = 1)
    public void signUpEmailAlert(JSONObject data) {
        if(data.get("unsubscribe").toString() =="false") {
            String sMessage = data.get("expected").toString();
            String type = "subscribe";

            homePage.selectEmailAlertsFromMenu();

            //Checking the checkboxes - all should not be checked
            Assert.assertTrue(emailAlertsPage.getEODChkBox(type), "EOD checkbox should be checked");
            Assert.assertTrue(emailAlertsPage.getTestListChkBox(type), "Test List checkbox should be checked");
            Assert.assertTrue(emailAlertsPage.getPressReleaseChkBox(type), "Press Release checkbox should be checked");

            Assert.assertTrue(emailAlertsPage.eventAlertPageDisplayed(), "Email Alerts Page did not load properly");
            Assert.assertEquals(emailAlertsPage.subscribe(data, type), sMessage, "Email Alert sign up does not work properly ");
        }
    }
    @Test(dataProvider = DATA, priority = 2)
    public void signUpEmailAlert_43(JSONObject data) {
        if(data.get("unsubscribe").toString() =="false") {
            String sMessage = data.get("expected").toString();
            String type = "subscribe_43";

            homePage.selectEmailAlertsFromMenu();

            //Checking the checkboxes - all should be unchecked
            Assert.assertTrue(emailAlertsPage.getEODChkBox(type), "EOD checkbox should be unchecked");
            Assert.assertTrue(emailAlertsPage.getTestListChkBox(type), "Test List checkbox should be unchecked");
            Assert.assertTrue(emailAlertsPage.getPressReleaseChkBox(type), "Press Release checkbox should be unchecked");

            Assert.assertTrue(emailAlertsPage.eventAlertPageDisplayed(), "Email Alerts Page did not load properly");
            Assert.assertEquals(emailAlertsPage.subscribe(data, type), sMessage, "Email Alert sign up does not work properly ");
        }
    }
    @Test(dataProvider = DATA, priority = 2)
    public void unsubscribeEmailAlert(JSONObject data){
        if(data.get("unsubscribe").toString() =="true") {
            String sMessage = data.get("expected").toString();
            String type = "unsubscribe";

            Assert.assertTrue(emailAlertsPage.eventAlertPageDisplayed(), "Email Alerts Page did not load properly");
            Assert.assertEquals(emailAlertsPage.unsubscribe(data, type), sMessage, "Email Alert sign up does not work properly ");
        }
    }

    //TODO Check Subscription Email
    //TODO Check Unsubscription email

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("email_alert");
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
