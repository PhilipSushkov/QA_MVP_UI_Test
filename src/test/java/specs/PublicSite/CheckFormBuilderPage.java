package specs.PublicSite;

import org.apache.commons.lang.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.testng.annotations.*;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LiveSite.FormBuilderPage;
import pageobjects.LiveSite.HomePage;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SystemAdmin.SiteMaintenance.FunctionalBtn;
import specs.AbstractSpec;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by zacharyk on 2017-05-16.
 */

public class CheckFormBuilderPage extends AbstractSpec {

    //// WHEN ADDING A TEST TO THIS CLASS, ADD A ENTRY TO IT IN CheckFormBuilderPr.java \\\\

    private static HomePage homePage;
    private static FormBuilderPage formBuilderPage;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private static By systemAdminMenuButton, siteMaintenanceMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static FunctionalBtn functionalBtn;

    private final String DATA="getData";

    private final String testAccount = "test@q4websystems.com", testPassword = "testing!";

    private final String randLastName = "Last Name: " + RandomStringUtils.randomAlphanumeric(6);

    private final Long LONG_WAIT = 15000L;

    private final Long SHORT_WAIT = 3000L;

    @BeforeTest
    public void setUp() throws Exception {
        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUIPublicSite.getProperty("dataPath_FormBuilderData");
        sDataFileJson = propUIPublicSite.getProperty("json_FormBuilderData");

        // Enable SendGrid

        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        siteMaintenanceMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_SiteMaintenance"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        functionalBtn = new FunctionalBtn(driver);

        loginPage.loginUser();

        dashboard.openPageFromMenu(systemAdminMenuButton, siteMaintenanceMenuItem);

        functionalBtn.enableSendGrid();

        // Navigate to public homepage

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");

        homePage = new HomePage(driver);
        formBuilderPage = new FormBuilderPage(driver);

        Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");
    }

    @Test(priority = 1)
    public void canNavigateToFormBuilderPage() {
        try{
            Assert.assertTrue(homePage.selectFormBuilderFromMenu().formBuilderPageDisplayed(), "FormBuilder Page couldn't be opened");
        }catch (TimeoutException e) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
    }

    @Test(dataProvider = DATA, priority = 2)
    public void canSubmitForm(JSONObject data) throws InterruptedException {

        homePage.selectFormBuilderFromMenu();

        formBuilderPage.completeForm(data, randLastName);

        Thread.sleep(SHORT_WAIT);


        Assert.assertTrue(formBuilderPage.correctMessageDisplayed(data.get("expected_message").toString()));
    }

    @Test(dataProvider = DATA, priority = 3)
    public void emailSent(JSONObject data) throws InterruptedException {
        if (Boolean.valueOf(data.get("expect_success").toString())) {

            // Wait for email in inbox

            Thread.sleep(LONG_WAIT);

            try {
                Message[] mail = getMail(testAccount, testPassword, randLastName);
                Assert.assertNotEquals(mail.length, 0);
                String content = mail[0].getContent().toString();
                Assert.assertTrue(content.contains(data.get("first_name").toString()));
                Assert.assertTrue(content.contains(data.get("email").toString()));
                Assert.assertTrue(content.contains(data.get("comments").toString()));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }


    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("form_builder");

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
