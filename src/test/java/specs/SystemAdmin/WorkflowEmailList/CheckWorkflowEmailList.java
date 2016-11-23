package specs.SystemAdmin.WorkflowEmailList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.SystemAdmin.WorkflowEmailList.WorkflowEmailList;

/**
 * Created by philipsushkov on 2016-11-11.
 */

public class CheckWorkflowEmailList extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkWorkflowEmailList() throws Exception {
        final String expectedTitle = "Workflow Email List";
        final Integer expectedQuantity = 4;

        Assert.assertNotNull(new Dashboard(driver).openWorkflowEmailListPage().getUrl());
        Assert.assertEquals("Actual Workflow Email List page Title doesn't match to expected", expectedTitle, new WorkflowEmailList(driver).getTitle());

        //System.out.println(new WorkflowEmailList(driver).getDescriptionQuantity().toString());
        Assert.assertTrue("Actual Description Quantity is less than expected: "+expectedQuantity, expectedQuantity < new WorkflowEmailList(driver).getDescriptionQuantity() );
    }

    @After
    public void tearDown() {
        //driver.quit();
    }

}
