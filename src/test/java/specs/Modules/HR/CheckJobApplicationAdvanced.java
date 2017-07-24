package specs.Modules.HR;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Modules.HR.JobApplicationAdvanced;
import pageobjects.Modules.ModuleBase;
import pageobjects.Modules.PageForModules;
import pageobjects.PageAdmin.WorkflowState;
import specs.AbstractSpec;
import specs.Modules.util.ModuleFunctions;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andyp on 2017-07-20.
 */
public class CheckJobApplicationAdvanced extends AbstractSpec {
    private static By pageAdminMenuButton;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PageForModules pageForModules;
    private static JobApplicationAdvanced jobApplicationAdvanced;
    private static ModuleBase moduleBase;
    private ArrayList<String> emailContent;

    private static String sPathToFile, sDataFileJson, sPathToModuleFile, sFileModuleJson;
    private static JSONParser parser;

    private static String user, password, jobApplicationSubject, autoResponseSubject;

    private final String MODULE_DATA="moduleData", MODULE_NAME="job_application_advanced", PAGE_DATA = "pageData", PAGE_NAME = "HR_modules";

    @BeforeTest
    public void setUp() throws Exception {
        pageAdminMenuButton = By.xpath(propUIModulesHR.getProperty("btnMenu_PageAdmin"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pageForModules = new PageForModules(driver);
        jobApplicationAdvanced = new JobApplicationAdvanced(driver);

        sPathToFile = System.getProperty("user.dir") + propUIModulesHR.getProperty("dataPath_HR");
        sDataFileJson = propUIModulesHR.getProperty("json_JobApplicationAdvancedData");
        sPathToModuleFile = System.getProperty("user.dir") + propUIModulesHR.getProperty("dataPath_HR");
        sFileModuleJson = propUIModulesHR.getProperty("json_JobApplicationAdvancedProp");

        moduleBase = new ModuleBase(driver, sPathToModuleFile, sFileModuleJson);

        parser = new JSONParser();

        user = "test@q4websystems.com";
        password = "testing!";
        jobApplicationSubject = "Job Application for position";
        autoResponseSubject = "Application for";

        deleteMail(user,password, jobApplicationSubject);
        deleteMail(user,password, autoResponseSubject);

        loginPage.loginUser();
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromCommonTasks(pageAdminMenuButton);
    }

    @Test(dataProvider=PAGE_DATA, priority=1, enabled=true)
    public void createJobApplicationAdvancedPage(JSONObject module) throws InterruptedException {
        Assert.assertEquals(pageForModules.savePage(module, MODULE_NAME), WorkflowState.IN_PROGRESS.state(), "New "+MODULE_NAME+" Page didn't save properly");
        Assert.assertEquals(pageForModules.saveAndSubmitPage(module, MODULE_NAME), WorkflowState.FOR_APPROVAL.state(), "Couldn't submit New "+MODULE_NAME+" Page properly");
        Assert.assertEquals(pageForModules.publishPage(MODULE_NAME), WorkflowState.LIVE.state(), "Couldn't publish New "+MODULE_NAME+" Page properly");
    }

    @Test(dataProvider=MODULE_DATA, priority=2, enabled=true)
    public void createJobApplicationAdvancedModule(JSONObject module) throws InterruptedException {
        String sModuleNameSet = module.get("module_title").toString();
        Assert.assertEquals(moduleBase.saveModule(module, MODULE_NAME), WorkflowState.IN_PROGRESS.state(), "New "+sModuleNameSet+" Module didn't save properly");
        Assert.assertEquals(jobApplicationAdvanced.saveAndSubmitModule(module, sModuleNameSet), WorkflowState.FOR_APPROVAL.state(), "Couldn't submit New "+sModuleNameSet+" Module properly");
        Assert.assertEquals(moduleBase.publishModule(sModuleNameSet), WorkflowState.LIVE.state(), "Couldn't publish New "+sModuleNameSet+" Module properly");
    }

    @Test(dataProvider=MODULE_DATA, priority=3, enabled=true)
    public void checkProperties(JSONObject module) throws InterruptedException {
        // Checks that all input properties were saved correctly
        Assert.assertEquals(jobApplicationAdvanced.goToModuleEditPage(module.get("module_title").toString()), WorkflowState.LIVE.state());
        JSONArray JSONArrProp = (JSONArray) module.get("properties");
        for (Object property : JSONArrProp) {
            String sProperty = property.toString();
            By propertyTextValue = By.xpath("//td[contains(@class, 'DataGridItemBorderLeft')][(text()='"+sProperty.split(";")[0]+"')]/parent::tr/td/div/input[contains(@id, 'txtStatic')]");
            By propertySelectValue = By.xpath("//td[contains(@class, 'DataGridItemBorderLeft')][(text()='" + sProperty.split(";")[0] + "')]/parent::tr/td/div/select[contains(@id, 'ddlDynamic')]/option[@selected]");
            if (!driver.findElements(propertyTextValue).isEmpty()) {
                Assert.assertEquals(driver.findElement(propertyTextValue).getAttribute("value"), sProperty.split(";")[1],
                        sProperty.split(";")[0] + " property did not save correctly");
            } else if (!driver.findElements(propertySelectValue).isEmpty()) {
                Assert.assertEquals(driver.findElement(propertySelectValue).getText(), sProperty.split(";")[1],
                        sProperty.split(";")[0] + " property did not save correctly");
            }
        }
    }

    @Test(dataProvider=MODULE_DATA, priority=4, enabled=true)
    public void checkJobApplicationAdvancedPreview(JSONObject module) throws InterruptedException, IOException, MessagingException {

        try {
            String sModuleNameSet = module.get("module_title").toString();
            Assert.assertTrue(moduleBase.openModulePreview(sModuleNameSet).contains(MODULE_NAME),"Did not open correct page");

            JSONArray expectedResults = (JSONArray) module.get("expected");
            for (Object expected : expectedResults) {
                String sExpected = expected.toString();
                Assert.assertTrue(ModuleFunctions.checkExpectedValue(driver, sExpected, module, sPathToModuleFile + sFileModuleJson, propUIModulesHR),
                        "Did not find correct " + sExpected.split(";")[0] + " at item " + sExpected.split(";")[1]);
            }
            if(module.get("check_email").toString().equals("true")){
                // Checking email that gets sent to the manager
                jobApplicationAdvanced.enterFields(module);
                jobApplicationAdvanced.clickSubmit(module);
                Assert.assertTrue(jobApplicationAdvanced.getSubmittedMessage(module), "Application was not submitted");

                emailContent = jobApplicationAdvanced.getMultipartEmailContent(user, password, jobApplicationSubject);
                String managerEmailContent = jobApplicationAdvanced.getContent(emailContent) + jobApplicationAdvanced.getFile(emailContent);

                Assert.assertTrue(jobApplicationAdvanced.getFirstName(module, managerEmailContent), "First name does not match");
                Assert.assertTrue(jobApplicationAdvanced.getLastName(module, managerEmailContent), "Last name does not match");
                Assert.assertTrue(jobApplicationAdvanced.getAddress(module, managerEmailContent), "Address does not match");
                Assert.assertTrue(jobApplicationAdvanced.getCity(module, managerEmailContent), "City does not match");
                Assert.assertTrue(jobApplicationAdvanced.getProvince(module, managerEmailContent), "Province does not match");
                Assert.assertTrue(jobApplicationAdvanced.getCountry(module, managerEmailContent), "Country does not match");
                Assert.assertTrue(jobApplicationAdvanced.getPostalCode(module, managerEmailContent), "Postal code does not match");
                Assert.assertTrue(jobApplicationAdvanced.getHomePhone(module, managerEmailContent), "Home phone does not match");
                Assert.assertTrue(jobApplicationAdvanced.getBusinessPhone(module, managerEmailContent), "Business phone does not match");
                Assert.assertTrue(jobApplicationAdvanced.getFax(module, managerEmailContent), "Fax does not match");
                Assert.assertTrue(jobApplicationAdvanced.getEmail(module, managerEmailContent), "Email does not match");
                Assert.assertTrue(jobApplicationAdvanced.getCoverLetterText(module, managerEmailContent), "Cover letter text does not match");
                Assert.assertTrue(jobApplicationAdvanced.getResumeText(module, managerEmailContent), "Resume text does not match");
                Assert.assertTrue(jobApplicationAdvanced.getResumeFile(module, managerEmailContent), "Resume file does not match");

                deleteMail(user,password, jobApplicationSubject);

                //Checking email that gets sent to applicant
                String applicantEmailContent = jobApplicationAdvanced.getEmailContent(user, password, autoResponseSubject);
                System.out.println(applicantEmailContent);

                Assert.assertTrue(jobApplicationAdvanced.getFirstName(module, applicantEmailContent), "First name does not match");
                Assert.assertTrue(jobApplicationAdvanced.getLastName(module, applicantEmailContent), "Last name does not match");
                Assert.assertTrue(jobApplicationAdvanced.getAddress(module, applicantEmailContent), "Address does not match");
                Assert.assertTrue(jobApplicationAdvanced.getCity(module, applicantEmailContent), "City does not match");
                Assert.assertTrue(jobApplicationAdvanced.getProvince(module, applicantEmailContent), "Province does not match");
                Assert.assertTrue(jobApplicationAdvanced.getCountry(module, applicantEmailContent), "Country does not match");
                Assert.assertTrue(jobApplicationAdvanced.getPostalCode(module, applicantEmailContent), "Postal code does not match");
                Assert.assertTrue(jobApplicationAdvanced.getHomePhone(module, applicantEmailContent), "Home phone does not match");
                Assert.assertTrue(jobApplicationAdvanced.getBusinessPhone(module, applicantEmailContent), "Business phone does not match");
                Assert.assertTrue(jobApplicationAdvanced.getFax(module, applicantEmailContent), "Fax does not match");
                Assert.assertTrue(jobApplicationAdvanced.getEmail(module, applicantEmailContent), "Email does not match");

                deleteMail(user,password, autoResponseSubject);
                
            } else{
                jobApplicationAdvanced.clickSubmit(module);
                Assert.assertTrue(jobApplicationAdvanced.getValidationSummary(module), "Validation summary is not working properly");
            }

        } finally {
            moduleBase.closeWindow();
        }
    }

    @Test(dataProvider=MODULE_DATA, priority=5, enabled=true)
    public void checkJobApplicationAdvancedLive(JSONObject module) throws InterruptedException, IOException, MessagingException {

        try {
            Assert.assertTrue(moduleBase.openModuleLiveSite(MODULE_NAME).contains(MODULE_NAME),"Did not open correct page");

            JSONArray expectedResults = (JSONArray) module.get("expected");
            for (Object expected : expectedResults) {
                String sExpected = expected.toString();
                Assert.assertTrue(ModuleFunctions.checkExpectedValue(driver, sExpected, module, sPathToModuleFile + sFileModuleJson, propUIModulesHR),
                        "Did not find correct " + sExpected.split(";")[0] + " at item " + sExpected.split(";")[1]);
            }

            if(module.get("check_email").toString().equals("true")){
                // Checking email that gets sent to the manager
                jobApplicationAdvanced.enterFields(module);
                jobApplicationAdvanced.clickSubmit(module);
                Assert.assertTrue(jobApplicationAdvanced.getSubmittedMessage(module), "Application was not submitted");

                emailContent = jobApplicationAdvanced.getMultipartEmailContent(user, password, jobApplicationSubject);
                String managerEmailContent = jobApplicationAdvanced.getContent(emailContent) + jobApplicationAdvanced.getFile(emailContent);

                Assert.assertTrue(jobApplicationAdvanced.getFirstName(module, managerEmailContent), "First name does not match");
                Assert.assertTrue(jobApplicationAdvanced.getLastName(module, managerEmailContent), "Last name does not match");
                Assert.assertTrue(jobApplicationAdvanced.getAddress(module, managerEmailContent), "Address does not match");
                Assert.assertTrue(jobApplicationAdvanced.getCity(module, managerEmailContent), "City does not match");
                Assert.assertTrue(jobApplicationAdvanced.getProvince(module, managerEmailContent), "Province does not match");
                Assert.assertTrue(jobApplicationAdvanced.getCountry(module, managerEmailContent), "Country does not match");
                Assert.assertTrue(jobApplicationAdvanced.getPostalCode(module, managerEmailContent), "Postal code does not match");
                Assert.assertTrue(jobApplicationAdvanced.getHomePhone(module, managerEmailContent), "Home phone does not match");
                Assert.assertTrue(jobApplicationAdvanced.getBusinessPhone(module, managerEmailContent), "Business phone does not match");
                Assert.assertTrue(jobApplicationAdvanced.getFax(module, managerEmailContent), "Fax does not match");
                Assert.assertTrue(jobApplicationAdvanced.getEmail(module, managerEmailContent), "Email does not match");
                Assert.assertTrue(jobApplicationAdvanced.getCoverLetterText(module, managerEmailContent), "Cover letter text does not match");
                Assert.assertTrue(jobApplicationAdvanced.getResumeText(module, managerEmailContent), "Resume text does not match");
                Assert.assertTrue(jobApplicationAdvanced.getResumeFile(module, managerEmailContent), "Resume file does not match");

                deleteMail(user,password, jobApplicationSubject);

                //Checking email that gets sent to applicant
                String applicantEmailContent = jobApplicationAdvanced.getEmailContent(user, password, autoResponseSubject);
                System.out.println(applicantEmailContent);

                Assert.assertTrue(jobApplicationAdvanced.getFirstName(module, applicantEmailContent), "First name does not match");
                Assert.assertTrue(jobApplicationAdvanced.getLastName(module, applicantEmailContent), "Last name does not match");
                Assert.assertTrue(jobApplicationAdvanced.getAddress(module, applicantEmailContent), "Address does not match");
                Assert.assertTrue(jobApplicationAdvanced.getCity(module, applicantEmailContent), "City does not match");
                Assert.assertTrue(jobApplicationAdvanced.getProvince(module, applicantEmailContent), "Province does not match");
                Assert.assertTrue(jobApplicationAdvanced.getCountry(module, applicantEmailContent), "Country does not match");
                Assert.assertTrue(jobApplicationAdvanced.getPostalCode(module, applicantEmailContent), "Postal code does not match");
                Assert.assertTrue(jobApplicationAdvanced.getHomePhone(module, applicantEmailContent), "Home phone does not match");
                Assert.assertTrue(jobApplicationAdvanced.getBusinessPhone(module, applicantEmailContent), "Business phone does not match");
                Assert.assertTrue(jobApplicationAdvanced.getFax(module, applicantEmailContent), "Fax does not match");
                Assert.assertTrue(jobApplicationAdvanced.getEmail(module, applicantEmailContent), "Email does not match");

                deleteMail(user,password, autoResponseSubject);

            } else{
                jobApplicationAdvanced.clickSubmit(module);
                Assert.assertTrue(jobApplicationAdvanced.getValidationSummary(module), "Validation summary is not working properly");
            }
        } finally {
            moduleBase.closeWindow();
        }
    }

    @Test(dataProvider=MODULE_DATA, priority=6, enabled=true)
    public void removeJobApplicationAdvancedModule(JSONObject module) throws Exception {
        String sModuleNameSet = module.get("module_title").toString();
        Assert.assertEquals(moduleBase.setupAsDeletedModule(sModuleNameSet), WorkflowState.DELETE_PENDING.state(), "New "+sModuleNameSet+" Module didn't setup as Deleted properly");
        Assert.assertEquals(moduleBase.removeModule(module, sModuleNameSet), WorkflowState.NEW_ITEM.state(), "Couldn't remove "+sModuleNameSet+" Module. Something went wrong.");
    }

    @Test(dataProvider=PAGE_DATA, priority=7, enabled=true)
    public void removeJobApplicationAdvancedPage(JSONObject module) throws Exception {
        Assert.assertEquals(pageForModules.setupAsDeletedPage(MODULE_NAME), WorkflowState.DELETE_PENDING.state(), "New "+MODULE_NAME+" Page didn't setup as Deleted properly");
        Assert.assertEquals(pageForModules.removePage(module, MODULE_NAME), WorkflowState.NEW_ITEM.state(), "Couldn't remove "+MODULE_NAME+" Page. Something went wrong.");
    }

    private Object[][] genericProvider(String dataType) {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sDataFileJson));
            JSONArray pageData = (JSONArray) jsonObject.get(dataType);
            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < pageData.size(); i++) {
                JSONObject pageObj = (JSONObject) pageData.get(i);
                if (Boolean.parseBoolean(pageObj.get("do_assertions").toString())) {
                    zoom.add(pageData.get(i));
                }
            }

            Object[][] newPages = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                newPages[i][0] = zoom.get(i);
            }

            return newPages;

        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @DataProvider
    public Object[][] moduleData() {
        return genericProvider(MODULE_NAME);
    }

    @DataProvider
    public Object[][] pageData() {
        return genericProvider(PAGE_NAME);
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }
}
