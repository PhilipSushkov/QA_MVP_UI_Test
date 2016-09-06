package specs.Dashboard;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pageobjects.CompanyDetails.CompanyPage;
import pageobjects.Dashboard.Dashboard;
import pageobjects.LoginPage.LoginPage;
import specs.AbstractSpec;

public class DashboardSearch extends AbstractSpec{

    @Before
    public void logInUserBeforeEachTest() {
        new LoginPage(driver).loginUser();
    }

    @Test
    public void canSearchForCompany() {
        String companyName = "Tesla Motors, Inc.";
        CompanyPage finish = new CompanyPage(driver);
        Dashboard start = new Dashboard(driver);
        start.searchForCompany(companyName)
                .selectCompanyFromSearch();

        Assert.assertEquals(companyName, finish.getCompanyName());
        System.out.println(finish.getCompanyName());
    }

    @Test
    public void cantSearchForUnknownCompany() {
        String companyName = "12345qwerty";
        Dashboard start = new Dashboard(driver);
        start.searchForCompany(companyName);
    }
}
