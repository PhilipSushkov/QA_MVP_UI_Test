package specs.ContentAdmin.FinancialReports;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.FinancialReports.FinancialReportEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-03.
 */

public class CheckFinancialReportEdit extends AbstractSpec {

    private static By contentAdminMenuButton, financialReportsMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static FinancialReportEdit financialReportEdit;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        financialReportsMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_FinancialReports"));
        userAddNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        financialReportEdit = new FinancialReportEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(contentAdminMenuButton, financialReportsMenuItem, userAddNewLink);
    }

    @Test
    public void checkFinancialReportEdit() throws Exception {
        final String expectedTitle = "Financial Report Edit";

        Assert.assertNotNull(financialReportEdit.getUrl());
        Assert.assertEquals(financialReportEdit.getTitle(), expectedTitle, "Actual Financial Report Edit page Title doesn't match to expected");

        Assert.assertNotNull(financialReportEdit.getReportYearSelect(), "Report Year select doesn't exist");
        Assert.assertNotNull(financialReportEdit.getReportTypeSelect(), "Report Type select doesn't exist");
        Assert.assertNotNull(financialReportEdit.getCoverImageInput(), "Cover Image field doesn't exist");
        Assert.assertNotNull(financialReportEdit.getFilingDateInput(), "Filing Date field doesn't exist");
        Assert.assertNotNull(financialReportEdit.getTagsInput(), "Tags field doesn't exist");
        Assert.assertNotNull(financialReportEdit.getActiveCheckbox(), "Active checkbox doesn't exist");

        Assert.assertNotNull(financialReportEdit.getAddNewRelatedDocLink(), "Add New Related Document link doesn't exist");
        Assert.assertNotNull(financialReportEdit.getDocumentsTable(), "Documents table doesn't exist");
        Assert.assertNotNull(financialReportEdit.getSaveOrderImage(), "Save Order image doesn't exist");

        Assert.assertNotNull(financialReportEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
