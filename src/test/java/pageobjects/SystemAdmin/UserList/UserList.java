package pageobjects.SystemAdmin.UserList;

import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

/**
 * Created by philipsushkov on 2016-11-10.
 */
public class UserList extends AbstractPageObject {

    public UserList(WebDriver driver) {
        super(driver);
    }

    public String getUrl () {
        return driver.getCurrentUrl();
    }

}
