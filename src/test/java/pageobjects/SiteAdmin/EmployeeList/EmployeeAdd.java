package pageobjects.SiteAdmin.EmployeeList;

import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pageobjects.AbstractPageObject;

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
}
