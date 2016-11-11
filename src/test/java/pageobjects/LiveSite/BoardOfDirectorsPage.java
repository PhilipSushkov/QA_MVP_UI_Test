package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by jasons on 2016-11-11.
 */
public class BoardOfDirectorsPage extends AbstractPageObject {

    private final By person = By.className("PersonContainer");
    private final By biographicalInformation = By.className("PersonDescription");

    public BoardOfDirectorsPage(WebDriver driver) {
        super(driver);
    }

    public boolean peopleAreDisplayed(){
        return doesElementExist(person) && findElement(person).isDisplayed();
    }

    public boolean peopleHaveBiographicalInformation(){
        if (!doesElementExist(biographicalInformation) || !findElement(biographicalInformation).isDisplayed()){
            System.out.println("No biographical information is displayed on the page.");
            return false;
        }
        return findElements(person).size() == findElements(biographicalInformation).size();
    }
}
