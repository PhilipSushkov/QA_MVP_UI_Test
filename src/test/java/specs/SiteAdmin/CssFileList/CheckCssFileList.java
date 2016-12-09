package specs.SiteAdmin.CssFileList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.CssFileList.CssFileList;

/**
 * Created by philipsushkov on 2016-11-18.
 */

public class CheckCssFileList extends AbstractSpec {
    private static By siteAdminMenuButton, cssFileListMenuItem;

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();

        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        cssFileListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_CssFileList"));
    }

    @Test
    public void checkCssFileList() throws Exception {

        final String expectedTitle = "Css File List";
        final Integer expectedQuantity = 2;

        new Dashboard(driver).openPageFromMenu(siteAdminMenuButton, cssFileListMenuItem);

        Assert.assertNotNull(new CssFileList(driver).getUrl());
        Assert.assertEquals("Actual Css File List page Title doesn't match to expected", expectedTitle, new CssFileList(driver).getTitle());

        //System.out.println(new CssFileList(driver).getCssFileListQuantity().toString());
        Assert.assertTrue("Actual Css Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= new CssFileList(driver).getCssFileListQuantity() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
