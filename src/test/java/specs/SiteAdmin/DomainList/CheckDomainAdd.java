package specs.SiteAdmin.DomainList;

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
import pageobjects.SiteAdmin.DomainList.DomainAdd;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andyp on 2017-06-15.
 */
public class CheckDomainAdd extends AbstractSpec {
    private static By siteAdminMenuButton, domainListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static DomainAdd domainAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", DOMAIN_NAME="domain_name", PAGE_NAME="Domain";

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        domainListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_DomainList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        domainAdd = new DomainAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_DomainList");
        sDataFileJson = propUISiteAdmin.getProperty("json_DomainListData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(siteAdminMenuButton, domainListMenuItem);
    }

    @Test(dataProvider=DATA, priority=1)
    public void saveDomain(JSONObject data) {
        String sDomainName = data.get(DOMAIN_NAME).toString();
        String expectedTitleEdit = "Domain Edit";

        Assert.assertEquals(domainAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(domainAdd.saveDomain(data, sDomainName), WorkflowState.IN_PROGRESS.state(), "New "+PAGE_NAME+" didn't save properly");
    }
//
    @Test(dataProvider=DATA, priority=2)
    public void saveAndSubmitDomain(JSONObject data) throws InterruptedException {
        String sDomainName = data.get(DOMAIN_NAME).toString();

        Assert.assertEquals(domainAdd.saveAndSubmitDomain(data, sDomainName), WorkflowState.FOR_APPROVAL.state(), "New " + PAGE_NAME + " doesn't submit properly (after Save And Submit)");
        Assert.assertTrue(domainAdd.checkDomain(data, sDomainName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Save and Submit)");
    }
//
//    @Test(dataProvider=DATA, priority=3)
//    public void publishDomain(JSONObject data) throws InterruptedException {
//        String sDomainName = data.get(DOMAIN_NAME).toString();
//        Assert.assertEquals(domainAdd.publishDomain(data, sDomainName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
//    }
//
//    @Test(dataProvider=DATA, priority=4)
//    public void revertDomain(JSONObject data) throws InterruptedException {
//        String sDomainName = data.get(DOMAIN_NAME).toString();
//
//        Assert.assertEquals(domainAdd.changeAndSubmitDomain(data, sDomainName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
//        Assert.assertEquals(domainAdd.revertToLiveDomain(sDomainName), WorkflowState.LIVE.state(), "Couldn't revert to Live changes for New "+ PAGE_NAME);
//        Assert.assertTrue(domainAdd.checkDomain(data, sDomainName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Revert To Live)");
//    }
//
//    @Test(dataProvider=DATA, priority=5)
//    public void changeAndSubmitDomain(JSONObject data) throws Exception {
//        String sDomainName = data.get(DOMAIN_NAME).toString();
//
//        Assert.assertEquals(domainAdd.changeAndSubmitDomain(data, sDomainName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
//        Assert.assertTrue(domainAdd.checkDomainCh(data, sDomainName), "Submitted New "+ PAGE_NAME +" changes don't fit well to change data (after Change And Submit)");
//    }
//
//    @Test(dataProvider=DATA, priority=6)
//    public void publishEditDomain(JSONObject data) throws InterruptedException {
//        String sDomainName = data.get(DOMAIN_NAME).toString();
//        Assert.assertEquals(domainAdd.publishDomain(data, sDomainName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
//    }
//
//    @Test(dataProvider=DATA, priority=7)
//    public void deleteDomain(JSONObject data) throws Exception {
//        String sDomainName = data.get(DOMAIN_NAME).toString();
//        Assert.assertEquals(domainAdd.setupAsDeletedDomain(sDomainName), WorkflowState.DELETE_PENDING.state(), "New "+ PAGE_NAME +" didn't setup as Deleted properly");
//    }
//
//    @Test(dataProvider=DATA, priority=8)
//    public void removeDomain(JSONObject data) throws Exception {
//        String sDomainName = data.get(DOMAIN_NAME).toString();
//        Assert.assertEquals(domainAdd.removeDomain(data, sDomainName), WorkflowState.NEW_ITEM.state(), "Couldn't remove New "+ PAGE_NAME +". Something went wrong.");
//    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("domain_list");
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
