package specs.ContentAdmin.RegulatoryFiling;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.RegulatoryFiling.RegulatoryFilingEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-05.
 */

public class CheckRegulatoryFilingEdit extends AbstractSpec {

    private static By contentAdminMenuButton, regulatoryFilingListMenuItem, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static RegulatoryFilingEdit regulatoryFilingEdit;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        regulatoryFilingListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_RegulatoryFiling"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        regulatoryFilingEdit = new RegulatoryFilingEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(contentAdminMenuButton, regulatoryFilingListMenuItem, addNewLink);
    }

    @Test
    public void checkRegulatoryFilingEdit() throws Exception {
        final String expectedTitle = "Regulatory Filing Edit";

        Assert.assertNotNull(regulatoryFilingEdit.getUrl());
        Assert.assertEquals(regulatoryFilingEdit.getTitle(), expectedTitle, "Actual Regulatory Filing Edit page Title doesn't match to expected");

        Assert.assertNotNull(regulatoryFilingEdit.getDateInput(), "Date field doesn't exist");
        Assert.assertNotNull(regulatoryFilingEdit.getFormInput(), "Form field doesn't exist");
        Assert.assertNotNull(regulatoryFilingEdit.getFormDescInput(), "Form Desc field doesn't exist");
        Assert.assertNotNull(regulatoryFilingEdit.getIssuerInput(), "Issuer field doesn't exist");
        Assert.assertNotNull(regulatoryFilingEdit.getFormGroupInput(), "Form Group field doesn't exist");
        Assert.assertNotNull(regulatoryFilingEdit.getSizeInput(), "Size field doesn't exist");
        Assert.assertNotNull(regulatoryFilingEdit.getPagesInput(), "Pages field doesn't exist");

        Assert.assertTrue(regulatoryFilingEdit.getLinkInputSet(), "Link Input elements don't exist");

        Assert.assertNotNull(regulatoryFilingEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
