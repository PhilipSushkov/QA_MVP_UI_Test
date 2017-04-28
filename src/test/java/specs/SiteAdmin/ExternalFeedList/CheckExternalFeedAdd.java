package specs.SiteAdmin.ExternalFeedList;

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
import pageobjects.SiteAdmin.ExternalFeedList.ExternalFeedAdd;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-04-27.
 */

public class CheckExternalFeedAdd extends AbstractSpec {
    private static By siteAdminMenuButton, externalFeedListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static ExternalFeedAdd externalFeedAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", FEED_NAME="feed", PAGE_NAME="External Feed";

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        externalFeedListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_ExternalFeedList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        externalFeedAdd = new ExternalFeedAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_ExternalFeedList");
        sDataFileJson = propUISiteAdmin.getProperty("json_ExternalFeedData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(siteAdminMenuButton, externalFeedListMenuItem);
    }

    @Test(dataProvider=DATA, priority=1)
    public void saveExternalFeed(JSONObject data) {
        String sExternalFeedName = data.get(FEED_NAME).toString();
        String expectedTitleList = "External Feed List";
        String expectedTitleEdit = "External Feed Edit";

        Assert.assertEquals(externalFeedAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(externalFeedAdd.saveExternalFeed(data, sExternalFeedName), expectedTitleList, "New "+PAGE_NAME+" didn't save properly");
    }

    @Test(dataProvider=DATA, priority=2)
    public void checkExternalFeed(JSONObject data) {
        String sExternalFeedName = data.get(FEED_NAME).toString();
        Assert.assertTrue(externalFeedAdd.checkExternalFeed(data, sExternalFeedName), "New "+PAGE_NAME+" doesn't fit to entry data (after Save)");
    }

    @Test(dataProvider=DATA, priority=5)
    public void removeExternalFeed(JSONObject data) {
        String sExternalFeedName = data.get(FEED_NAME).toString();
        Assert.assertTrue(externalFeedAdd.removeExternalFeed(sExternalFeedName), "New "+PAGE_NAME+" shouldn't be shown in "+PAGE_NAME+" List (after Delete)");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("external_feed");
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
