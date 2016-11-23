package specs.ContentAdmin.FinancialReports;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.ContentAdmin.FinancialReports.FinancialReports;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-23.
 */

public class CheckFinancialReports extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkFinancialReports() throws Exception {
        final String expectedTitle = "Financial Report List";
        final Integer expectedQuantity = 6;

        Assert.assertNotNull(new Dashboard(driver).openFinancialReports().getUrl());
        Assert.assertEquals("Actual Financial Reports page Title doesn't match to expected", expectedTitle, new FinancialReports(driver).getTitle());

        //System.out.println(new FinancialReports(driver).getTitleQuantity().toString());
        Assert.assertTrue("Actual Financial Reports Title Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new FinancialReports(driver).getTitleQuantity() );
        Assert.assertNotNull("Financial Reports Pagination doesn't exist", new FinancialReports(driver).getFinancialReportPagination() );
        Assert.assertNotNull("Filter By Tag field doesn't exist", new FinancialReports(driver).getFilterByTag() );

    }

    @After
    public void tearDown() {
        //driver.quit();
    }

}
