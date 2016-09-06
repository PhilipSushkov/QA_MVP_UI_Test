package pageobjects.CompanyDetails;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.Dashboard.Dashboard;
import pageobjects.Page;

/**
 * Created by patrickp on 2016-08-04.
 */
public class CompanyPage extends Page {
    private final By companyName = By.className("company-name");

    public CompanyPage(WebDriver driver) {
        super(driver);
    }

    public String getCompanyName() {
        return findElement(companyName).getText();
    }
}
