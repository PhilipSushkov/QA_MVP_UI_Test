package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class Page extends AbstractPageObject {

    private final By dismissTutorialSelector = By.cssSelector(".guider #NoThanks");

    public Page(WebDriver driver) {
        super(driver);
    }

    
    public <T extends Page> T dismissTutorial() {
        if(!findElements(By.linkText("No Thanks")).isEmpty()) {

            findElement(dismissTutorialSelector).click();
            waitForElementToDissapear(dismissTutorialSelector);
        }
        else{
            System.out.println("Tutorial did not appear");
        }
        return (T) this;
    }
}