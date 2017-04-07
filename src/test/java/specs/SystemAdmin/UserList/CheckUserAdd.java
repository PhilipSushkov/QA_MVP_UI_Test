package specs.SystemAdmin.UserList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SystemAdmin.UserList.UserAdd;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-04-03.
 */

public class CheckUserAdd extends AbstractSpec {
    private static By systemAdminMenuButton, userListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static UserAdd userAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", USER_NAME="user_name", PAGE_NAME="User";

    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        userListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_UserList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        userAdd = new UserAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUISystemAdmin.getProperty("dataPath_UserList");
        sDataFileJson = propUISystemAdmin.getProperty("json_UserData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(systemAdminMenuButton, userListMenuItem);
    }

    @Test(dataProvider=DATA, priority=1)
    public void saveUser(JSONObject data) {
        String sUserName = data.get(USER_NAME).toString();
        String expectedTitleList = "User List";
        String expectedTitleEdit = "User Edit";

        Assert.assertEquals(userAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(userAdd.saveUser(data, sUserName), expectedTitleList, "New "+PAGE_NAME+" didn't save properly");
    }

    @Test(dataProvider=DATA, priority=2)
    public void checkUser(JSONObject data) {
        String sUserName = data.get(USER_NAME).toString();
        Assert.assertTrue(userAdd.checkUser(data, sUserName), "New "+PAGE_NAME+" doesn't fit to entry data (after Save)");
    }

    @Test(dataProvider=DATA, priority=3)
    public void editWorkflowEmail(JSONObject data) throws Exception {
        String sUserName = data.get(USER_NAME).toString();
        Assert.assertTrue(userAdd.editUser(data, sUserName), PAGE_NAME+" didn't change properly (after Save)");
    }

    @Test(dataProvider=DATA, priority=4)
    public void checkUserCh(JSONObject data) {
        String sUserName = data.get(USER_NAME).toString();
        Assert.assertTrue(userAdd.checkUserCh(data, sUserName), "New "+PAGE_NAME+" doesn't fit to change data (after Edit)");
    }

    @Test(dataProvider=DATA, priority=5)
    public void removeUser(JSONObject data) {
        String sUserName = data.get(USER_NAME).toString();
        Assert.assertTrue(userAdd.removeUser(sUserName), "New "+PAGE_NAME+" shouldn't be shown in "+PAGE_NAME+" List (after Delete)");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("user");
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
