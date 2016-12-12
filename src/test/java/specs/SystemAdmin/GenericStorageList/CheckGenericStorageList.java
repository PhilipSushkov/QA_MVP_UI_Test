package specs.SystemAdmin.GenericStorageList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.openqa.selenium.By;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.SystemAdmin.GenericStorageList.GenericStorageList;

/**
 * Created by philipsushkov on 2016-11-11.
 */
public class CheckGenericStorageList extends AbstractSpec {
    private static By systemAdminMenuButton, genericStorageListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static GenericStorageList genericStorageList;


    @Before
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        genericStorageListMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_GenericStorageList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        genericStorageList = new GenericStorageList(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkGenericStorageList() throws Exception {
        final String expectedTitle = "Generic Storage List";
        final Integer expectedSize = 1;

        dashboard.openPageFromMenu(systemAdminMenuButton, genericStorageListMenuItem);

        Assert.assertNotNull(genericStorageList.getUrl());
        Assert.assertEquals("Actual Generic Storage List page Title doesn't match to expected", expectedTitle, genericStorageList.getTitle());

        //System.out.println(new GenericStorageList(driver).getStorageHeaderSize().toString());
        Assert.assertTrue("Actual Storage Header Size is less than expected: "+expectedSize+" or doesn't exist", expectedSize == genericStorageList.getStorageHeaderSize() );
    }

    @After
    public void tearDown() {
        dashboard.logout();
        //driver.quit();
    }

}
