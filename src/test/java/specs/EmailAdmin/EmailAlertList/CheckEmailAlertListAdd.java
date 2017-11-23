package specs.EmailAdmin.EmailAlertList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.Dashboard.Dashboard;
import pageobjects.EmailAdmin.EmailAlertList.EmailAlertListAdd;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by charleszheng on 2017-10-20.
 */

public class CheckEmailAlertListAdd extends AbstractSpec{
    private static By emailAdminMenuButton, emailAlertListMenuButton, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static EmailAlertListAdd emailAlertListAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", EMAIL_ALERT_NAME="description";

    @BeforeTest
    public void setUp() throws Exception {
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        emailAlertListMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAlertList"));
        addNewLink = By.xpath(propUIEmailAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        emailAlertListAdd = new EmailAlertListAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUIEmailAdmin.getProperty("dataPath_EmailAlertList");
        sDataFileJson = propUIEmailAdmin.getProperty("json_EmailAlertData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception{
        dashboard.openPageFromMenu(emailAdminMenuButton, emailAlertListMenuButton);
    }

    @Test(priority = 1)
    public void checkTitle() throws Exception{
        final String expectedTitle = "Workflow Email Edit";
        Assert.assertEquals(emailAlertListAdd.getTitle(), expectedTitle, "Actual Workflow Email Edit page Title doesn't match to expected");
    }


    @Test(dataProvider = DATA, priority = 2)
    public void checkSaveEmailAlertList(JSONObject data) throws Exception{
        String sEmailAlertListName = data.get(EMAIL_ALERT_NAME).toString();
        Assert.assertNotNull(emailAlertListAdd.saveEmailAlertList(data, sEmailAlertListName), "New "+EMAIL_ALERT_NAME+" didn't save properly");
        Assert.assertTrue(emailAlertListAdd.checkEmailAlertList(data, sEmailAlertListName),"New "+EMAIL_ALERT_NAME+" Check fails (After Save)");
    }

    @Test(dataProvider = DATA, priority = 3)
    public void checkEditEmailAlertList(JSONObject data) throws Exception {
        String sEmailAlertListName = data.get(EMAIL_ALERT_NAME).toString();
        Assert.assertNotNull(emailAlertListAdd.editEmailAlertList(data, sEmailAlertListName),"New "+EMAIL_ALERT_NAME+" Edit fails" );
        Assert.assertTrue(emailAlertListAdd.checkEmailAlertListCh(data,sEmailAlertListName),"New "+EMAIL_ALERT_NAME+" Check fails (After Edit) ");
    }

    @Test(dataProvider = DATA, priority = 4)
    public void checkDeleteEmailAlertList(JSONObject data) throws Exception {
        String sEmailAlertListName = data.get(EMAIL_ALERT_NAME).toString();
        Assert.assertNotNull(emailAlertListAdd.deleteEmailAlertList(data, sEmailAlertListName),"New "+EMAIL_ALERT_NAME+" Delete fails" );
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
