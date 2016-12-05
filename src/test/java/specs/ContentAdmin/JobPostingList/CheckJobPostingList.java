package specs.ContentAdmin.JobPostingList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.ContentAdmin.FaqList.FaqList;
import pageobjects.ContentAdmin.JobPostingList.JobPostingList;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-12-05.
 */

public class CheckJobPostingList extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkJobPostingList() throws Exception {
        final String expectedTitle = "Job Posting List";
        final Integer expectedQuantity = 3;

        Assert.assertNotNull(new Dashboard(driver).openJobPostingList().getUrl());
        Assert.assertEquals("Actual Job Posting List page Title doesn't match to expected", expectedTitle, new JobPostingList(driver).getTitle());

        //System.out.println(new JobPostingList(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual Job Posting Title Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new JobPostingList(driver).getTitleQuantity() );

    }

    @After
    public void tearDown() {
        //driver.quit();
    }

}
