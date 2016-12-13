package specs.ContentAdmin.FinancialReports;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.ContentAdmin.DepartmentList.DepartmentList;
import pageobjects.ContentAdmin.FinancialReports.FinancialReports;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-23.
 */

public class CheckFinancialReports extends AbstractSpec {
    private static By contentAdminMenuButton, financialReportsMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static FinancialReports financialReports;

    @Before
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        financialReportsMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_FinancialReports"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        financialReports = new FinancialReports(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkFinancialReports() throws Exception {
        final String expectedTitle = "Financial Report List";
        final Integer expectedQuantity = 6;

        dashboard.openPageFromMenu(contentAdminMenuButton, financialReportsMenuItem);

        Assert.assertNotNull(financialReports.getUrl());
        Assert.assertEquals("Actual Financial Reports page Title doesn't match to expected", expectedTitle, financialReports.getTitle());

        //System.out.println(new FinancialReports(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual Financial Reports Title Quantity is less than expected: "+expectedQuantity, expectedQuantity <= financialReports.getTitleQuantity() );
        Assert.assertNotNull("Financial Reports Pagination doesn't exist", financialReports.getFinancialReportPagination() );
        Assert.assertNotNull("Filter By Tag field doesn't exist", financialReports.getFilterByTag() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
