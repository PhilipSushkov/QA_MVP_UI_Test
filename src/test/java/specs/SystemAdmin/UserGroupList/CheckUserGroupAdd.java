package specs.SystemAdmin.UserGroupList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SystemAdmin.UserGroupList.UserGroupAdd;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-04-10.
 */

public class CheckUserGroupAdd extends AbstractSpec {
    private static By systemAdminMenuButton, userGroupListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static UserGroupAdd userGroupAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", USER_GROUP_NAME="user_group_name", PAGE_NAME="User Group";

    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        userGroupListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_UserGroupList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        userGroupAdd = new UserGroupAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUISystemAdmin.getProperty("dataPath_UserGroupList");
        sDataFileJson = propUISystemAdmin.getProperty("json_UserGroupData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(systemAdminMenuButton, userGroupListMenuItem);
    }

    @Test(dataProvider=DATA, priority=1)
    public void saveUserGroup(JSONObject data) {
        String sUserGroupName = data.get(USER_GROUP_NAME).toString();
        String expectedTitleList = "User Group List";
        String expectedTitleEdit = "User Group Edit";

        Assert.assertEquals(userGroupAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(userGroupAdd.saveUserGroup(data, sUserGroupName), expectedTitleList, "New "+PAGE_NAME+" didn't save properly");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("user_group");
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
