package specs.SystemAdmin.PDFTemplateEdit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.openqa.selenium.By;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.SystemAdmin.PDFTemplateEdit.PDFTemplateEdit;

/**
 * Created by philipsushkov on 2016-11-14.
 */

public class CheckPDFTemplateEdit extends AbstractSpec {
    private static By systemAdminMenuButton, pdfTemplateEditMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static PDFTemplateEdit pdfTemplateEdit;

    @Before
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        pdfTemplateEditMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_PDFTemplateEdit"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        pdfTemplateEdit = new PDFTemplateEdit(driver);

        loginPage.loginUser();
    }

    @Test
    public void checkPDFTemplateEdit() throws Exception {
        final String expectedTitle = "PDF Template Edit";

        dashboard.openPageFromMenu(systemAdminMenuButton, pdfTemplateEditMenuItem);

        Assert.assertNotNull(pdfTemplateEdit.getUrl());

        Assert.assertEquals("Actual PDF Template Edit page Title doesn't match to expected", expectedTitle, pdfTemplateEdit.getTitle());

        //System.out.println(new PDFTemplateEdit(driver).getHeaderRadEditor().getSize().toString() );
        Assert.assertNotNull("Header Rad Editor doesn't exist", pdfTemplateEdit.getHeaderRadEditor() );
        Assert.assertNotNull("Body Rad Editor doesn't exist", pdfTemplateEdit.getBodyRadEditor() );
        Assert.assertNotNull("Footer Rad Editor doesn't exist", pdfTemplateEdit.getFooterRadEditor() );

    }

    @After
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
