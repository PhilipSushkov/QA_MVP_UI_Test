package specs.SystemAdmin.WorkflowEmailList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

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

    @BeforeTest
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
        Assert.assertEquals(workflowEmailList.getTitle(), expectedTitle, "Actual Workflow Email List page Title doesn't match to expected");

        //System.out.println(new WorkflowEmailList(driver).getDescriptionQuantity().toString());
        Assert.assertTrue(expectedQuantity < workflowEmailList.getDescriptionQuantity(), "Actual Description Quantity is less than expected: "+expectedQuantity );
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
