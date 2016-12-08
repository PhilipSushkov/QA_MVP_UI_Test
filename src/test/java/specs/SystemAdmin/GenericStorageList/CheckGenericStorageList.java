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
    final By systemAdminMenuButton = By.xpath("//span[contains(text(),'System Admin')]");
    final By genericStorageListMenuItem = By.xpath("//a[contains(text(),'Generic Storage List')]/parent::li");

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkGenericStorageList() throws Exception {
        final String expectedTitle = "Generic Storage List";
        final Integer expectedSize = 1;

        new Dashboard(driver).openPageFromMenu(systemAdminMenuButton, genericStorageListMenuItem);

        Assert.assertNotNull(new GenericStorageList(driver).getUrl());
        Assert.assertEquals("Actual Generic Storage List page Title doesn't match to expected", expectedTitle, new GenericStorageList(driver).getTitle());

        System.out.println(new GenericStorageList(driver).getStorageHeaderSize().toString());
        Assert.assertTrue("Actual Storage Header Size is less than expected: "+expectedSize+" or doesn't exist", expectedSize == new GenericStorageList(driver).getStorageHeaderSize() );
    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
