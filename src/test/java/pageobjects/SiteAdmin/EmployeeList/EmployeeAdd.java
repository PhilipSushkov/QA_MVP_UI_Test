package pageobjects.SiteAdmin.EmployeeList;

import netscape.javascript.JSObject;
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
import java.net.URL;

import static specs.AbstractSpec.propUISiteAdmin;

/**
 * Created by andyp on 2017-05-29.
 */
public class EmployeeAdd extends AbstractPageObject {
    private static By moduleTitle, emailInput, passwordInput, firstNameInput;
    private static By lastNameInput, jobTitleInput, phoneInput, extensionInput;
    private static By cellPhoneInput, locationInput;
    private static By saveBtn, cancelBtn, deleteBtn, addNewLink, activeChk;
    private static By workflowStateSpan, commentsTxt, successMsg, currentContentSpan;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Employee List", EMPLOYEE_NAME = "Bob";

    public EmployeeAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUISiteAdmin.getProperty("spanModule_Title"));

        emailInput = By.xpath(propUISiteAdmin.getProperty("input_Email"));
        passwordInput = By.xpath(propUISiteAdmin.getProperty("input_Password"));
        firstNameInput = By.xpath(propUISiteAdmin.getProperty("input_FirstName"));
        lastNameInput = By.xpath(propUISiteAdmin.getProperty("input_LastName"));
        jobTitleInput = By.xpath(propUISiteAdmin.getProperty("input_JobTitle"));
        phoneInput = By.xpath(propUISiteAdmin.getProperty("input_Phone"));
        extensionInput = By.xpath(propUISiteAdmin.getProperty("input_Extension"));
        cellPhoneInput = By.xpath(propUISiteAdmin.getProperty("input_CellPhone"));
        locationInput = By.xpath(propUISiteAdmin.getProperty("input_Location"));
        activeChk = By.xpath(propUISiteAdmin.getProperty("chk_Active"));

        saveBtn = By.xpath(propUISiteAdmin.getProperty("btn_Save"));
        cancelBtn = By.xpath(propUISiteAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUISiteAdmin.getProperty("btn_Delete"));
        addNewLink = By.xpath(propUISiteAdmin.getProperty("input_AddNew"));
        workflowStateSpan = By.xpath(propUISiteAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUISiteAdmin.getProperty("txtarea_Comments"));
        successMsg = By.xpath(propUISiteAdmin.getProperty("msg_Success"));
        currentContentSpan = By.xpath(propUISiteAdmin.getProperty("span_CurrentContent"));

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_EmployeeList");
        sFileJson = propUISiteAdmin.getProperty("json_EmployeeList");
    }

    public String getTitle(){
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveEmployeeList(JSONObject data, String name){
        String email, password, first_name, last_name, job_title;
        String phone, extension, cell_phone, location;
        Boolean active;
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonMain = new JSONObject();

        waitForElement(addNewLink);
        findElement(addNewLink).click();
        waitForElement(saveBtn);

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            email = data.get("email").toString();
            findElement(emailInput).sendKeys(email);
            jsonObj.put("email", email);

            password = data.get("password").toString();
            findElement(passwordInput).sendKeys(password);
            jsonObj.put("password", password);

            first_name = data.get("first_name").toString();
            findElement(firstNameInput).sendKeys(first_name);
            jsonObj.put("first_name", first_name);

            last_name = data.get("last_name").toString();
            findElement(lastNameInput).sendKeys(last_name);
            jsonObj.put("last_name", last_name);

            job_title = data.get("job_title").toString();
            findElement(jobTitleInput).sendKeys(job_title);
            jsonObj.put("job_title", job_title);

            phone = data.get("phone").toString();
            findElement(phoneInput).sendKeys(phone);
            jsonObj.put("phone", phone);

            extension = data.get("extension").toString();
            findElement(extensionInput).sendKeys(extension);
            jsonObj.put("extension", extension);
            
            cell_phone = data.get("cell_phone").toString();
            findElement(cellPhoneInput).sendKeys(cell_phone);
            jsonObj.put("cell_phone", cell_phone);

            location = data.get("location").toString();
            findElement(locationInput).sendKeys(location);
            jsonObj.put("location", location);

            // Save Active checkbox
            active = Boolean.parseBoolean(data.get("active").toString());
            jsonObj.put("active", Boolean.parseBoolean(data.get("active").toString()));
            if (active) {
                if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                    findElement(activeChk).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                } else {
                    findElement(activeChk).click();
                }
            }

            findElement(saveBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

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

            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(moduleTitle);

            System.out.println(name + ": "+PAGE_NAME+" has been created");
            return findElement(moduleTitle).getText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public Boolean checkEmployeeList(JSONObject data, String name) {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        String email;

        try {
            FileReader readFile = new FileReader(sPathToFile + sFileJson);
            jsonMain = (JSONObject) parser.parse(readFile);
            jsonObj = (JSONObject) jsonMain.get(name);
        } catch (ParseException e) {
        } catch (IOException e) {
        }

        email = data.get("email").toString();

        By editBtn = By.xpath("//td[(text()='" + email + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");

        try {
            waitForElement(moduleTitle);
            findElement(editBtn).click();
            waitForElement(saveBtn);

            try{
                if(findElement(emailInput).toString().equals(data.get("email").toString())){
                    return false;
                }
            } catch (NullPointerException e) {
            return false;
            }

            try{
                if(findElement(passwordInput).toString().equals(data.get("password").toString())){
                    return false;
                }
            } catch (NullPointerException e) {
                return false;
            }

            try{
                if(findElement(firstNameInput).toString().equals(data.get("first_name").toString())){
                    return false;
                }
            } catch (NullPointerException e) {
                return false;
            }

            try{
                if(findElement(lastNameInput).toString().equals(data.get("last_name").toString())){
                    return false;
                }
            } catch (NullPointerException e) {
                return false;
            }

            try{
                if(findElement(jobTitleInput).toString().equals(data.get("job_title").toString())){
                    return false;
                }
            } catch (NullPointerException e) {
                return false;
            }

            try{
                if(findElement(phoneInput).toString().equals(data.get("phone").toString())){
                    return false;
                }
            } catch (NullPointerException e) {
                return false;
            }

            try{
                if(findElement(extensionInput).toString().equals(data.get("extension").toString())){
                    return false;
                }
            } catch (NullPointerException e) {
                return false;
            }

            try{
                if(findElement(cellPhoneInput).toString().equals(data.get("cell_phone").toString())){
                    return false;
                }
            } catch (NullPointerException e) {
                return false;
            }

            try{
                if(findElement(locationInput).toString().equals(data.get("location").toString())){
                    return false;
                }
            } catch (NullPointerException e) {
                return false;
            }

            try {
                if (!findElement(activeChk).getAttribute("checked").equals(data.get("active").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
                return false;
            }

            // Save Url
            URL url = new URL(getUrl());
            String[] params = url.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            jsonObj.put("url_query", jsonURLQuery);

            int ExternalFeedID = Integer.parseInt(jsonURLQuery.get("UserID").toString());

            try {
                FileWriter writeFile = new FileWriter(sPathToFile + sFileJson);
                writeFile.write(jsonMain.toJSONString().replace("\\", ""));
                writeFile.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(name + ": "+PAGE_NAME+" has been checked");
            return ExternalFeedID > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean editExternalFeed(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        String feed, details_name = null;

        try {
            FileReader readFile = new FileReader(sPathToFile + sFileJson);
            jsonMain = (JSONObject) parser.parse(readFile);
            jsonObj = (JSONObject) jsonMain.get(name);
        } catch (ParseException e) {
        } catch (IOException e) {
        }

        feed = data.get("feed").toString();

        switch (feed) {
            case PRESS_RELEASE:
                details_name = "ClientId: " + jsonObj.get("comp_id").toString();
                break;

            case EOD_MAIL:
                details_name = jsonObj.get("stock_exchange").toString() + " : " + jsonObj.get("stock_symbol").toString();
                break;

            default:
                break;
        }

        By editBtn = By.xpath("//td[(text()='" + details_name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");

        try {

            waitForElement(moduleTitle);
            String pageUrl = getPageUrl(jsonMain, name);
            //driver.get(pageUrl);
            findElement(editBtn).click();
            waitForElement(deleteBtn);

            try {
                if (!data.get("tag_list_ch").toString().isEmpty()) {
                    String tag_list_ch = data.get("tag_list_ch").toString();
                    findElement(tagListInput).clear();
                    findElement(tagListInput).sendKeys(tag_list_ch);
                    jsonObj.put("tag_list", tag_list_ch);
                }
            } catch (NullPointerException e) {}

            try {
                if (!data.get("active_ch").toString().isEmpty()) {
                    if (Boolean.parseBoolean(data.get("active_ch").toString())) {
                        if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                            findElement(activeChk).click();
                            jsonObj.put("active", true);
                        } else {
                        }
                    } else {
                        if (!Boolean.parseBoolean(findElement(activeChk).getAttribute("checked"))) {
                        } else {
                            findElement(activeChk).click();
                            jsonObj.put("active", false);
                        }
                    }
                }
            } catch (NullPointerException e) {
            }

            Thread.sleep(DEFAULT_PAUSE);
            findElement(saveBtn).click();

            try {
                FileWriter writeFile = new FileWriter(sPathToFile + sFileJson);
                writeFile.write(jsonMain.toJSONString().replace("\\", ""));
                writeFile.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(editBtn);

            System.out.println(name + ": " + PAGE_NAME + " has been changed");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
