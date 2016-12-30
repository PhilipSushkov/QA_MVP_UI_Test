package specs.SiteAdmin.DomainList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.SiteAdmin.DomainList.DomainEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2016-12-30.
 */

public class CheckDomainEdit extends AbstractSpec {

    private static By siteAdminMenuButton, domainListMenuItem, userAddNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static DomainEdit domainEdit;

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        domainListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_DomainList"));
        userAddNewLink = By.xpath(propUISystemAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        domainEdit = new DomainEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(siteAdminMenuButton, domainListMenuItem, userAddNewLink);
    }

    @Test
    public void checkDomainEdit() throws Exception {
        final String expectedTitle = "Domain Edit";

        Assert.assertNotNull(domainEdit.getUrl());
        Assert.assertEquals(domainEdit.getTitle(), expectedTitle, "Actual Domain Edit page Title doesn't match to expected");

        Assert.assertNotNull(domainEdit.getDomainNameInput(), "Domain Name field doesn't exist");
        Assert.assertNotNull(domainEdit.getLandingPageSelect(), "Landing Page select doesn't exist");
        Assert.assertNotNull(domainEdit.getDomainNameAltInput(), "Domain Name Alternative field doesn't exist");
        Assert.assertNotNull(domainEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
