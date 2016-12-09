package specs.SystemAdmin.WorkflowEmailList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.openqa.selenium.By;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.SystemAdmin.WorkflowEmailList.WorkflowEmailList;

/**
 * Created by philipsushkov on 2016-11-11.
 */

public class CheckWorkflowEmailList extends AbstractSpec {
    private static By systemAdminMenuButton, workflowEmailListMenuItem;

    @Before
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        workflowEmailListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_WorkflowEmailList"));

        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkWorkflowEmailList() throws Exception {
        final String expectedTitle = "Workflow Email List";
        final Integer expectedQuantity = 4;

        new Dashboard(driver).openPageFromMenu(systemAdminMenuButton, workflowEmailListMenuItem);

        Assert.assertNotNull(new WorkflowEmailList(driver).getUrl());
        Assert.assertEquals("Actual Workflow Email List page Title doesn't match to expected", expectedTitle, new WorkflowEmailList(driver).getTitle());

        //System.out.println(new WorkflowEmailList(driver).getDescriptionQuantity().toString());
        Assert.assertTrue("Actual Description Quantity is less than expected: "+expectedQuantity, expectedQuantity < new WorkflowEmailList(driver).getDescriptionQuantity() );
    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
