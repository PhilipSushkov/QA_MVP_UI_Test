package specs.PublicSite;

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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by zacharyk on 2017-05-16.
 */

public class CheckFormBuilderPage extends AbstractSpec {

    private final String firstName = "Winston";
    private final String lastName = "Smith";
    private final String email = "winston@bigbrother.com";
    private final String company = "Ministry of Truth";
    private final String address = "Airstrip One";
    private final String city = "London";
    private final String country = "United Kingdom";
    private final String phone = "1984";
    private final String comments = "I love Big Brother";

    private static HomePage homePage;
    private static FormBuilderPage formBuilderPage;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", FILTER_NAME="filter_name";

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
        homePage.selectFormBuilderFromMenu();
        formBuilderPage.enterFields(data.get("first_name").toString(),
                                    data.get("last_name").toString(),
                                    data.get("email").toString(),
                                    data.get("company").toString(),
                                    data.get("address").toString(),
                                    data.get("city").toString(),
                                    data.get("country").toString(),
                                    data.get("phone").toString(),
                                    data.get("comments").toString());
        formBuilderPage.submitForm();

        Assert.assertEquals(formBuilderPage.successDisplayed(), data.get("expect_success"));

        //Assert.assertTrue(formBuilderPage.successDisplayed());
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
