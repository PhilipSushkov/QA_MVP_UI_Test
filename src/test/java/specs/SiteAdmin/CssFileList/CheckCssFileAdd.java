package specs.SiteAdmin.CssFileList;

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
import pageobjects.SiteAdmin.CssFileList.CssFileAdd;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by philipsushkov on 2017-04-26.
 */

public class CheckCssFileAdd extends AbstractSpec {
    private static By siteAdminMenuButton, cssFileListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static CssFileAdd cssFileAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", CSS_FILE_NAME="css_name", PAGE_NAME="Css File";

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        cssFileListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_CssFileList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        cssFileAdd = new CssFileAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_CssFileList");
        sDataFileJson = propUISiteAdmin.getProperty("json_CssFileData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(siteAdminMenuButton, cssFileListMenuItem);
    }

    @Test(dataProvider=DATA, priority=1)
    public void saveCssFile(JSONObject data) {
        String sCssFileName = data.get(CSS_FILE_NAME).toString();
        String expectedTitleEdit = "Css File Edit";

        Assert.assertEquals(cssFileAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(cssFileAdd.saveCssFile(data, sCssFileName), WorkflowState.IN_PROGRESS.state(), "New "+PAGE_NAME+" didn't save properly");
    }

    @Test(dataProvider=DATA, priority=2)
    public void saveAndSubmitCssFile(JSONObject data) throws InterruptedException {
        String sCssFileName = data.get(CSS_FILE_NAME).toString();

        Assert.assertEquals(cssFileAdd.saveAndSubmitCssFile(data, sCssFileName), WorkflowState.FOR_APPROVAL.state(), "New " + PAGE_NAME + " doesn't submit properly (after Save And Submit)");
        Assert.assertTrue(cssFileAdd.checkCssFile(data, sCssFileName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Save and Submit)");
    }

    @Test(dataProvider=DATA, priority=3)
    public void publishCssFile(JSONObject data) throws InterruptedException {
        String sCssFileName = data.get(CSS_FILE_NAME).toString();
        Assert.assertEquals(cssFileAdd.publishCssFile(data, sCssFileName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=4)
    public void revertCssFile(JSONObject data) throws InterruptedException {
        String sCssFileName = data.get(CSS_FILE_NAME).toString();

        Assert.assertEquals(cssFileAdd.changeAndSubmitCssFile(data, sCssFileName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertEquals(cssFileAdd.revertToLiveCssFile(sCssFileName), WorkflowState.LIVE.state(), "Couldn't revert to Live changes for New "+ PAGE_NAME);
        Assert.assertTrue(cssFileAdd.checkCssFile(data, sCssFileName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Revert To Live)");
    }

    @Test(dataProvider=DATA, priority=5)
    public void changeAndSubmitCssFile(JSONObject data) throws Exception {
        String sCssFileName = data.get(CSS_FILE_NAME).toString();

        Assert.assertEquals(cssFileAdd.changeAndSubmitCssFile(data, sCssFileName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertTrue(cssFileAdd.checkCssFileCh(data, sCssFileName), "Submitted New "+ PAGE_NAME +" changes don't fit well to change data (after Change And Submit)");
    }

    @Test(dataProvider=DATA, priority=6)
    public void publishEditCssFile(JSONObject data) throws InterruptedException {
        String sCssFileName = data.get(CSS_FILE_NAME).toString();
        Assert.assertEquals(cssFileAdd.publishCssFile(data, sCssFileName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=7)
    public void deleteCssFile(JSONObject data) throws Exception {
        String sCssFileName = data.get(CSS_FILE_NAME).toString();
        Assert.assertEquals(cssFileAdd.setupAsDeletedCssFile(sCssFileName), WorkflowState.DELETE_PENDING.state(), "New "+ PAGE_NAME +" didn't setup as Deleted properly");
    }

    @Test(dataProvider=DATA, priority=8)
    public void removeCssFile(JSONObject data) throws Exception {
        String sCssFileName = data.get(CSS_FILE_NAME).toString();
        Assert.assertEquals(cssFileAdd.removeCssFile(data, sCssFileName), WorkflowState.NEW_ITEM.state(), "Couldn't remove New "+ PAGE_NAME +". Something went wrong.");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("css_file");
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
