package specs.ContentAdmin.Region;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.ContentAdmin.Region.RegionAdd;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.PageAdmin.WorkflowState;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by victorlam on 2017-09-25.
 */

public class CheckRegionAdd extends AbstractSpec {
    private static By contentAdminMenuButton, regionListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static RegionAdd regionAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", REGION_NAME="region_name", PAGE_NAME="Region";

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        regionListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_RegionList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        regionAdd = new RegionAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_RegionList");
        sDataFileJson = propUIContentAdmin.getProperty("json_RegionData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(contentAdminMenuButton, regionListMenuItem);
    }

    @Test(dataProvider=DATA, priority=1)
    public void saveRegion(JSONObject data) {
        String sRegionName = data.get(REGION_NAME).toString();
        String expectedTitleEdit = "Region Edit";

        Assert.assertEquals(regionAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(regionAdd.saveRegion(data, sRegionName), WorkflowState.IN_PROGRESS.state(), "New "+PAGE_NAME+" didn't save properly");
    }

    @Test(dataProvider=DATA, priority=2)
    public void saveAndSubmitRegion(JSONObject data) throws InterruptedException {
        String sRegionName = data.get(REGION_NAME).toString();

        Assert.assertEquals(regionAdd.saveAndSubmitRegion(data, sRegionName), WorkflowState.FOR_APPROVAL.state(), "New " + PAGE_NAME + " doesn't submit properly (after Save And Submit)");
        Assert.assertTrue(regionAdd.checkRegion(data, sRegionName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Save and Submit)");
    }

    @Test(dataProvider=DATA, priority=3)
    public void publishRegion(JSONObject data) throws InterruptedException {
        String sRegionName = data.get(REGION_NAME).toString();
        Assert.assertEquals(regionAdd.publishRegion(data, sRegionName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=4)
    public void revertRegion(JSONObject data) throws InterruptedException {
        String sRegionName = data.get(REGION_NAME).toString();

        Assert.assertEquals(regionAdd.changeAndSubmitRegion(data, sRegionName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertEquals(regionAdd.revertToLiveRegion(sRegionName), WorkflowState.LIVE.state(), "Couldn't revert to Live changes for New "+ PAGE_NAME);
        Assert.assertTrue(regionAdd.checkRegion(data, sRegionName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Revert To Live)");
    }

    @Test(dataProvider=DATA, priority=5)
    public void changeAndSubmitRegion(JSONObject data) throws Exception {
        String sRegionName = data.get(REGION_NAME).toString();

        Assert.assertEquals(regionAdd.changeAndSubmitRegion(data, sRegionName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertTrue(regionAdd.checkRegionCh(data, sRegionName), "Submitted New "+ PAGE_NAME +" changes don't fit well to change data (after Change And Submit)");
    }

    @Test(dataProvider=DATA, priority=6)
    public void publishEditRegion(JSONObject data) throws InterruptedException {
        String sRegionName = data.get(REGION_NAME).toString();
        Assert.assertEquals(regionAdd.publishRegion(data, sRegionName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=7)
    public void deleteRegion(JSONObject data) throws Exception {
        String sRegionName = data.get(REGION_NAME).toString();
        Assert.assertEquals(regionAdd.setupAsDeletedRegion(sRegionName), WorkflowState.DELETE_PENDING.state(), "New "+ PAGE_NAME +" didn't setup as Deleted properly");
    }

    @Test(dataProvider=DATA, priority=8)
    public void removeRegion(JSONObject data) throws Exception {
        String sRegionName = data.get(REGION_NAME).toString();
        Assert.assertEquals(regionAdd.removeRegion(data, sRegionName), WorkflowState.NEW_ITEM.state(), "Couldn't remove New "+ PAGE_NAME +". Something went wrong.");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("region");
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
