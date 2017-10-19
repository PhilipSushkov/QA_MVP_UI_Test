package pageobjects.EmailAdmin.ManageList;

import org.apache.commons.lang.ObjectUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
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
                    cancelBtn, deleteBtn, addNewLink, successMsg, successMsgDelete;

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
        deleteBtn = By.xpath(propUIEmailAdmin.getProperty("btn_Delete"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));
        saveButton = By.xpath(propUIEmailAdmin.getProperty("button_Save"));
        parser = new JSONParser();
        successMsg = By.xpath(propUIContentAdmin.getProperty("msg_Success"));
        successMsgDelete = By.xpath(propUIEmailAdmin.getProperty("success_Msg_Delete"));

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
            findElement(activationEmailSelect).sendKeys(activation_email);
            jsonObj.put("activation_email", activation_email);

            unsubscribe_email = data.get("unsubscribe_email").toString();
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
                    findElement(publicNoCheckbox).click();
                } else {
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

    public String editMailingLists(JSONObject data, String name){
        try{
            By editBtn = By.xpath("//td[(text()='" + name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')][contains(@id, 'MailingList')]");
            waitForElement(editBtn);
            findElement(editBtn).click();

            JSONObject jsonObj = new JSONObject();
            JSONObject jsonMain = new JSONObject();
            waitForElement(saveButton);
            try {
                try {
                    FileReader readFile = new FileReader(sPathToFile + sFileJson);
                    jsonMain = (JSONObject) parser.parse(readFile);
                } catch (ParseException e) {
                }

                try {
                    if (!data.get("mailing_list_name_ch").toString().isEmpty()) {
                        findElement(mailingListNameInput).clear();
                        findElement(mailingListNameInput).sendKeys(data.get("mailing_list_name_ch").toString());
                        jsonObj.put("mailing_list_name", data.get("mailing_list_name_ch").toString());
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!data.get("description_ch").toString().isEmpty()) {
                        findElement(descriptionTextarea).clear();
                        findElement(descriptionTextarea).sendKeys(data.get("description_ch").toString());
                        jsonObj.put("description", data.get("description_ch").toString());
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!data.get("activation_email_ch").toString().isEmpty()) {
                        findElement(activationEmailSelect).sendKeys(data.get("activation_email_ch").toString());
                        jsonObj.put("activation_email", data.get("activation_email_ch").toString());
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!data.get("unsubscribe_email_ch").toString().isEmpty()) {
                        findElement(unsubscribeEmailSelect).sendKeys(data.get("unsubscribe_email_ch").toString());
                        jsonObj.put("unsubscribe_email", data.get("unsubscribe_email_ch").toString());
                    }
                } catch (NullPointerException e) {
                }

                jsonObj.put("active", Boolean.parseBoolean(data.get("active").toString()));
                try {
                    // Edit Active checkbox
                    if (Boolean.parseBoolean(data.get("active_ch").toString())) {
                        if (!Boolean.parseBoolean(findElement(activeCheckbox).getAttribute("checked"))) {
                            findElement(activeCheckbox).click();
                            jsonObj.put("active", true);
                        } else {
                        }
                    } else {
                        if (!Boolean.parseBoolean(findElement(activeCheckbox).getAttribute("checked"))) {
                        } else {
                            findElement(activeCheckbox).click();
                            jsonObj.put("active", false);
                        }
                    }
                } catch (NullPointerException e) {
                }

                try{
                    // Edit Public checkbox
                if (Boolean.parseBoolean(data.get("public_ch").toString())) {
                    if (!Boolean.parseBoolean(findElement(publicYesCheckbox).getAttribute("checked"))) {
                        findElement(publicYesCheckbox).click();
                        jsonObj.put("public", true);
                    } else {
                    }
                } else {
                    if (!Boolean.parseBoolean(findElement(publicNoCheckbox).getAttribute("checked"))) {
                        findElement(publicNoCheckbox).click();
                        jsonObj.put("public", false);
                    } else {
                    }
                }}catch (NullPointerException e){
                    e.printStackTrace();
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

                System.out.println(name + ": "+PAGE_NAME+" has been updated");

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "PASS";
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Boolean checkMailingLists (JSONObject data, String name){
        JSONObject jsonMain = new JSONObject();
        try {
            By editBtn = By.xpath("//td[(text()='"+ name +"')]/parent::tr/td/input[contains(@id, 'btnEdit')][contains(@id, 'MailingList')]");
            waitForElement(editBtn);
            findElement(editBtn).click();
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            // Compare field values with entry data
            try {
                if (!findElement(mailingListNameInput).getAttribute("value").equals(data.get("mailing_list_name").toString())) {
                    System.out.println(findElement(mailingListNameInput).getAttribute("value"));
                    System.out.println(data.get("mailing_list_name").toString());
                    System.out.println("Fails mailing list name");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(descriptionTextarea).getAttribute("value").equals(data.get("description").toString())) {
                    System.out.println(findElement(descriptionTextarea).getAttribute("value"));
                    System.out.println(data.get("description").toString());
                    System.out.println("Fails description");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(activationEmailSelect)).getFirstSelectedOption().getText().trim().equals(data.get("activation_email").toString())) {
                    System.out.println(findElement(activationEmailSelect).getAttribute("value"));
                    System.out.println(data.get("activation_email").toString());
                    System.out.println("Fails activation email");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(unsubscribeEmailSelect)).getFirstSelectedOption().getText().trim().equals(data.get("unsubscribe_email").toString())) {
                    System.out.println(findElement(unsubscribeEmailSelect).getAttribute("value"));
                    System.out.println(data.get("unsubscribe_email").toString());
                    System.out.println("Fails unsubscribe email");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(activeCheckbox).getAttribute("checked").equals(data.get("active").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(publicYesCheckbox).getAttribute("checked").equals(data.get("public").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            System.out.println(name+ ": New "+PAGE_NAME+" has been checked");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean checkMailingListsCh (JSONObject data, String name) {
        JSONObject jsonMain = new JSONObject();
        By editBtn;
        try {
            if (data.containsKey("mailing_list_name_ch")) {
                 editBtn = By.xpath("//td[(text()='" + data.get("mailing_list_name_ch").toString() + "')]/parent::tr/td/input[contains(@id, 'btnEdit')][contains(@id, 'MailingList')]");
            } else {
                 editBtn = By.xpath("//td[(text()='" + name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')][contains(@id, 'MailingList')]");
            }
                waitForElement(editBtn);
                findElement(editBtn).click();
                try {
                    FileReader readFile = new FileReader(sPathToFile + sFileJson);
                    jsonMain = (JSONObject) parser.parse(readFile);
                } catch (ParseException e) {
                }

                // Compare field values with entry data
                try {
                    if (!findElement(mailingListNameInput).getAttribute("value").equals(data.get("mailing_list_name_ch").toString())) {
                        System.out.println(findElement(mailingListNameInput).getAttribute("value"));
                        System.out.println(data.get("mailing_list_name_ch").toString());
                        System.out.println("Fails mailing list name");
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!findElement(descriptionTextarea).getAttribute("value").equals(data.get("description_ch").toString())) {
                        System.out.println(findElement(descriptionTextarea).getAttribute("value"));
                        System.out.println(data.get("description_ch").toString());
                        System.out.println("Fails description");
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!new Select(findElement(activationEmailSelect)).getFirstSelectedOption().getText().trim().equals(data.get("activation_email_ch").toString())) {
                        System.out.println(findElement(activationEmailSelect).getAttribute("value"));
                        System.out.println(data.get("activation_email_ch").toString());
                        System.out.println("Fails activation email");
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!new Select(findElement(unsubscribeEmailSelect)).getFirstSelectedOption().getText().trim().equals(data.get("unsubscribe_email_ch").toString())) {
                        System.out.println(findElement(unsubscribeEmailSelect).getAttribute("value"));
                        System.out.println(data.get("unsubscribe_email_ch").toString());
                        System.out.println("Fails unsubscribe email");
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!findElement(activeCheckbox).getAttribute("checked").equals(data.get("active_ch").toString())) {
                        System.out.println("Fails active");
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!findElement(publicYesCheckbox).getAttribute("checked").equals(data.get("public_ch").toString())) {
                        System.out.println("Fails public");
                        return false;
                    }
                } catch (NullPointerException e) {
                }

                System.out.println(": New " + PAGE_NAME + " has been checked");
                return true;
            } catch(IOException e){
                e.printStackTrace();
            } catch(NullPointerException e){
                e.printStackTrace();
        }
            return null;

    }

    public String deleteMailingLists (JSONObject data, String name) {
        By editBtn;
        try {
            if (data.containsKey("mailing_list_name_ch")) {
                editBtn = By.xpath("//td[(text()='" + data.get("mailing_list_name_ch").toString() + "')]/parent::tr/td/input[contains(@id, 'btnEdit')][contains(@id, 'MailingList')]");
            } else {
                editBtn = By.xpath("//td[(text()='" + name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')][contains(@id, 'MailingList')]");
            }
            findElement(editBtn).click();
            waitForElement(deleteBtn);
            findElement(deleteBtn).click();
            waitForElement(successMsgDelete);
            if (checkElementExists(editBtn) == null)
                return "DELETE SUCCESSFUL";
        } catch(NullPointerException e){
            e.printStackTrace();
        }
            return null;
    }





}


