package pageobjects.LiveSite;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import static specs.AbstractSpec.propUIPublicSite;

/**
 * Created by jasons on 2016-11-11.
 */
public class BoardOfDirectorsPage extends AbstractPageObject {

    private final By person;
    private final By biographicalInformation;

    public BoardOfDirectorsPage(WebDriver driver) {
        super(driver);
        person = By.className(propUIPublicSite.getProperty("person"));
        biographicalInformation = By.className(propUIPublicSite.getProperty("biographicalInformation"));
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
