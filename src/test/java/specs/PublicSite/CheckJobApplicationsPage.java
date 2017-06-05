package specs.PublicSite;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.*;
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
    private final String user = "test@q4websystems.com";
    private final String password = "testing!";
    private final String subject = "Job Application for position";

    @BeforeTest
    public void setUp(){
        sPathToFile = System.getProperty("user.dir") + propUIPublicSite.getProperty("dataPath_LiveSite");
        sDataFileJson = propUIPublicSite.getProperty("json_JobApplicationData");

        parser = new JSONParser();

        driver.get("http://chicagotest.q4web.com/English/Investors/");
        homePage = new HomePage(driver);
        jobApplicationsPage = new JobApplicationsPage(driver);
        homePage.selectJobApplicationFromMenu();
    }

    @AfterTest
    public void deleteEmail(){
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

            //Strictly checking if an email was sent
            if (data.get("check_email").toString() == "true") {
                Assert.assertTrue(jobApplicationsPage.checkEmail(data), "Job Application does not match email");

            } else if (data.get("check_email").toString() == "false") {
                //Checking if email didnt get sent
                Assert.assertFalse(jobApplicationsPage.checkEmail(data), "Job Application should not have been submitted");
            }
        }
    }

    @Test(dataProvider = DATA, priority = 3)
    public void checkFile(JSONObject data) throws IOException, MessagingException, InterruptedException {
        homePage.selectJobApplicationFromMenu();
        jobApplicationsPage.submitJobApplication(data);

        if (data.get("check_email").toString() == "true") {
            //Checking if email with file got sent
            if  (data.get("check_file").toString() == "true") {
                //Waiting for email with attachment to arrive
                deleteMail(user, password, subject);
                Assert.assertTrue(jobApplicationsPage.hasAttachments(data), "Email has no attachments");
                Assert.assertTrue(jobApplicationsPage.checkAttachments(data), "Attachments are not the same");
            }
        } else if (data.get("check_email").toString() == "false" ) {
            //Making sure no email got sent -> checking data against existing email
            Assert.assertFalse(jobApplicationsPage.checkEmail(data), "Job Application should not have been submitted");
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
