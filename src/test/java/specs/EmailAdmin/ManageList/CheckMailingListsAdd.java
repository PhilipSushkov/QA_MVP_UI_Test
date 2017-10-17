package specs.EmailAdmin.ManageList;

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
import pageobjects.EmailAdmin.ManageList.MailingListsAdd;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by charleszheng on 2017-10-17.
 */

public class CheckMailingListsAdd extends AbstractSpec{

    private static By emailAdminMenuButton, manageListMenuItem, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static MailingListsAdd mailingListsAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", MAIL_NAME="mailing_list_name";

    @BeforeTest
    public void setUp() throws Exception {
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        manageListMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_ManageList"));
        addNewLink = By.xpath(propUIEmailAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        mailingListsAdd = new MailingListsAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUIEmailAdmin.getProperty("dataPath_ManageList");
        sDataFileJson = propUIEmailAdmin.getProperty("json_ManageListData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @Test(dataProvider = DATA, priority = 1)
    public void checkSaveMailingLists(JSONObject data) throws Exception{
        String sMailingListName = data.get(MAIL_NAME).toString();
        dashboard.openPageFromMenu(emailAdminMenuButton, manageListMenuItem);
        Assert.assertNotNull(mailingListsAdd.saveMailingLists(data, sMailingListName), "New "+MAIL_NAME+" didn't save properly");
        dashboard.openPageFromMenu(emailAdminMenuButton, manageListMenuItem);
        Assert.assertTrue(mailingListsAdd.checkMailingLists(data, sMailingListName),"New "+MAIL_NAME+" Check fails (After Save)");
    }

    @Test(dataProvider = DATA, priority = 2)
    public void checkEditMailingLists(JSONObject data) throws Exception {
        String sMailingListName = data.get(MAIL_NAME).toString();
        dashboard.openPageFromMenu(emailAdminMenuButton, manageListMenuItem);
        Assert.assertNotNull(mailingListsAdd.editMailingLists(data, sMailingListName),"New "+MAIL_NAME+" Edit fails" );
        dashboard.openPageFromMenu(emailAdminMenuButton, manageListMenuItem);
        Assert.assertTrue(mailingListsAdd.checkMailingListsCh(data,sMailingListName),"New "+MAIL_NAME+" Check fails (After Edit)");
    }

    @Test(dataProvider = DATA, priority = 3)
    public void checkDeleteMailingLists(JSONObject data) throws Exception {
        String sMailingListName = data.get(MAIL_NAME).toString();
        dashboard.openPageFromMenu(emailAdminMenuButton, manageListMenuItem);
        Assert.assertNotNull(mailingListsAdd.deleteMailingLists(data, sMailingListName),"New "+MAIL_NAME+" Delete fails" );
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
            JSONArray jsonArray = (JSONArray) jsonObject.get("mailing_lists");
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
