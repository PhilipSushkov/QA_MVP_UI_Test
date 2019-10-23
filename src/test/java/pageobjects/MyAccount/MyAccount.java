package pageobjects.MyAccount;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pageobjects.AbstractPageObject;
import util.Log;
import static specs.AbstractSpec.propUIMyAccount;

/**
 * Created by PSUSHKOV on Oct, 2019
 **/

public class MyAccount extends AbstractPageObject {
    private static By usernameId, passwordId, loginBtn, errorMessageLbl;

    public MyAccount(WebDriver driver) {
        super(driver);

        usernameId = By.id(propUIMyAccount.getProperty("txt_Username"));
        passwordId = By.id(propUIMyAccount.getProperty("txt_Password"));
        loginBtn = By.name(propUIMyAccount.getProperty("btn_Login"));
        errorMessageLbl = By.xpath(propUIMyAccount.getProperty("lbl_ErrorMessage"));
    }

    public String getErrorMessage(JSONObject data) {
        try {
            waitForElementToBeClickable(loginBtn);

            WebElement usernameElement = findElement(usernameId);
            usernameElement.clear();
            usernameElement.sendKeys(JsonPath.read(data, "$.credentials.username").toString());

            WebElement passwordElement = findElement(passwordId);
            passwordElement.clear();
            passwordElement.sendKeys(JsonPath.read(data, "$.credentials.password").toString());

            Log.info("Login data has filled up successfully");

            findElement(loginBtn).click();
            Log.info("Login button has been clicked");

            waitForElement(errorMessageLbl);
            WebElement errorMessageElement = findElement(errorMessageLbl);
            Log.info("Error message text has been taken successfully");

            return errorMessageElement.getText().trim();
        } catch (Exception e) {
            e.printStackTrace();
            Log.error("Get Login Error message: ElementNotFoundException occurred");
        }

        return null;
    }

}
