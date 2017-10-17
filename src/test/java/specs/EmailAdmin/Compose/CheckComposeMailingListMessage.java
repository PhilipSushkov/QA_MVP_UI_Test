package specs.EmailAdmin.Compose;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.EmailAdmin.Compose.ComposeMailingListMessage;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;
import util.Functions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by charleszheng on 2017-10-16.
 */

public class CheckComposeMailingListMessage extends AbstractSpec {
    private static By emailAdminMenuButton, composeMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static ComposeMailingListMessage composeMailingListMessage;
    private static Functions functions;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", MAIL_NAME="subject";

    @BeforeTest
    public void setUp() throws Exception {
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        composeMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_Compose"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        composeMailingListMessage = new ComposeMailingListMessage(driver);

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_ComposeList");
        sDataFileJson = propUIContentAdmin.getProperty("json_MailingData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @Test(priority = 1)
    public void checkTitle() throws Exception{
        dashboard.openPageFromMenu(emailAdminMenuButton, composeMenuItem);
        final String expectedTitle = "Mailing List Messages Admin";
        Assert.assertEquals(composeMailingListMessage.getTitle(), expectedTitle, "Actual Mailing List Messages Admin page Title doesn't match to expected");
    }


    @Test(dataProvider = DATA, priority = 2)
    public void checkDeleteCompose(JSONObject data) throws Exception{
        String sMailingName = data.get(MAIL_NAME).toString();

        dashboard.openPageFromMenu(emailAdminMenuButton, composeMenuItem);
        Assert.assertNotNull(composeMailingListMessage.saveMail(data, sMailingName),"New "+MAIL_NAME+" didn't save properly");
        if (data.get("subject_ch") != null){
            Assert.assertNotNull(composeMailingListMessage.deleteMail(data, data.get("subject_ch").toString()), "New "+MAIL_NAME+" is not deleted from list properly");
        }
        else {
            Assert.assertNotNull(composeMailingListMessage.deleteMail(data, sMailingName), "New "+MAIL_NAME+" is not deleted from list properly");

        }
    }

    @Test(dataProvider = DATA , priority = 3)
    public void checkSaveCompose(JSONObject data) throws Exception{
        String sMailingName = data.get(MAIL_NAME).toString();

        dashboard.openPageFromMenu(emailAdminMenuButton, composeMenuItem);
        Assert.assertNotNull(composeMailingListMessage.saveMail(data, sMailingName),"New "+MAIL_NAME+" didn't save properly");
        Assert.assertEquals(composeMailingListMessage.getSentOnInfo(),"Not Sent","Sent On status should be Not Sent (After Save)");
    }

    @Test(dataProvider = DATA, priority = 4)
    public void checkSendTestCompose(JSONObject data) throws Exception{
        String sMailingName = data.get(MAIL_NAME).toString();

        Assert.assertNotNull(composeMailingListMessage.sendTestMail(data, sMailingName),"New "+MAIL_NAME+" didn't test send properly" );
        Assert.assertEquals(composeMailingListMessage.getSentOnInfo(),"Not Sent","Sent On status should be Not Sent (After Sent Test Email)");
        Assert.assertNotNull(functions.getSpecificMail("test@q4websystems.com", "testing!" , sMailingName),"New "+MAIL_NAME+" is not received" );
        functions.deleteMail("test@q4websystems.com", "testing!" ,sMailingName);
        Assert.assertNull(functions.getSpecificMail("test@q4websystems.com", "testing!" ,sMailingName),"New "+MAIL_NAME+" is not deleted properly" );
    }

    @Test(dataProvider = DATA, priority = 5)
    public void checkUpdateAndSendCompose(JSONObject data) throws Exception{
        String sMailingName = data.get(MAIL_NAME).toString();
        dashboard.openPageFromMenu(emailAdminMenuButton, composeMenuItem);
        Assert.assertNotNull(composeMailingListMessage.updateAndSendMail(data, sMailingName),"New "+MAIL_NAME+" didn't update and send properly");
        Assert.assertTrue(composeMailingListMessage.isValidFormat("yyyy/MM/dd HH:mm:ss",composeMailingListMessage.getSentOnInfo()),"Sent On status is wrong");
        System.out.println("Email Sent On: " + composeMailingListMessage.getSentOnInfo());
        if (data.get("subject_ch") != null){
            Assert.assertNotNull(functions.getSpecificMail("test@q4websystems.com", "testing!", data.get("subject_ch").toString()), "New " + MAIL_NAME + " is not received");
            functions.deleteMail("test@q4websystems.com", "testing!", data.get("subject_ch").toString());
            Assert.assertNull(functions.getSpecificMail("test@q4websystems.com", "testing!", data.get("subject_ch").toString()), "New " + MAIL_NAME + " is not deleted from mailbox properly");
        }
        else {
            Assert.assertNotNull(functions.getSpecificMail("test@q4websystems.com", "testing!", sMailingName), "New " + MAIL_NAME + " is not received");
            functions.deleteMail("test@q4websystems.com", "testing!", sMailingName);
            Assert.assertNull(functions.getSpecificMail("test@q4websystems.com", "testing!", sMailingName), "New " + MAIL_NAME + " is not deleted from mailbox properly");
        }
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("mailing");
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

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
