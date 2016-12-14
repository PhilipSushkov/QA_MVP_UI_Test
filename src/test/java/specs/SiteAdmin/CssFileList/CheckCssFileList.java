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
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static CssFileList cssFileList;

    @Before
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        cssFileListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_CssFileList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        cssFileList = new CssFileList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkCssFileList() throws Exception {

        final String expectedTitle = "Css File List";
        final Integer expectedQuantity = 2;

        dashboard.openPageFromMenu(siteAdminMenuButton, cssFileListMenuItem);

        Assert.assertNotNull(cssFileList.getUrl());
        Assert.assertEquals("Actual Css File List page Title doesn't match to expected", expectedTitle, cssFileList.getTitle());

        //System.out.println(new CssFileList(driver).getCssFileListQuantity().toString());
        Assert.assertTrue("Actual Css Name Quantity is less than expected: "+expectedQuantity, expectedQuantity <= cssFileList.getCssFileListQuantity() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
