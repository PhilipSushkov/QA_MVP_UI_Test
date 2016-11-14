package specs.SystemAdmin.PDFTemplateEdit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.SystemAdmin.PDFTemplateEdit.PDFTemplateEdit;

/**
 * Created by philipsushkov on 2016-11-14.
 */

public class CheckPDFTemplateEdit extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkPDFTemplateEdit() throws Exception {
        final String expectedTitle = "PDF Template Edit";

        Assert.assertNotNull(new Dashboard(driver).openPDFTemplateEditPage().getUrl());

        Assert.assertEquals("Actual PDF Template Edit page Title doesn't match to expected", expectedTitle, new PDFTemplateEdit(driver).getTitle());

        //System.out.println(new PDFTemplateEdit(driver).getHeaderRadEditor().getSize().toString() );
        Assert.assertNotNull(new PDFTemplateEdit(driver).getHeaderRadEditor() );
        Assert.assertNotNull(new PDFTemplateEdit(driver).getBodyRadEditor() );
        Assert.assertNotNull(new PDFTemplateEdit(driver).getFooterRadEditor() );

    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
