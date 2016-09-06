package pageobjects.Dashboard;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;
import pageobjects.CompanyDetails.CompanyPage;

public class Dashboard extends AbstractPageObject {
    private final By searchField = By.name("search");
    private final By firstCompanyInList = By.cssSelector("span:nth-child(1)");

    public Dashboard(WebDriver driver) {
        super(driver);
    }

    public Dashboard searchForCompany(String companyName) {
        waitForElementToAppear(searchField);
        findElement(searchField).click();
        findElement(searchField).sendKeys(companyName);
        pause(10000L);

        return this;
    }

    public CompanyPage selectCompanyFromSearch() {
        waitForElementToAppear(firstCompanyInList);
        findElement(firstCompanyInList).click();

        return new CompanyPage(getDriver());
    }
}
