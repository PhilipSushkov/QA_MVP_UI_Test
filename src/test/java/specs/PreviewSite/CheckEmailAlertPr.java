package specs.PreviewSite;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.EmailAdmin.Subscribers.MailingListUsers;
import pageobjects.LiveSite.EmailAlertsPage;
import pageobjects.LiveSite.HomePage;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by easong on 1/24/17.
 */
public class CheckEmailAlertPr extends AbstractSpec {

    //// THERE SHOULD BE ONE TEST HERE FOR EVERY TEST ON CheckPublicSite.java \\\\
    private static HomePage homePage;
    private static EmailAlertsPage emailAlertsPage;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static MailingListUsers mailingListUsers;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;
    private final String DATA = "getData";

    private final String user = "test@q4websystems.com";
    private final String password = "testing!";

    private final String subscribeSubject = "ChicagoTest Website - Validate Account";
    private final String unsubscribeSubject = "ChicagoTest Website - Unsubscribe";

    private static By emailAdminMenuButton, subscribersMenuItem;

    @BeforeTest
    public void setUp() throws Exception {
        sPathToFile = System.getProperty("user.dir") + propUIPublicSite.getProperty("dataPath_LiveSite");
        sDataFileJson = propUIPublicSite.getProperty("json_EmailAlertData");
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        subscribersMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_Subscribers"));

        parser = new JSONParser();

        homePage = new HomePage(driver);
        emailAlertsPage = new EmailAlertsPage(driver);
        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        mailingListUsers = new MailingListUsers(driver);

        //Delete user
        loginPage.loginUser();
        dashboard.openPageFromMenu(emailAdminMenuButton, subscribersMenuItem);
        mailingListUsers.deleteUser(user);

        dashboard.previewSite().goToInvestorsPage();

        deleteMail(user, password, subscribeSubject);
        deleteMail(user, password, unsubscribeSubject);
    }


    @Test(dataProvider = DATA, priority = 1)
    public void signUpEmailAlert(JSONObject data) throws InterruptedException, MessagingException, IOException {
        if (data.get("unsubscribe").toString() == "false") {
            String sMessage = data.get("expected").toString();
            String type = "subscribe";

            homePage.selectEmailAlertsFromMenu();

            //Checking the checkboxes - all should not be checked
            Assert.assertTrue(emailAlertsPage.getEODChkBox(type), "EOD checkbox should be checked");
            Assert.assertTrue(emailAlertsPage.getTestListChkBox(type), "Test List checkbox should be checked");
            Assert.assertTrue(emailAlertsPage.getPressReleaseChkBox(type), "Press Release checkbox should be checked");

            Assert.assertTrue(emailAlertsPage.eventAlertPageDisplayed(), "Email Alerts Page did not load properly");
            Assert.assertEquals(emailAlertsPage.subscribe(data, type), sMessage, "Email Alert sign up does not work properly ");

            //Checking for email
            if (data.get("fail").toString() == "false") {
                String url = emailAlertsPage.checkAndGetEmail(user, password, subscribeSubject);

                Assert.assertNotNull(url, "Subscription email was not sent");
                Assert.assertTrue(emailAlertsPage.checkMessage(url, type), "Subscription could not be validated");
                deleteMail(user, password, subscribeSubject);
            }
        }
    }

    @Test(dataProvider = DATA, priority = 2)
    public void unsubscribeEmailAlert (JSONObject data) throws Exception {
        if (data.get("unsubscribe").toString() == "true") {
            String sMessage = data.get("expected").toString();
            String type = "unsubscribe";

            homePage.selectEmailAlertsFromMenu();

            Assert.assertTrue(emailAlertsPage.eventAlertPageDisplayed(), "Email Alerts Page did not load properly");
            Assert.assertEquals(emailAlertsPage.unsubscribe(data, type), sMessage, "Email Alert sign up does not work properly ");

            //Checking for email
            if (data.get("fail").toString() == "false") {
                String url = emailAlertsPage.checkAndGetEmail(user, password, unsubscribeSubject);

                Assert.assertNotNull(url, "Unsubscription email was not sent");
                Assert.assertTrue(emailAlertsPage.checkMessage(url, type), "Unsubscription could not be validated");
                deleteMail(user, password, unsubscribeSubject);

                //Delete user
                driver.get("http://chicagotest.s3.q4web.com/admin");
                dashboard.openPageFromMenu(emailAdminMenuButton, subscribersMenuItem);
                mailingListUsers.deleteUser(user);

                ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
                driver.switchTo().window(tabs.get(1)).close();
                driver.switchTo().window(tabs.get(0));

                //Prep for next test
                dashboard.previewSite().goToInvestorsPage();
            }
        }
    }

    @Test(dataProvider = DATA, priority = 3)
    public void signUpEmailAlert_43 (JSONObject data) throws Exception {
        if (data.get("unsubscribe").toString() == "false") {
            String sMessage = data.get("expected").toString();
            String type = "subscribe_43";

            homePage.selectEmailAlertsFromMenu();

            //Checking the checkboxes - all should be unchecked
            Assert.assertTrue(emailAlertsPage.getEODChkBox(type), "EOD checkbox should be unchecked");
            Assert.assertTrue(emailAlertsPage.getTestListChkBox(type), "Test List checkbox should be unchecked");
            Assert.assertTrue(emailAlertsPage.getPressReleaseChkBox(type), "Press Release checkbox should be unchecked");

            Assert.assertTrue(emailAlertsPage.eventAlertPageDisplayed(), "Email Alerts Page did not load properly");
            Assert.assertEquals(emailAlertsPage.subscribe(data, type), sMessage, "Email Alert sign up does not work properly ");

            //Checking for email
            if (data.get("fail").toString() == "false") {
                String url = emailAlertsPage.checkAndGetEmail(user, password, subscribeSubject);

                Assert.assertNotNull(url, "Subscription email was not sent for 4.3");
                Assert.assertTrue(emailAlertsPage.checkMessage(url, type), "Subscription could not be validated");
                deleteMail(user, password, subscribeSubject);
            }
        }
    }


    @DataProvider
    public Object[][] getData () {

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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}
