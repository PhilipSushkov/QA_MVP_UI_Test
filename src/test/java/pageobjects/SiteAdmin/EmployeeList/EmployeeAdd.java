package pageobjects.SiteAdmin.EmployeeList;

import netscape.javascript.JSObject;
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
    private final String PAGE_NAME="External Feed";

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


}
