package specs.SiteAdmin.GlobalModuleList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.openqa.selenium.By;
import pageobjects.SiteAdmin.GlobalModuleList.GlobalModuleList;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-11-15.
 */

public class CheckGlobalModuleList extends AbstractSpec {
    private static By siteAdminMenuButton, globalModuleListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static GlobalModuleList globalModuleList;

    @Before
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        globalModuleListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_GlobalModuleList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        globalModuleList = new GlobalModuleList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkGlobalModuleList() throws Exception {
        final String expectedTitle = "Global Module List";
        final Integer expectedQuantity = 10;

        dashboard.openPageFromMenu(siteAdminMenuButton, globalModuleListMenuItem);

        Assert.assertNotNull(globalModuleList.getUrl());
        Assert.assertEquals("Actual Global Module List page Title doesn't match to expected", expectedTitle, globalModuleList.getTitle());

        //System.out.println(new GlobalModuleList(driver).getGlobalModuleListQuantity().toString());
        Assert.assertTrue("Actual Module Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= globalModuleList.getGlobalModuleListQuantity() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
