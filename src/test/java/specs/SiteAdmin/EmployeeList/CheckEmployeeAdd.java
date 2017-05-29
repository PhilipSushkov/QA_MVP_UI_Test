package specs.SiteAdmin.EmployeeList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import pageobjects.SiteAdmin.EmployeeList.EmployeeAdd;
import pageobjects.SiteAdmin.ExternalFeedList.ExternalFeedAdd;
import specs.AbstractSpec;

/**
 * Created by andyp on 2017-05-29.
 */
public class CheckEmployeeAdd extends AbstractSpec{

    private static By siteAdminMenuButton, employeeListMenuItem;
    private static LoginPage loginPage;
    private static Dashboard dashboard;
    private static EmployeeAdd employeeAdd;

    private static String sPathToFile, sDataFileJson;
    private static JSONParser parser;

    private final String DATA="getData", PAGE_NAME="Employee List";

    @BeforeTest
    public void setUp() throws Exception {
        siteAdminMenuButton = By.xpath(propUISiteAdmin.getProperty("btnMenu_SiteAdmin"));
        employeeListMenuItem = By.xpath(propUISiteAdmin.getProperty("itemMenu_EmployeeList"));

        loginPage = new LoginPage(driver);
        dashboard = new Dashboard(driver);
        employeeAdd = new EmployeeAdd(driver);

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_EmployeeList");
        sDataFileJson = propUISiteAdmin.getProperty("json_EmployeeListData");

        parser = new JSONParser();

        loginPage.loginUser();
    }
    @BeforeMethod
    public void beforeMethod() throws Exception {
        dashboard.openPageFromMenu(siteAdminMenuButton, externalFeedListMenuItem);
    }

    @Test(dataProvider=DATA, priority=1)
    public void saveExternalFeed(JSONObject data) {
        //Checking Employee List vs. Employee Edit
        String sExternalFeedName = data.get(FEED_NAME).toString();
        String expectedTitleList = "External Feed List";
        String expectedTitleEdit = "External Feed Edit";

        Assert.assertEquals(externalFeedAdd.getTitle(), expectedTitleEdit, "Actual "+PAGE_NAME+" Edit page Title doesn't match to expected");
        Assert.assertEquals(externalFeedAdd.saveExternalFeed(data, sExternalFeedName), expectedTitleList, "New "+PAGE_NAME+" didn't save properly");
    }
}
