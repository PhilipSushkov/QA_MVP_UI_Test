package specs.SiteAdmin.LookupList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.PageAdmin.WorkflowState;
import pageobjects.SiteAdmin.LookupList.LookupAdd;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-05-05.
 */

public class CheckLookupAdd extends AbstractSpec {
    private static By siteAdminMenuButton, lookupListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static LookupAdd lookupAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", KEY_NAME="key_name", PAGE_NAME="Link To Page";

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        lookupListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_LookupList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        lookupAdd = new LookupAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_LookupList");
        sDataFileJson = propUISiteAdmin.getProperty("json_LookupData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(siteAdminMenuButton, lookupListMenuItem);
    }

    @Test(dataProvider=DATA, priority=1)
    public void saveLookup(JSONObject data) {
        String sLinkToPageName = data.get(KEY_NAME).toString();
        String expectedTitleEdit = "Link To Page Edit";

        Assert.assertEquals(lookupAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(lookupAdd.saveLookup(data, sLinkToPageName), WorkflowState.IN_PROGRESS.state(), "New "+PAGE_NAME+" didn't save properly");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("link_to_page");
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
    }

}
