package specs.PreviewSite;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.LiveSite.HomePage;
import pageobjects.LiveSite.JobApplicationsPage;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;
import specs.PublicSite.CheckJobApplicationsPage;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andyp on 2017-06-07.
 */
public class CheckJobApplicationPr extends AbstractSpec {

    private static HomePage homePage;
    private static JobApplicationsPage jobApplicationsPage;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData";
    private final String user = "test@q4websystems.com";
    private final String password = "testing!";
    private final String subject = "Job Application for position";

    @BeforeTest
    public void goToPreviewSite() throws Exception {
        sPathToFile = System.getProperty("user.dir") + propUIPublicSite.getProperty("dataPath_LiveSite");
        sDataFileJson = propUIPublicSite.getProperty("json_JobApplicationData");

        parser = new JSONParser();

        new LoginPage(driver).loginUser().previewSite().goToInvestorsPage();
        homePage = new HomePage(driver);
        jobApplicationsPage = new JobApplicationsPage(driver);

    }

    @AfterTest
    public void deleteEmail() throws InterruptedException {
        deleteMail(user, password, subject);
    }

    @Test(dataProvider = DATA, priority = 1)
    public void submitJobApplication(JSONObject data){
        String sMessage = data.get("expected").toString();

        Assert.assertTrue(homePage.selectJobApplicationFromMenu().applicationPageDisplayed(), "Job Applications Page couldn't be opened");
        Assert.assertTrue(jobApplicationsPage.submitJobApplication(data).contains(sMessage),"Job Application Submission doesn't work properly");
    }

    @Test(dataProvider = DATA, priority = 2)
    public void checkEmail(JSONObject data) throws IOException, MessagingException, InterruptedException {
        // Strictly checking for if email gets sent - no file attached
        if (data.get("check_file").toString() == "false") {
            homePage.selectJobApplicationFromMenu();
            jobApplicationsPage.submitJobApplication(data);
            String content = jobApplicationsPage.getEmailContent();

            //Strictly checking if an email was sent
            if (data.get("check_email").toString() == "true") {
                Assert.assertTrue(jobApplicationsPage.getFirstName(data, content), "First name does not match");
                Assert.assertTrue(jobApplicationsPage.getLastName(data, content), "Last name does not match");
                Assert.assertTrue(jobApplicationsPage.getAddress(data, content), "Address does not match");
                Assert.assertTrue(jobApplicationsPage.getCity(data, content), "City does not match");
                Assert.assertTrue(jobApplicationsPage.getProvince(data, content), "Province does not match");
                Assert.assertTrue(jobApplicationsPage.getCountry(data, content), "Country does not match");
                Assert.assertTrue(jobApplicationsPage.getPostalCode(data, content), "Postal code does not match");
                Assert.assertTrue(jobApplicationsPage.getHomePhone(data, content), "Home phone does not match");
                Assert.assertTrue(jobApplicationsPage.getBusinessPhone(data, content), "Business phone does not match");
                Assert.assertTrue(jobApplicationsPage.getFax(data, content), "Fax does not match");
                Assert.assertTrue(jobApplicationsPage.getEmail(data, content), "Email does not match");
                Assert.assertTrue(jobApplicationsPage.getCoverLetterText(data, content), "Cover letter text does not match");
                Assert.assertTrue(jobApplicationsPage.getResumeText(data, content), "Resume text does not match");


            } else if (data.get("check_email").toString() == "false") {
                //Checking if email didnt get sent
                //Got rid of first name because I'm using 'contains' and it is causing false alarms
                Assert.assertFalse(jobApplicationsPage.getLastName(data, content), "Last name should not have been submitted");
                Assert.assertFalse(jobApplicationsPage.getAddress(data, content), "Address should not have been submitted");
                Assert.assertFalse(jobApplicationsPage.getCity(data, content), "City should not have been submitted");
                Assert.assertFalse(jobApplicationsPage.getProvince(data, content), "Province should not have been submitted");
                Assert.assertFalse(jobApplicationsPage.getCountry(data, content), "Country should not have been submitted");
                Assert.assertFalse(jobApplicationsPage.getPostalCode(data, content), "Postal code should not have been submitted");
                Assert.assertFalse(jobApplicationsPage.getHomePhone(data, content), "Home phone should not have been submitted");
                Assert.assertFalse(jobApplicationsPage.getBusinessPhone(data, content), "Business phone should not have been submitted");
                Assert.assertFalse(jobApplicationsPage.getFax(data, content), "Fax should not have been submitted");
                Assert.assertFalse(jobApplicationsPage.getEmail(data, content), "Email should not have been submitted");
                Assert.assertFalse(jobApplicationsPage.getCoverLetterText(data, content), "Cover letter text should not have been submitted");
                Assert.assertFalse(jobApplicationsPage.getResumeText(data, content), "Resume text should not have been submitted");
            }
        }
    }

    @Test(dataProvider = DATA, priority = 3)
    public void checkFile(JSONObject data) throws IOException, MessagingException, InterruptedException {
        if (data.get("check_email").toString() == "true") {
            //Checking if email with file got sent - will skip other data
            if (data.get("check_file").toString() == "true") {
                deleteMail(user, password, subject);
                homePage.selectJobApplicationFromMenu();
                jobApplicationsPage.submitJobApplication(data);

                Assert.assertTrue(jobApplicationsPage.hasAttachments(data), "Email has no attachments");
                Assert.assertTrue(jobApplicationsPage.checkAttachments(data), "Attachments are not the same");
            }
        }
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
