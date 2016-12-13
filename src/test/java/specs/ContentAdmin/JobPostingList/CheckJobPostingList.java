package specs.ContentAdmin.JobPostingList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.ContentAdmin.FaqList.FaqList;
import pageobjects.ContentAdmin.JobPostingList.JobPostingList;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-12-05.
 */

public class CheckJobPostingList extends AbstractSpec {
    private static By contentAdminMenuButton, jobPostingMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static JobPostingList jobPostingList;

    @Before
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        jobPostingMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_JobPosting"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        jobPostingList = new JobPostingList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkJobPostingList() throws Exception {
        final String expectedTitle = "Job Posting List";
        final Integer expectedQuantity = 3;

        dashboard.openPageFromMenu(contentAdminMenuButton, jobPostingMenuItem);

        Assert.assertNotNull(jobPostingList.getUrl());
        Assert.assertEquals("Actual Job Posting List page Title doesn't match to expected", expectedTitle, jobPostingList.getTitle());

        //System.out.println(new JobPostingList(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual Job Posting Title Quantity is less than expected: "+expectedQuantity, expectedQuantity <= jobPostingList.getTitleQuantity() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
