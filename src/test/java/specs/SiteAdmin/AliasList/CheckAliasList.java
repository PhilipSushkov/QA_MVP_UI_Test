package specs.SiteAdmin.AliasList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.AliasList.AliasList;
import specs.AbstractSpec;

/**
 * Created by philipsushkov on 2016-11-21.
 */

public class CheckAliasList extends AbstractSpec {
    private static By siteAdminMenuButton, aliasListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static AliasList aliasList;

    @Before
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        aliasListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_AliasList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        aliasList = new AliasList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkAliasList() throws Exception {
        final String expectedTitle = "Alias List";
        final Integer expectedQuantity = 4;

        dashboard.openPageFromMenu(siteAdminMenuButton, aliasListMenuItem);

        Assert.assertNotNull(aliasList.getUrl());
        Assert.assertEquals("Actual Alias List page Title doesn't match to expected", expectedTitle, aliasList.getTitle());

        //System.out.println(new AliasList(driver).getAliasListQuantity().toString());
        Assert.assertTrue("Actual Alias Quantity is less than expected: "+expectedQuantity, expectedQuantity == aliasList.getAliasListQuantity() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
