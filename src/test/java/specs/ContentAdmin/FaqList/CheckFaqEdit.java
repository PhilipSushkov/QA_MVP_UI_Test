package specs.ContentAdmin.FaqList;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;

import org.openqa.selenium.By;
import pageobjects.ContentAdmin.FaqList.FaqEdit;
import specs.AbstractSpec;
import pageobjects.LoginPage.LoginPage;
import pageobjects.Dashboard.Dashboard;

/**
 * Created by philipsushkov on 2017-01-05.
 */

public class CheckFaqEdit extends AbstractSpec {

    private static By contentAdminMenuButton, faqListMenuItem, addNewLink;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static FaqEdit faqEdit;

    @BeforeTest
    public void setUp() throws Exception {
        contentAdminMenuButton = By.xpath(propUIContentAdmin.getProperty("btnMenu_ContentAdmin"));
        faqListMenuItem = By.xpath(propUIContentAdmin.getProperty("btnMenu_FaqList"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        faqEdit = new FaqEdit(driver);

        loginPage.loginUser();
        dashboard.openEditPageFromAddNew(contentAdminMenuButton, faqListMenuItem, addNewLink);
    }

    @Test
    public void checkFaqEdit() throws Exception {
        final String expectedTitle = "Faq Edit";

        Assert.assertNotNull(faqEdit.getUrl());
        Assert.assertEquals(faqEdit.getTitle(), expectedTitle, "Actual Faq Edit page Title doesn't match to expected");

        Assert.assertNotNull(faqEdit.getFaqNameInput(), "Faq Name input doesn't exist");
        Assert.assertNotNull(faqEdit.getQuestionsLabel(), "Questions label doesn't exist");

        Assert.assertTrue(faqEdit.getChkBoxSet(), "Checkbox elements don't exist");

        Assert.assertNotNull(faqEdit.getSaveAndSubmitButton(), "Save And Submit button doesn't exist");
    }

    @AfterTest
    public void tearDown() {
        dashboard.logoutFromAdmin();
        //driver.quit();
    }

}
