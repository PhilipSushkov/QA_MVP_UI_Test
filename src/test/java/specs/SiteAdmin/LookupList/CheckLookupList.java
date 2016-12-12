package specs.SiteAdmin.LookupList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.LookupList.LookupList;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-21.
 */

public class CheckLookupList extends AbstractSpec {
    private static By siteAdminMenuButton, lookupListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static LookupList lookupList;

    @Before
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        lookupListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_LookupList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        lookupList = new LookupList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkLookupList() throws Exception {
        final String expectedTitle = "Lookup List";
        final Integer expectedQuantity = 150;

        dashboard.openPageFromMenu(siteAdminMenuButton, lookupListMenuItem);

        Assert.assertNotNull(lookupList.getUrl());
        Assert.assertEquals("Actual Lookup List page Title doesn't match to expected", expectedTitle, lookupList.getTitle());

        Assert.assertNotNull("Lookup Type dropdown list doesn't exist", lookupList.getLookupListLookupType() );
        //System.out.println(new LookupList(driver).getLookupListQuantity().toString());
        Assert.assertTrue("Actual Lookup Text Quantity is less than expected: "+expectedQuantity, expectedQuantity <= lookupList.getLookupListQuantity() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
