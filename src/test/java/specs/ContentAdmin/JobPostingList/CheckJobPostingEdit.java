package specs.ContentAdmin.JobPostingList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.JobPostingList.JobPostingEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-06.
 */

public class CheckJobPostingEdit extends AbstractSpec {

    private static By contentAdminMenuButton, jobPostingMenuItem, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static JobPostingEdit jobPostingEdit;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        jobPostingMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_JobPosting"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        jobPostingEdit = new JobPostingEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(contentAdminMenuButton, jobPostingMenuItem, addNewLink);
    }

    @Test
    public void checkJobPostingEdit() throws Exception {
        final String expectedTitle = "Job Posting Edit (Advanced)";

        Assert.assertNotNull(jobPostingEdit.getUrl());
        Assert.assertEquals(jobPostingEdit.getTitle(), expectedTitle, "Actual Job Posting Edit page Title doesn't match to expected");

        Assert.assertNotNull(jobPostingEdit.getRegionSelect(), "Region select doesn't exist");
        Assert.assertNotNull(jobPostingEdit.getCountrySelect(), "Country select doesn't exist");
        Assert.assertNotNull(jobPostingEdit.getLocationInput(), "Location field doesn't exist");
        Assert.assertNotNull(jobPostingEdit.getDivisionSelect(), "Division select doesn't exist");
        Assert.assertNotNull(jobPostingEdit.getJobTitleInput(), "Job Title field doesn't exist");
        Assert.assertNotNull(jobPostingEdit.getJobTypeSelect(), "Job Type select doesn't exist");
        Assert.assertNotNull(jobPostingEdit.getJobFunctionInput(), "Job Function field doesn't exist");
        Assert.assertNotNull(jobPostingEdit.getRefNoInput(), "Ref No field doesn't exist");
        Assert.assertNotNull(jobPostingEdit.getManagerEmailInput(), "Manager's Email field doesn't exist");

        Assert.assertNotNull(jobPostingEdit.getOpeningDateInput(), "Opening Date field doesn't exist");
        Assert.assertNotNull(jobPostingEdit.getClosingDateInput(), "Closing Date field doesn't exist");
        Assert.assertNotNull(jobPostingEdit.getSummaryFrame(), "Summary frame doesn't exist");
        Assert.assertNotNull(jobPostingEdit.getDocumentPathInput(), "Document Path field doesn't exist");

        Assert.assertNotNull(jobPostingEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
