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
import pageobjects.LiveSite.FormBuilderPage;
import pageobjects.LiveSite.HomePage;
import specs.AbstractSpec;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by zacharyk on 2017-05-16.
 */

public class CheckFormBuilderPage extends AbstractSpec {

    private static HomePage homePage;
    private static FormBuilderPage formBuilderPage;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData";

    private final String testAccount = "test@q4websystems.com", testPassword = "testing!";

    private final Long EMAIL_WAIT = 15000L;

    @BeforeTest
    public void setUp() {
        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUIPublicSite.getProperty("dataPath_FormBuilderData");
        sDataFileJson = propUIPublicSite.getProperty("json_FormBuilderData");
    }

    @BeforeClass
    public void goToPublicSite() {

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");

        homePage = new HomePage(driver);
        formBuilderPage = new FormBuilderPage(driver);

        Assert.assertTrue(homePage.logoIsPresent(), "Home page of public site has not been loaded.");
    }

    @Test
    public void canNavigateToFormBuilderPage() {
        try{
            Assert.assertTrue(homePage.selectFormBuilderFromMenu().formBuilderPageDisplayed(), "FormBuilder Page couldn't be opened");
        }catch (TimeoutException e) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
    }

    @Test(dataProvider = DATA)
    public void canSubmitForm(JSONObject data) {

        String randLastName = RandomStringUtils.randomAlphanumeric(6);

        homePage.selectFormBuilderFromMenu();

        formBuilderPage.enterFields(data.get("first_name").toString(),
                                    randLastName,                       // email subjectID
                                    data.get("email").toString(),
                                    data.get("company").toString(),
                                    data.get("address").toString(),
                                    data.get("city").toString(),
                                    data.get("country").toString(),
                                    data.get("phone").toString(),
                                    data.get("comments").toString());

        formBuilderPage.submitForm();

        Assert.assertEquals(formBuilderPage.successDisplayed(), data.get("expect_success"));

        try {
            Thread.sleep(EMAIL_WAIT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Boolean.valueOf(data.get("expect_success").toString())) {
            try {
                String content = getRecentMail(testAccount, testPassword, randLastName).getContent().toString();
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

    @Test
    public void submitIncompleteForm() {
        homePage.selectFormBuilderFromMenu();
        formBuilderPage.submitForm();

        Assert.assertTrue(formBuilderPage.formBuilderPageDisplayed());
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
