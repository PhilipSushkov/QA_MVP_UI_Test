package pageobjects;

import org.apache.commons.collections4.Predicate;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.LoginPage.LoginPage;

public class AbstractPageObject implements PageObject {

    public final WebDriver driver;
    public final WebDriverWait wait;
    private final Predicate<WebElement> displayedElementPredicate = new Predicate<WebElement>() {
        @Override
        public boolean evaluate(WebElement t) {

            return t.isDisplayed();
        }
    };

    private final By logoutButton = By.xpath("//li/a[contains(text(),'Logout')]");

    public AbstractPageObject(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 15L);
    }

    @Override
    public WebDriver getDriver() {
        return this.driver;
    }

    @Override
    public Predicate<WebElement> getDisplayedElementPredicate() {
        return this.displayedElementPredicate;
    }

    @Override
    public WebDriverWait getWait() {
        return this.wait;
    }

    public void checkIfUnchecked(By selector) {
        String value = findElement(selector).getAttribute("checked");
        if (!Boolean.parseBoolean(value)) {
            findElement(selector).click();
        }
    }

    public long getScrollPositionY(){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return (Long) executor.executeScript("return window.pageYOffset;");
    }

    //Logout from Admin site
    public LoginPage logoutFromAdmin(){
        waitForElement(logoutButton);
        findElement(logoutButton).click();
        return new LoginPage(getDriver());
    }

    // Get current URL
    public String getUrl() {
        return driver.getCurrentUrl();
    }

    // Get text value of found element
    public String getText(By by) {
        return findElement(by).getText();
    }

    // Get row quantity of table
    public Integer getGridRowQuantity(int size, int columnsNumber) {
        return size/columnsNumber;
    }


}
