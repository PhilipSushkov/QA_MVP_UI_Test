package specs.ContentAdmin.FaqList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.ContentAdmin.FaqList.FaqAdd;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.PageAdmin.WorkflowState;
import specs.AbstractSpec;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andyp on 2017-06-20.
 */
public class CheckFaqAdd extends AbstractSpec {
    private static By contentAdminMenuButton, faqListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static FaqAdd faqAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", FAQ_NAME="name_EN", PAGE_NAME="Faq";

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        faqListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_FaqList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        faqAdd = new FaqAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_faqList");
        sDataFileJson = propUIContentAdmin.getProperty("json_faqListData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(contentAdminMenuButton, faqListMenuItem);
    }

    @Test(dataProvider=DATA, priority=1)
    public void saveFaq(JSONObject data) throws Exception {
        String sFaqName = data.get(FAQ_NAME).toString();
        String expectedTitleEdit = "Faq Edit";
        String expectedQuestionTitleEdit= "Edit Faq Question";

        Assert.assertEquals(faqAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        dashboard.openPageFromMenu(contentAdminMenuButton, faqListMenuItem);
        Assert.assertEquals(faqAdd.saveFaq(data, sFaqName), WorkflowState.IN_PROGRESS.state(), "New "+PAGE_NAME+" didn't save properly");
        Assert.assertEquals(faqAdd.getQuestionTitle(), expectedQuestionTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertTrue(faqAdd.saveQuestion(data, sFaqName), "Questions did not save properly");
    }
    
    @Test(dataProvider=DATA, priority=2)
    public void saveAndSubmitFaq(JSONObject data) throws InterruptedException {
        String sFaqName = data.get(FAQ_NAME).toString();

        Assert.assertEquals(faqAdd.saveAndSubmitFaq(data, sFaqName), WorkflowState.FOR_APPROVAL.state(), "New " + PAGE_NAME + " doesn't submit properly (after Save And Submit)");
        Assert.assertTrue(faqAdd.checkFaq(data, sFaqName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Save and Submit)");
    }

    @Test(dataProvider=DATA, priority=3)
    public void publishFaq(JSONObject data) throws InterruptedException {
        String sFaqName = data.get(FAQ_NAME).toString();
        Assert.assertEquals(faqAdd.publishFaq(data, sFaqName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=4)
    public void revertFaq(JSONObject data) throws InterruptedException {
        String sFaqName = data.get(FAQ_NAME).toString();

        Assert.assertEquals(faqAdd.changeAndSubmitFaq(data, sFaqName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertEquals(faqAdd.revertToLiveFaq(sFaqName), WorkflowState.LIVE.state(), "Couldn't revert to Live changes for New "+ PAGE_NAME);
        Assert.assertTrue(faqAdd.checkFaq(data, sFaqName), "Submitted New "+ PAGE_NAME +" data doesn't fit well to entry data (after Revert To Live)");
    }

    @Test(dataProvider=DATA, priority=5)
    public void changeAndSubmitFaq(JSONObject data) throws Exception {
        String sFaqName = data.get(FAQ_NAME).toString();

        Assert.assertEquals(faqAdd.changeAndSubmitFaq(data, sFaqName), WorkflowState.FOR_APPROVAL.state(), "Some fields of New "+ PAGE_NAME +" didn't change properly (after Save and Submit)");
        Assert.assertTrue(faqAdd.checkFaqCh(data, sFaqName), "Submitted New "+ PAGE_NAME +" changes don't fit well to change data (after Change And Submit)");
    }

    @Test(dataProvider=DATA, priority=6)
    public void publishEditFaq(JSONObject data) throws InterruptedException {
        String sFaqName = data.get(FAQ_NAME).toString();
        Assert.assertEquals(faqAdd.publishFaq(data, sFaqName), WorkflowState.LIVE.state(), "New "+ PAGE_NAME +" doesn't publish properly (after Publish)");
    }

    @Test(dataProvider=DATA, priority=7)
    public void deleteFaq(JSONObject data) throws Exception {
        String sFaqName = data.get(FAQ_NAME).toString();
        Assert.assertEquals(faqAdd.setupAsDeletedFaq(sFaqName), WorkflowState.DELETE_PENDING.state(), "New "+ PAGE_NAME +" didn't setup as Deleted properly");
    }

    @Test(dataProvider=DATA, priority=8)
    public void removeFaq(JSONObject data) throws Exception {
        String sFaqName = data.get(FAQ_NAME).toString();
        Assert.assertEquals(faqAdd.removeFaq(data, sFaqName), WorkflowState.NEW_ITEM.state(), "Couldn't remove New "+ PAGE_NAME +". Something went wrong.");
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("faq");
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
