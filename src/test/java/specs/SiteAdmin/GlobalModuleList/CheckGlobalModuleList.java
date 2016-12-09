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

    @Before
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        globalModuleListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_GlobalModuleList"));
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkGlobalModuleList() throws Exception {
        final String expectedTitle = "Global Module List";
        final Integer expectedQuantity = 10;

        new Dashboard(driver).openPageFromMenu(siteAdminMenuButton, globalModuleListMenuItem);

        Assert.assertNotNull(new GlobalModuleList(driver).getUrl());
        Assert.assertEquals("Actual Global Module List page Title doesn't match to expected", expectedTitle, new GlobalModuleList(driver).getTitle());

        //System.out.println(new GlobalModuleList(driver).getGlobalModuleListQuantity().toString());
        Assert.assertTrue("Actual Module Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new GlobalModuleList(driver).getGlobalModuleListQuantity() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
