package specs.SystemAdmin.WorkflowEmailList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SystemAdmin.WorkflowEmailList.WorkflowEmailEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-12-19.
 */
public class CheckWorkflowEmailEdit extends AbstractSpec {

    private static By systemAdminMenuButton, workflowEmailListMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static WorkflowEmailEdit workflowEmailEdit;

    @BeforeTest
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        workflowEmailListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_WorkflowEmailList"));
        userAddNewLink = By.xpath(propUISystemAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        workflowEmailEdit = new WorkflowEmailEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(systemAdminMenuButton, workflowEmailListMenuItem, userAddNewLink);
    }

    @Test
    public void checkWorkflowEmailEdit() throws Exception {
        final String expectedTitle = "Workflow Email Edit";

        Assert.assertNotNull(workflowEmailEdit.getUrl());
        Assert.assertEquals(workflowEmailEdit.getTitle(), expectedTitle, "Actual Workflow Email Edit page Title doesn't match to expected");

        Assert.assertNotNull(workflowEmailEdit.getDescriptionInput(), "Description field doesn't exist");
        Assert.assertNotNull(workflowEmailEdit.getSystemTaskSelect(), "System Task Select doesn't exist");
        Assert.assertNotNull(workflowEmailEdit.getSystemMessageSelect(), "System Message Select doesn't exist");
        Assert.assertNotNull(workflowEmailEdit.getSaveButton(), "Save Button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
