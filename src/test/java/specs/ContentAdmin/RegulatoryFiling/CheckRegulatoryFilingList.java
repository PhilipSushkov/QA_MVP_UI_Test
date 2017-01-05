package specs.ContentAdmin.RegulatoryFiling;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.RegulatoryFiling.RegulatoryFilingList;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2017-01-05.
 */

public class CheckRegulatoryFilingList extends AbstractSpec {
    private static By contentAdminMenuButton, regulatoryFilingListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static RegulatoryFilingList regulatoryFilingList;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        regulatoryFilingListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_RegulatoryFiling"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        regulatoryFilingList = new RegulatoryFilingList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkRegulatoryFilingList() throws Exception {
        final String expectedTitle = "Regulatory Filing List";
        final Integer expectedQuantity = 0;

        dashboard.openPageFromMenu(contentAdminMenuButton, regulatoryFilingListMenuItem);

        Assert.assertNotNull(regulatoryFilingList.getUrl());
        Assert.assertEquals(regulatoryFilingList.getTitle(), expectedTitle, "Actual Regulatory Filing List page Title doesn't match to expected");

        //System.out.println(glossaryList.getTitleQuantity().toString());
        Assert.assertTrue(expectedQuantity <= regulatoryFilingList.getTitleQuantity(), "Actual Regulatory Filing Quantity is less than expected: "+expectedQuantity);
        Assert.assertNotNull(regulatoryFilingList.getRegulatoryFilingListPagination(), "Regulatory Filing List Pagination doesn't exist");
        //Assert.assertNotNull(quickLinks.getFilterByTag(), "Filter By Tag field doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
