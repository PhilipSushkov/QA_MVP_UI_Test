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

    @Before
    public void setUp() throws Exception {
        systemAdminMenuButton = By.xpath(propUISystemAdmin.getProperty("btnMenu_SystemAdmin"));
        pdfTemplateEditMenuItem = By.xpath(propUISystemAdmin.getProperty("itemMenu_PDFTemplateEdit"));

        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkPDFTemplateEdit() throws Exception {
        final String expectedTitle = "PDF Template Edit";

        new Dashboard(driver).openPageFromMenu(systemAdminMenuButton, pdfTemplateEditMenuItem);

        Assert.assertNotNull(new PDFTemplateEdit(driver).getUrl());

        Assert.assertEquals("Actual PDF Template Edit page Title doesn't match to expected", expectedTitle, new PDFTemplateEdit(driver).getTitle());

        //System.out.println(new PDFTemplateEdit(driver).getHeaderRadEditor().getSize().toString() );
        Assert.assertNotNull("Header Rad Editor doesn't exist", new PDFTemplateEdit(driver).getHeaderRadEditor() );
        Assert.assertNotNull("Body Rad Editor doesn't exist", new PDFTemplateEdit(driver).getBodyRadEditor() );
        Assert.assertNotNull("Footer Rad Editor doesn't exist", new PDFTemplateEdit(driver).getFooterRadEditor() );

    }

    @After
    public void tearDown() {
        new Dashboard(driver).logout();
        //driver.quit();
    }

}
