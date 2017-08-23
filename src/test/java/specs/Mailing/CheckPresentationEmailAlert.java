package specs.Mailing;

import org.apache.commons.lang.RandomStringUtils;
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
import pageobjects.ContentAdmin.Presentations.PresentationEdit;
import pageobjects.ContentAdmin.Presentations.Presentations;
import pageobjects.Dashboard.Dashboard;
import pageobjects.EmailAdmin.ManageList.MailingLists;
import pageobjects.EmailAdmin.Subscribers.MailingListUsers;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SystemAdmin.AlertFilterList.AlertFilterAdd;
import pageobjects.SystemAdmin.AlertFilterList.AlertFilterList;
import pageobjects.SystemAdmin.SiteMaintenance.FunctionalBtn;
import specs.AbstractSpec;

import javax.mail.Message;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by zacharyk on 2017-06-08.
 */
public class CheckPresentationEmailAlert extends AbstractSpec {

    private static String sPathToFile, sDataFileJson, sFileJson;
    private static JSONParser parser;

    private static LoginPage loginPage;
    private static Dashboard dashboard;

    // SendGrid data

    private static By systemAdminMenuButton, siteMaintenanceMenuItem;
    private static FunctionalBtn functionalBtn;

    // Mailing List data

    private static By emailAdminMenuButton, manageListMenuItem;
    private static MailingLists mailingLists;

    // Subscribers data

    private static By subscribersMenuItem;
    private static MailingListUsers mailingListUsers;

    // Alert Filter data

    private static By alertFilterListMenuItem;
    private static AlertFilterList alertFilterList;
    private static AlertFilterAdd alertFilterAdd;

    // Presentation data

    private static By addPresentationButton;
    private static PresentationEdit presentationEdit;
    private static Presentations presentations;

    // Cleanup

    JSONObject savedData;
    JSONObject testData;
    String presentationURL;
    String title;

    // Misc

    private final String DATA="getData";

    private final Long MED_WAIT = 10000L;

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
    SimpleDateFormat hourFormat = new SimpleDateFormat("h");
    SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");

    String date = dateFormat.format(new Date());
    String hour = hourFormat.format(new Date());
    String minute = minuteFormat.format(new Date());

    private final String companySubjectTag = "ChicagoTest - ";

    JSONObject jsonMain = new JSONObject();
    JSONObject jsonObj = new JSONObject();


    @BeforeTest
    public void setUp() throws Exception {
        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_presentationEmailAlertData");
        sDataFileJson = propUIContentAdmin.getProperty("json_presentationEmailAlertData");
        sFileJson = propUIContentAdmin.getProperty("json_presentationEmailAlert");

        loginPage = new LoginPage(driver);
        loginPage.loginUser();

        // Enable SendGrid

        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        siteMaintenanceMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_SiteMaintenance"));

        dashboard = new Dashboard(driver);
        functionalBtn = new FunctionalBtn(driver);

        dashboard.openPageFromMenu(systemAdminMenuButton, siteMaintenanceMenuItem);

        functionalBtn.enableSendGrid();

        // Misc setup

        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        manageListMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_ManageList"));
        mailingLists = new MailingLists(driver);

        subscribersMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_Subscribers"));
        mailingListUsers = new MailingListUsers(driver);

        alertFilterAdd = new AlertFilterAdd(driver);
        alertFilterListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_AlertFilterList"));
        alertFilterList = new AlertFilterList(driver);

        presentationEdit = new PresentationEdit(driver);
        presentations = new Presentations(driver);

        addPresentationButton = By.xpath(propUICommon.getProperty("btn_AddPresentation"));

    }

    @Test(dataProvider = DATA, priority = 1)
    public void checkRequirements(JSONObject data) throws Exception {

        try {
            FileReader readFile = new FileReader(sPathToFile + sFileJson);
            jsonMain = (JSONObject) parser.parse(readFile);

            jsonObj.put("preconditions_met", false);

            // Check that testing list exists

            dashboard.openPageFromMenu(emailAdminMenuButton, manageListMenuItem);
            Assert.assertTrue(mailingLists.listActive(data.get("mailing_list").toString()));

            // Check that test email is subscribed

            dashboard.openPageFromMenu(emailAdminMenuButton, subscribersMenuItem);
            Assert.assertTrue(mailingListUsers.userSubscribed(data.get("email_account").toString(), data.get("mailing_list").toString()));

            jsonObj.put("preconditions_met", true);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            jsonMain.put(data.get("test_name").toString(), jsonObj);
            FileWriter writeFile = new FileWriter(sPathToFile + sFileJson);
            writeFile.write(jsonMain.toJSONString());
            writeFile.flush();
        }
    }

