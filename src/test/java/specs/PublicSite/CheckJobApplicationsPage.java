package specs.PublicSite;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.LiveSite.HomePage;
import pageobjects.LiveSite.JobApplicationsPage;
import specs.AbstractSpec;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andyp on 2017-05-17.
 */
public class CheckJobApplicationsPage extends AbstractSpec {

    private static HomePage homePage;
    private static JobApplicationsPage jobApplicationsPage;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData";

    @BeforeTest
    public void setUp(){
        sPathToFile = System.getProperty("user.dir") + propUIPublicSite.getProperty("dataPath_LiveSite");
        sDataFileJson = propUIPublicSite.getProperty("json_JobApplicationData");

        parser = new JSONParser();

        driver.get("http://chicagotest.q4web.com/English/Investors/default.aspx");
        homePage = new HomePage(driver);
        jobApplicationsPage = new JobApplicationsPage(driver);
        homePage.selectJobApplicationFromMenu();
    }

    @Test
    public void canNavigateToJobApplicationsPage() {
        try {
            Assert.assertTrue(homePage.selectJobApplicationFromMenu().applicationPageDisplayed(), "Job Applications Page couldn't be opened");
        } catch (TimeoutException e) {
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        }
    }

    @Test(dataProvider = DATA, priority = 1)
    public void firstNameFieldNotFilled(JSONObject data){
        Assert.assertNotNull(jobApplicationsPage.checkErrorMessages(), "There are no error messages");
        Assert.assertTrue(jobApplicationsPage.checkFirstNameRequiredField(data),"Error message should be there for first name");
    }

    @Test(dataProvider = DATA, priority = 2)
    public void wrongEmailFormatting(JSONObject data) {

        Assert.assertNotNull(jobApplicationsPage.checkErrorMessages(), "There are no error messages");
        Assert.assertTrue(jobApplicationsPage.checkEmailFormat(data), "Error message should be there for invalid email formatting");
    }

    /*Test for uploading file


    */

    @Test(dataProvider = DATA, priority = 3)
    public void successfulSubmission(JSONObject data){

            Assert.assertTrue(jobApplicationsPage.checkSuccessSubmission(data), "Submission success message should be there");
        }

    @Test(dataProvider = DATA, dependsOnMethods = { "successfulSubmission"} )
    public void checkEmailForApplication(JSONObject data) throws IOException, MessagingException {

        Assert.assertTrue(jobApplicationsPage.getEmailContents(data));
    }

    @DataProvider
    public Object[][] getData() {

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray jsonArray = (JSONArray) jsonObject.get("job_application");
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
