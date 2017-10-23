package specs.EmailAdmin.Templates;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.Dashboard.Dashboard;
import pageobjects.EmailAdmin.Templates.TemplateAdd;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;
import util.Functions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by charleszheng on 2017-10-16.
 */

public class CheckTemplateAdd extends AbstractSpec{
    private static By emailAdminMenuButton, templateMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static Functions functions;
    private static TemplateAdd templateAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", TEMPLATE_NAME ="template_name";

    @BeforeTest
    public void setUp() throws Exception {
        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        templateMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_Templates"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        templateAdd = new TemplateAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUIEmailAdmin.getProperty("dataPath_Template");
        sDataFileJson = propUIEmailAdmin.getProperty("json_TemplateData");

        parser = new JSONParser();

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception{
        dashboard.openPageFromMenu(emailAdminMenuButton, templateMenuItem);
    }

    @Test(priority = 1)
    public void checkTitle() throws Exception{
        final String expectedTitle = "Template Edit";
        Assert.assertEquals(templateAdd.getTitle(), expectedTitle, "Actual Template Edit page Title doesn't match to expected");
    }

    @Test(dataProvider = DATA , priority = 2)
    public void checkSaveTemplate(JSONObject data) throws Exception{
        String sTemplateName = data.get(TEMPLATE_NAME).toString();
        Assert.assertNotNull(templateAdd.saveTemplate(data, sTemplateName),"New "+sTemplateName+" didn't save properly");
        Assert.assertTrue(templateAdd.checkTemplate(data, sTemplateName),"New "+sTemplateName+" Check fails (After Save)");
    }

    @Test(dataProvider = DATA, priority = 3)
    public void checkSendTestEmail(JSONObject data) throws Exception{
        String sTemplateName = data.get(TEMPLATE_NAME).toString();

        Assert.assertNotNull(templateAdd.sendTestMail(data, sTemplateName),"New "+sTemplateName+" didn't test send properly" );
        Assert.assertNotNull(templateAdd.getSpecificComposeMail("test@q4websystems.com", "testing!" , data.get("subject").toString()),"New "+sTemplateName+" is not received" );
        functions.deleteMail("test@q4websystems.com", "testing!" ,data.get("subject").toString());
        Assert.assertNull(templateAdd.getSpecificComposeMail("test@q4websystems.com", "testing!" ,data.get("subject").toString()),"New "+sTemplateName+" is not deleted properly" );
    }

    @Test(dataProvider = DATA, priority = 4)
    public void checkEditTemplate(JSONObject data) throws Exception {
        String sTemplateName = data.get(TEMPLATE_NAME).toString();
        Assert.assertNotNull(templateAdd.editTemplate(data, sTemplateName),"New "+sTemplateName+" Edit fails" );
        Assert.assertTrue(templateAdd.checkTemplateCh(data,sTemplateName),"New "+sTemplateName+" Check fails (After Edit)");
    }

    @Test(dataProvider = DATA, priority = 5)
    public void checkDeleteMailingLists(JSONObject data) throws Exception {
        String sTemplateName = data.get(TEMPLATE_NAME).toString();
        Assert.assertNotNull(templateAdd.deleteTemplate(data, sTemplateName),"New "+sTemplateName+" Delete fails" );
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
            JSONArray jsonArray = (JSONArray) jsonObject.get("template");
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
