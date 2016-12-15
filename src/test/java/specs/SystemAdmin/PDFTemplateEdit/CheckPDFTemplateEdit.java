package specs.SystemAdmin.PDFTemplateEdit;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

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

    @BeforeTest
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

        Assert.assertEquals(pdfTemplateEdit.getTitle(), expectedTitle, "Actual PDF Template Edit page Title doesn't match to expected");

        //System.out.println(new PDFTemplateEdit(driver).getHeaderRadEditor().getSize().toString() );
        Assert.assertNotNull(pdfTemplateEdit.getHeaderRadEditor(), "Header Rad Editor doesn't exist");
        Assert.assertNotNull(pdfTemplateEdit.getBodyRadEditor(), "Body Rad Editor doesn't exist");
        Assert.assertNotNull(pdfTemplateEdit.getFooterRadEditor(), "Footer Rad Editor doesn't exist");

    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
