package specs.Mailing;

import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.PressReleases.PressReleaseEdit;
import pageobjects.ContentAdmin.PressReleases.PressReleases;
import pageobjects.EmailAdmin.ManageList.MailingLists;
import pageobjects.EmailAdmin.Subscribers.MailingListUsers;
import pageobjects.SystemAdmin.AlertFilterList.AlertFilterAdd;
import pageobjects.SystemAdmin.AlertFilterList.AlertFilterEdit;
import pageobjects.SystemAdmin.AlertFilterList.AlertFilterList;
import pageobjects.SystemAdmin.SiteMaintenance.FunctionalBtn;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by zacharyk on 2017-05-19.
 */

public class CheckPressReleaseEmailAlert extends AbstractSpec {

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private static LoginPage loginPage;
    private static Dashboard dashboard;

    // SendGrid data

    private static By systemAdminMenuButton, siteMaintenanceMenuItem;
    private static FunctionalBtn functionalBtn;

    // Mailing List data

    private final String mailingList = "Test List";

    private static By emailAdminMenuButton, manageListMenuItem;
    private static MailingLists mailingLists;

    // Subscribers data

    private static By subscribersMenuItem;
    private static MailingListUsers mailingListUsers;

    // Alert Filter data

    private static By alertFilterListMenuItem;
    private static AlertFilterList alertFilterList;
    private static AlertFilterAdd alertFilterAdd;

    // Press Release data

    private static By addPressReleaseButton;
    private static PressReleaseEdit pressReleaseEdit;
    private static PressReleases pressReleases;

    // Misc

    private final String DATA="getData";
    private final String testAccount = "test@q4websystems.com", testPassword = "testing!";

    private final Long LONG_WAIT = 10000L;

    @BeforeTest
    public void setUp() throws Exception {
        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_pressReleaseEmailAlertData");
        sDataFileJson = propUIContentAdmin.getProperty("json_pressReleaseEmailAlertData");

        loginPage = new LoginPage(driver);
        loginPage.loginUser();

        // Enable SendGrid

        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        siteMaintenanceMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_SiteMaintenance"));

        dashboard = new Dashboard(driver);
        functionalBtn = new FunctionalBtn(driver);

        dashboard.openPageFromMenu(systemAdminMenuButton, siteMaintenanceMenuItem);

        functionalBtn.enableSendGrid();

        // Check that testing list exists

        emailAdminMenuButton = By.xpath(propUIEmailAdmin.getProperty("btnMenu_EmailAdmin"));
        manageListMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_ManageList"));
        mailingLists = new MailingLists(driver);

        dashboard.openPageFromMenu(emailAdminMenuButton, manageListMenuItem);

        Assert.assertTrue(mailingLists.listActive(mailingList));

        // Check that test email is subscribed

        subscribersMenuItem = By.xpath(propUIEmailAdmin.getProperty("btnMenu_Subscribers"));
        mailingListUsers = new MailingListUsers(driver);

        dashboard.openPageFromMenu(emailAdminMenuButton, subscribersMenuItem);

        Assert.assertTrue(mailingListUsers.userSubscribed(testAccount, mailingList));

        // Misc setup

        alertFilterListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_AlertFilterList"));
        alertFilterList = new AlertFilterList(driver);

        addPressReleaseButton = By.xpath(propUICommon.getProperty("btn_AddPressRelease"));
    }

    @Test(dataProvider = DATA)
    public void checkPressReleaseEmailAlert(JSONObject data) throws Exception {

        // Alert filter setup
        
        dashboard.openPageFromMenu(systemAdminMenuButton, alertFilterListMenuItem);
        alertFilterList.findElement(By.xpath(propUISystemAdmin.getProperty("edit_PressReleaseFilter"))).click();

        alertFilterAdd = new AlertFilterAdd(driver);
        String filterType = data.get("filter_type").toString();

        alertFilterAdd.setContentFilters(filterType, data.get("filter_title").toString(), data.get("filter_body").toString());
        alertFilterAdd.enableSendToList(mailingList + " - English");

        // Publish the press release

        dashboard.openPageFromCommonTasks(addPressReleaseButton);

        String taggedHeadline = data.get("headline").toString() + RandomStringUtils.randomAlphanumeric(6);

        pressReleaseEdit = new PressReleaseEdit(driver);
        pressReleaseEdit.addNewPressRelease(taggedHeadline, data.get("date").toString(), data.get("hour").toString(),
                                            data.get("minute").toString(), data.get("AMPM").toString(), new String[2]);

        pressReleases = new PressReleases(driver);
        pressReleases.publishPressRelease(taggedHeadline);

        // Wait for email in inbox

        Thread.sleep(LONG_WAIT);

        Message email = getRecentMail(testAccount, testPassword, taggedHeadline);

        if (Boolean.valueOf(data.get("expect_mail").toString())) {
            Assert.assertNotNull(email);
        } else {
            Assert.assertNull(email);
        }
    }

    @AfterTest
    public void tearDown() {
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("press_release_email_alert");

            Object[][] data = new Object[jsonArray.size()][1];
            for (int i = 0; i < jsonArray.size(); i++) {
                data[i][0] = jsonArray.get(i);
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