    @Test(dataProvider = DATA, priority = 2)
    public void publishPresentation(JSONObject data) throws Exception {

        try {

            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                testData = (JSONObject) jsonMain.get(data.get("test_name").toString());
            } catch (ParseException e) {
            }

            Assert.assertTrue(Boolean.valueOf(testData.get("preconditions_met").toString()));

            // Alert filter setup

            dashboard.openPageFromMenu(systemAdminMenuButton, alertFilterListMenuItem);
            alertFilterList.findElement(By.xpath(propUISystemAdmin.getProperty("edit_PresentationFilter"))).click();

            String filterType = data.get("filter_type").toString();

            alertFilterAdd.setContentFilters(filterType, data.get("filter_title").toString(), data.get("filter_body").toString());
            alertFilterAdd.enableSendToList(data.get("mailing_list").toString() + data.get("mailing_list_language").toString());

            // Publish presentation

            String taggedTitle = data.get("presentation_title").toString() + RandomStringUtils.randomAlphanumeric(6);

            dashboard.openPageFromCommonTasks(addPresentationButton);
            presentationURL = presentationEdit.getUrl();

            testData.put("presentation_url", presentationURL);
            testData.put("title", taggedTitle);
            jsonMain.put(data.get("test_name").toString(), testData);

            FileWriter writeFile = new FileWriter(sPathToFile+sFileJson);
            writeFile.write(jsonMain.toJSONString());
            writeFile.flush();

            presentationEdit.addNewPresentation(taggedTitle, date, hour, minute, "PM", new String[2]);
            presentations.publishPresentation(taggedTitle);

            driver.get(presentationURL);
            for (int i=1; i<5; i++) {
                if (presentationEdit.getWorkflowState().getText().equals("Live")) {
                    break;
                } else {
                    System.out.println("attempt #" + i);
                    Thread.sleep(MED_WAIT);
                    driver.navigate().refresh();
                }
            }

            Assert.assertEquals(presentationEdit.getWorkflowState().getText(), "Live");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = DATA, priority = 3)
    public void checkPresentationEmailAlert(JSONObject data) throws Exception {

        try {

            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                testData = (JSONObject) jsonMain.get(data.get("test_name").toString());
            } catch (ParseException e) {
            }

            Assert.assertTrue(Boolean.valueOf(testData.get("preconditions_met").toString()));

            Message[] email = getMail(data.get("email_account").toString(), data.get("email_password").toString(), "QaEnsco - " + testData.get("title").toString());
            System.out.println(testData.get("title").toString());

            if (Boolean.valueOf(data.get("expect_mail").toString())) {
                for (int i = 0; i < 5; i++) {
                    if (email.length != 0) {
                        break;
                    } else {
                        System.out.println("attempt #" + i);
                        email = getMail(data.get("email_account").toString(), data.get("email_password").toString(), "QaEnsco - " + testData.get("title").toString());
                        Thread.sleep(MED_WAIT);
                    }
                }
                Assert.assertNotEquals(email.length, 0);
            } else {
                Assert.assertEquals(email.length, 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = DATA, priority = 4, alwaysRun=true)
    public void cleanUp(JSONObject data) {

        try {
            FileReader readFile = new FileReader(sPathToFile + sFileJson);
            savedData = (JSONObject) parser.parse(readFile);
            testData = (JSONObject) savedData.get(data.get("test_name").toString());

            Assert.assertTrue(Boolean.valueOf(testData.get("preconditions_met").toString()));

            presentationURL = testData.get("presentation_url").toString();
            title = testData.get("title").toString();

            driver.get(presentationURL);

            presentationEdit.deletePresentation();
            presentations.publishPresentation(title);

            if (Boolean.valueOf(data.get("expect_mail").toString())) {
                deleteMail(data.get("email_account").toString(), data.get("email_password").toString(),
                        companySubjectTag + title);
            }

            savedData.remove(data.get("test_name").toString());
            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(savedData.toJSONString());
            file.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @AfterTest
    public void tearDown() { dashboard.logoutFromAdmin(); }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("presentation_email_alert");
            ArrayList<Object> zoom = new ArrayList<>();

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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
