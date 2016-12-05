package specs.EmailAdmin.Compose;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.EmailAdmin.Compose;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;


/**
 * Created by philipsushkov on 2016-12-05.
 */

public class CheckCompose extends AbstractSpec {

    @Before
    public void setUp() throws Exception {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void checkCompose() throws Exception {
        final String expectedTitle = "Mailing List Messages Admin";

        Assert.assertNotNull(new Dashboard(driver).openCompose().getUrl());
        Assert.assertEquals("Actual Mailing List Messages Admin page Title doesn't match to expected", expectedTitle, new Compose(driver).getTitle());

        //System.out.println(new DepartmentList(driver).getTitleQuantity().toString());
        Assert.assertNotNull("Template drop down list doesn't exist", new Compose(driver).getTemplateList() );
        Assert.assertNotNull("To drop down list doesn't exist", new Compose(driver).getToList() );
        Assert.assertNotNull("From field doesn't exist", new Compose(driver).getFromField() );
        Assert.assertNotNull("Subject field doesn't exist", new Compose(driver).getSubjectField() );
        Assert.assertNotNull("Body textarea doesn't exist", new Compose(driver).getBodyTextArea() );
        Assert.assertNotNull("Created By field doesn't exist", new Compose(driver).getCreatedByField() );
        Assert.assertNotNull("Send Test Email button doesn't exist", new Compose(driver).getSendTestEmailButton() );
        Assert.assertNotNull("Save button doesn't exist", new Compose(driver).getSaveButton() );

    }

    @After
    public void tearDown() {
        //driver.quit();
    }

}