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
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static WorkflowEmailList workflowEmailList;

    @Before
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        workflowEmailListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_WorkflowEmailList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        workflowEmailList = new WorkflowEmailList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkWorkflowEmailList() throws Exception {
        final String expectedTitle = "Workflow Email List";
        final Integer expectedQuantity = 4;

        dashboard.openPageFromMenu(systemAdminMenuButton, workflowEmailListMenuItem);

        Assert.assertNotNull(workflowEmailList.getUrl());
        Assert.assertEquals("Actual Workflow Email List page Title doesn't match to expected", expectedTitle, workflowEmailList.getTitle());

        //System.out.println(new WorkflowEmailList(driver).getDescriptionQuantity().toString());
        Assert.assertTrue("Actual Description Quantity is less than expected: "+expectedQuantity, expectedQuantity < workflowEmailList.getDescriptionQuantity() );
    }

    @After
    public void tearDown() {
        dashboard.logout();
        //driver.quit();
    }

}
