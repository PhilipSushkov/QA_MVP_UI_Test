package pageobjects.SiteAdmin.EmployeeList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;
import util.Functions;

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
    private static By cellPhoneInput, locationInput, photoInput;
    private static By saveBtn, cancelBtn, deleteBtn, addNewLink, activeChk;
    private static String sPathToFile, sFileJson, sFileJsonData ;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Employee List";

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
        photoInput = By.xpath(propUISiteAdmin.getProperty("input_Photo"));
        activeChk = By.xpath(propUISiteAdmin.getProperty("chk_Active"));

        saveBtn = By.xpath(propUISiteAdmin.getProperty("btn_Save"));
        cancelBtn = By.xpath(propUISiteAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUISiteAdmin.getProperty("btn_Delete"));
        addNewLink = By.xpath(propUISiteAdmin.getProperty("input_AddNew"));

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUISiteAdmin.getProperty("dataPath_EmployeeList");
        sFileJson = propUISiteAdmin.getProperty("json_EmployeeList");
        sFileJsonData = propUISiteAdmin.getProperty("json_EmployeeListData");
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
        String phone, extension, cell_phone, location, photo;
        Boolean active;
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonMain = new JSONObject();
        int randNum = Functions.randInt(0, 99999);

        waitForElement(addNewLink);
        findElement(addNewLink).click();
        waitForElement(saveBtn);

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            email = randNum + data.get("email").toString();
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


            photo = data.get("photo").toString();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement elemSrc =  driver.findElement(photoInput);
            js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "files/"+photo);
            jsonObj.put("photo",photo);

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

        email = jsonObj.get("email").toString();

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

            try{
                if(findElement(photoInput).toString().equals("files/" + data.get("photo").toString())){
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

            int EmployeeID = Integer.parseInt(jsonURLQuery.get("UserID").toString());

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
            return EmployeeID > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean editEmployeeList(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        String email = null;

        try {
            FileReader readFile = new FileReader(sPathToFile + sFileJson);
            jsonMain = (JSONObject) parser.parse(readFile);
            jsonObj = (JSONObject) jsonMain.get(name);
        } catch (ParseException e) {
        } catch (IOException e) {
        }

        email = jsonObj.get("email").toString();

        By editBtn = By.xpath("//td[(text()='" + email + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");

        try {

            waitForElement(moduleTitle);
            findElement(editBtn).click();
            waitForElement(deleteBtn);

            //Changing user first name
            try {
                if (!data.get("first_name_ch").toString().isEmpty()) {
                    String first_name_ch = data.get("first_name_ch").toString();
                    findElement(firstNameInput).clear();
                    findElement(firstNameInput).sendKeys(first_name_ch);
                    jsonObj.put("first_name", first_name_ch);
                }
            } catch (NullPointerException e) {}

            //Making user inactive
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

    public Boolean checkEmployeeListCh(JSONObject data, String name) {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        String email = null;

        try {
            FileReader readFile = new FileReader(sPathToFile + sFileJson);
            jsonMain = (JSONObject) parser.parse(readFile);
            jsonObj = (JSONObject) jsonMain.get(name);
        } catch (ParseException e) {
        } catch (IOException e) {
        }

        email = jsonObj.get("email").toString();

        By editBtn = By.xpath("//td[(text()='" + email + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");

        try {
            waitForElement(moduleTitle);
            findElement(editBtn).click();
            waitForElement(saveBtn);

            try {
                if (findElement(firstNameInput).toString().equals(data.get("first_name_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(activeChk).getAttribute("checked").equals(data.get("active_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            // Save Url
            URL url = new URL(getUrl());
            String[] params = url.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            jsonObj.put("url_query", jsonURLQuery);

            int EmployeeID = Integer.parseInt(jsonURLQuery.get("UserID").toString());

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
            return EmployeeID > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Boolean removeEmployee(JSONObject data, String name) {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();
        String email= null;

        try {
            FileReader readFile = new FileReader(sPathToFile + sFileJson);
            jsonMain = (JSONObject) parser.parse(readFile);
            jsonObj = (JSONObject) jsonMain.get(name);
        } catch (ParseException e) {
        } catch (IOException e) {
        }

        email = jsonObj.get("email").toString();

        By editBtn = By.xpath("//td[(text()='" + email + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");

        try {

            waitForElement(moduleTitle);
            findElement(editBtn).click();
            waitForElement(deleteBtn);

            findElement(deleteBtn).click();

            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(moduleTitle);

            try {
                waitForElement(editBtn);
            } catch (TimeoutException e) {

                jsonMain.remove(name);

                try {
                    FileWriter writeFile = new FileWriter(sPathToFile + sFileJson);
                    writeFile.write(jsonMain.toJSONString().replace("\\", ""));
                    writeFile.flush();
                } catch (FileNotFoundException e1) {
                    e.printStackTrace();
                } catch (IOException e1) {
                    e.printStackTrace();
                }

                System.out.println(name + ": " + PAGE_NAME + " has been removed");
                return true;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
