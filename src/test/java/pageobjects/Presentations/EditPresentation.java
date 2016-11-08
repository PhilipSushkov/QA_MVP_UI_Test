package pageobjects.Presentations;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import pageobjects.AbstractPageObject;

/**
 * Created by philipsushkov on 2016-11-08.
 */
public class EditPresentation extends AbstractPageObject {
    Actions action = new Actions(driver);

    public EditPresentation(WebDriver driver) {
        super(driver);
    }

    public String addNewPresentation(String headline, String date, String hour, String minute, String AMPM, String[] filenames) {

        String newsPageURL = "";
        return newsPageURL;
    }

}
