package pageobjects.EmailAdmin.ManageList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static specs.AbstractSpec.propUIContentAdmin;
import static specs.AbstractSpec.propUIEmailAdmin;

/**
 * Created by charleszheng on 2017-10-17.
 */

public class MailingListsAdd extends AbstractPageObject {
    private static By moduleTitle, mailingListNameInput, descriptionTextarea, activationEmailSelect;
    private static By unsubscribeEmailSelect, activeCheckbox, publicYesCheckbox, publicNoCheckbox, saveButton,
                    cancelBtn, addNewLink, successMsg;

    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="MailingList";

    public MailingListsAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIEmailAdmin.getProperty("spanModule_Title"));

        mailingListNameInput = By.xpath(propUIEmailAdmin.getProperty("input_MailingListName"));
        descriptionTextarea = By.xpath(propUIEmailAdmin.getProperty("txtarea_Description"));
        activationEmailSelect = By.xpath(propUIEmailAdmin.getProperty("select_ActivationEmail"));
        unsubscribeEmailSelect = By.xpath(propUIEmailAdmin.getProperty("select_UnsubscribeEmail"));

        activeCheckbox = By.xpath(propUIEmailAdmin.getProperty("chk_Active"));
        publicYesCheckbox = By.xpath(propUIEmailAdmin.getProperty("chk_PublicYes"));
        publicNoCheckbox = By.xpath(propUIEmailAdmin.getProperty("chk_PublicNo"));
        cancelBtn = By.xpath(propUIContentAdmin.getProperty("btn_Cancel"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));
        saveButton = By.xpath(propUIEmailAdmin.getProperty("button_Save"));
        parser = new JSONParser();
        successMsg = By.xpath(propUIContentAdmin.getProperty("msg_Success"));

        sPathToFile = System.getProperty("user.dir") + propUIEmailAdmin.getProperty("dataPath_ManageList");
        sFileJson = propUIEmailAdmin.getProperty("json_ManageList");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveMailingLists(JSONObject data, String name) {
        String mailing_list_name, description, activation_email, unsubscribe_email;
        Boolean active, bool_public;
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonMain = new JSONObject();

        waitForElement(addNewLink);
        findElement(addNewLink).click();
        waitForElement(saveButton);

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            mailing_list_name = data.get("mailing_list_name").toString();
            findElement(mailingListNameInput).clear();
            findElement(mailingListNameInput).sendKeys(mailing_list_name);
            jsonObj.put("mailing_list_name", mailing_list_name);

            description = data.get("description").toString();
            findElement(descriptionTextarea).clear();
            findElement(descriptionTextarea).sendKeys(description);
            jsonObj.put("description", description);

            activation_email = data.get("activation_email").toString();
            findElement(activationEmailSelect).clear();
            findElement(activationEmailSelect).sendKeys(activation_email);
            jsonObj.put("activation_email", activation_email);

            unsubscribe_email = data.get("unsubscribe_email").toString();
            findElement(unsubscribeEmailSelect).clear();
            findElement(unsubscribeEmailSelect).sendKeys(unsubscribe_email);
            jsonObj.put("unsubscribe_email", unsubscribe_email);

            // Save Active checkbox
            active = Boolean.parseBoolean(data.get("active").toString());
            jsonObj.put("active", Boolean.parseBoolean(data.get("active").toString()));
            if (active) {
                if (!Boolean.parseBoolean(findElement(activeCheckbox).getAttribute("checked"))) {
                    findElement(activeCheckbox).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(activeCheckbox).getAttribute("checked"))) {
                } else {
                    findElement(activeCheckbox).click();
                }
            }

            //Save Public checkbox
            bool_public = Boolean.parseBoolean(data.get("public").toString());
            jsonObj.put("public", Boolean.parseBoolean(data.get("public").toString()));
            if (bool_public) {
                if (!Boolean.parseBoolean(findElement(publicYesCheckbox).getAttribute("checked"))) {
                    findElement(publicYesCheckbox).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(publicNoCheckbox).getAttribute("checked"))) {
                } else {
                    findElement(publicNoCheckbox).click();
                }
            }

            findElement(saveButton).click();
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(successMsg);

            jsonMain.put(name, jsonObj);

            try {
                FileWriter writeFile = new FileWriter(sPathToFile + sFileJson);
                writeFile.write(jsonMain.toJSONString().replace("\\", ""));
                writeFile.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(name + ": "+PAGE_NAME+" has been created");
            return "PASS";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}


