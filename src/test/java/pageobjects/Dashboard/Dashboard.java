package pageobjects.Dashboard;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.AbstractPageObject;
import pageobjects.NewPressRelease.NewPressRelease;

public class Dashboard extends AbstractPageObject {
    private final By addPressReleaseButton = By.xpath("//a[contains(@id,'hrefPressReleases')]");

    public Dashboard(WebDriver driver) {
        super(driver);
    }

    public NewPressRelease newPressRelease() {
        wait.until(ExpectedConditions.elementToBeClickable(addPressReleaseButton));
        findElement(addPressReleaseButton).click();
        return new NewPressRelease(getDriver());
    }
}
