package pageobjects.ContentAdmin.Presentations;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import pageobjects.AbstractPageObject;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pageobjects.PageAdmin.WorkflowState;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static specs.AbstractSpec.desktopUrl;
import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by charleszheng on 2017-10-11.
 */

public class PresentationAdd extends AbstractPageObject{
    private static By moduleTitle, timeHourSelect, timeMinSelect, presentationFileInput;
    private static By pDateInput, tagsInput, publishBtn,pTitle;
    private static By speakerName, speakerPosition, btn_SpeakerOK, speakerNameChk, speakerPositionChk;
    private static By currentContentSpan;
    private static By activeChk, saveBtn, revertBtn, cancelBtn, deleteBtn, addNewLink, speakersAddNewBtn;
    private static By workflowStateSpan, commentsTxt, successMsg, saveAndSubmitBtn;
    private static By previewLnk;
    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Presentation", SPEAKERS="speakers";

    public PresentationAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIContentAdmin.getProperty("spanModule_Title"));
        timeHourSelect = By.xpath(propUIContentAdmin.getProperty("select_PresentationTimeHH"));
        timeMinSelect = By.xpath(propUIContentAdmin.getProperty("select_PresentationTimeMM"));
        presentationFileInput = By.xpath(propUIContentAdmin.getProperty("input_PresentationFile"));
        pDateInput = By.xpath(propUIContentAdmin.getProperty("input_PresentationDate"));
        pTitle = By.xpath(propUIContentAdmin.getProperty("input_PresentationTitle"));
        tagsInput = By.xpath(propUIContentAdmin.getProperty("input_Tags"));
        activeChk = By.xpath(propUIContentAdmin.getProperty("chk_Active"));
        saveBtn = By.xpath(propUIContentAdmin.getProperty("btn_Save"));
        cancelBtn = By.xpath(propUIContentAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUIContentAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUIContentAdmin.getProperty("btn_Publish"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));
        revertBtn = By.xpath(propUIContentAdmin.getProperty("btn_Revert"));
        workflowStateSpan = By.xpath(propUIContentAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUIContentAdmin.getProperty("txtarea_Comments"));
        successMsg = By.xpath(propUIContentAdmin.getProperty("msg_Success"));
        saveAndSubmitBtn = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
        currentContentSpan = By.xpath(propUIContentAdmin.getProperty("span_CurrentContent"));
        previewLnk = By.xpath(propUIContentAdmin.getProperty("lnk_Preview"));
        speakersAddNewBtn = By.xpath(propUIContentAdmin.getProperty("href_AddNewSpeakers"));
        speakerName = By.xpath(propUIContentAdmin.getProperty("input_SpeakerName")) ;
        speakerPosition = By.xpath(propUIContentAdmin.getProperty("input_SpeakerPosition")) ;
        btn_SpeakerOK = By.xpath(propUIContentAdmin.getProperty("btn_SpeakerOK"));
        speakerNameChk = By.xpath(propUIContentAdmin.getProperty("input_SpeakerName_Chk"));
        speakerPositionChk = By.xpath(propUIContentAdmin.getProperty("input_SpeakerPosition_Chk"));

        parser = new JSONParser();

        sPathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_PresentationList");
        sFileJson = propUIContentAdmin.getProperty("json_Presentation");
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String savePresentation(JSONObject data, String name) {
        String time_hour, time_min, presentation_file, presentation_date, tags, presentation_title,
                speaker_name, speaker_position;

        Boolean active;
        JSONObject jsonObj = new JSONObject();
        JSONObject jsonMain = new JSONObject();
        JSONObject  sObj;

        waitForElement(addNewLink);
        findElement(addNewLink).click();
        waitForElement(saveBtn);
        waitForElement(speakersAddNewBtn);
        findElement(speakersAddNewBtn).click();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            presentation_date = data.get("presentation_date").toString();
            findElement(pDateInput).clear();
            findElement(pDateInput).sendKeys(presentation_date);
            jsonObj.put("presentation_date", presentation_date);

            time_hour = data.get("time_hour").toString();
            findElement(timeHourSelect).sendKeys(time_hour);
            jsonObj.put("time_hour", time_hour);

            time_min = data.get("time_min").toString();
            findElement(timeMinSelect).sendKeys(time_min);
            jsonObj.put("time_min", time_min);

            presentation_title= data.get("presentation_title").toString();
            findElement(pTitle).clear();
            findElement(pTitle).sendKeys(presentation_title);
            jsonObj.put("presentation_title", presentation_title);

            tags = data.get("tags").toString();
            findElement(tagsInput).clear();
            findElement(tagsInput).sendKeys(tags);
            jsonObj.put("tags", tags);

            sObj = (JSONObject) data.get("speakers");

            speaker_name = sObj.get("speaker_name").toString();
            findElement(speakerName).clear();
            findElement(speakerName).sendKeys(speaker_name);
            jsonObj.put("speaker_name", speaker_name);

            speaker_position = sObj.get("speaker_position").toString();
            findElement(speakerPosition).clear();
            findElement(speakerPosition).sendKeys(speaker_position);
            jsonObj.put("speaker_position", speaker_position);

            findElement(btn_SpeakerOK).click();

            presentation_file = data.get("presentation_file").toString();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement elemSrc =  driver.findElement(presentationFileInput);
            String filename = presentation_file;
            js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "files/"+filename);
            jsonObj.put("presentation_filename", presentation_file);


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

            URL url = new URL(getUrl());
            String[] params = url.getQuery().split("&");
            JSONObject jsonURLQuery = new JSONObject();
            for (String param:params) {
                jsonURLQuery.put(param.split("=")[0], param.split("=")[1]);
            }
            jsonObj.put("url_query", jsonURLQuery);

            findElement(saveBtn).click();
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(successMsg);

            jsonObj.put("workflow_state", WorkflowState.IN_PROGRESS.state());

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
            return findElement(workflowStateSpan).getText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String saveAndSubmitPresentation(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        By editBtn = By.xpath("//td[(text()='"+name+"')]/parent::tr/td/input[contains(@id, 'imgEdit')][contains(@id, 'Presentation')]");

        try {
            waitForElement(moduleTitle);

            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);

            findElement(commentsTxt).sendKeys(data.get("comment").toString());
            findElement(saveAndSubmitBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(editBtn);
            findElement(editBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            JSONObject jsonObj = (JSONObject) jsonMain.get(name);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "false");

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(name+ ": "+PAGE_NAME+" has been sumbitted");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean checkPresentation(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonSpeakers = (JSONObject) data.get(SPEAKERS);

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);

            // Compare field values with entry data
            try {
                if (!new Select(findElement(timeHourSelect)).getFirstSelectedOption().getText().trim().equals(data.get("time_hour").toString())) {
                    System.out.println(findElement(timeHourSelect).getAttribute("value"));
                    System.out.println(data.get("time_hour").toString());
                    System.out.println("Fails time hour");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(timeMinSelect)).getFirstSelectedOption().getText().trim().equals(data.get("time_min").toString())) {
                    System.out.println(findElement(timeMinSelect).getAttribute("value"));
                    System.out.println(data.get("time_Min").toString());
                    System.out.println("Fails time min");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(pDateInput).getAttribute("value").equals(data.get("presentation_date").toString())) {
                    System.out.println("Fails date");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(pTitle).getAttribute("value").equals(data.get("presentation_title").toString())) {
                    System.out.println("Fails title");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(tagsInput).getAttribute("value").trim().equals(data.get("tags").toString())) {
                    System.out.println(findElement(tagsInput).getAttribute("value"));
                    System.out.println(data.get("tags").toString());
                    System.out.println("Fails tags");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(speakerNameChk).getAttribute("value").trim().equals(jsonSpeakers.get("speaker_name").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(speakerPositionChk).getAttribute("value").trim().equals(jsonSpeakers.get("speaker_position").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                JavascriptExecutor js = (JavascriptExecutor)driver;
                if (!js.executeScript("return arguments[0].value;", driver.findElement(presentationFileInput)).equals("files/" + data.get("presentation_file").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(activeChk).getAttribute("checked").equals(data.get("active").toString())) {
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

    public String publishPresentation(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(publishBtn);
            findElement(publishBtn).click();
            Thread.sleep(DEFAULT_PAUSE*2);

            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE*2);
            driver.navigate().refresh();
            Thread.sleep(DEFAULT_PAUSE);

            jsonObj.put("workflow_state", WorkflowState.LIVE.state());
            jsonObj.put("deleted", "false");

            jsonMain.put(name, jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            Thread.sleep(DEFAULT_PAUSE*2);
            driver.navigate().refresh();

            System.out.println(jsonObj.get("presentation_title").toString() + ": New "+PAGE_NAME+" has been published");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String changeAndSubmitPresentation(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(saveAndSubmitBtn);

            try {
                if (!data.get("presentation_title_ch").toString().isEmpty()) {
                    findElement(pTitle).clear();
                    findElement(pTitle).sendKeys(data.get("presentation_title_ch").toString());
                    jsonObj.put("presentation_title", data.get("presentation_title_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!data.get("presentation_date_ch").toString().isEmpty()) {
                    findElement(pDateInput).clear();
                    findElement(pDateInput).sendKeys(data.get("presentation_date_ch").toString());
                    jsonObj.put("presentation_date", data.get("presentation_date_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!data.get("tags_ch").toString().isEmpty()) {
                    findElement(tagsInput).clear();
                    findElement(tagsInput).sendKeys(data.get("tags_ch").toString());
                    jsonObj.put("tags", data.get("tags_ch").toString());
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!data.get("presentation_file_ch").toString().isEmpty()) {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    WebElement elemSrc =  driver.findElement(presentationFileInput);
                    String filename = data.get("presentation_file_ch").toString();
                    js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", elemSrc, "value", "files/"+filename);
                    jsonObj.put("presentation_filename", filename);
                }
            } catch (NullPointerException e) {
            }

            jsonObj.put("active", Boolean.parseBoolean(data.get("active").toString()));
            try {
                // Save Active checkbox
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
            } catch (NullPointerException e) {
            }

            try {
                findElement(commentsTxt).clear();
                findElement(commentsTxt).sendKeys(data.get("comment_ch").toString());
            } catch (NullPointerException e) {
            }

            findElement(saveAndSubmitBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "false");

            jsonMain.put(name, jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(workflowStateSpan);

            System.out.println(jsonObj.get("presentation_title").toString() + ": New "+PAGE_NAME+" changes have been submitted");
            return findElement(workflowStateSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String revertToLivePresentation(String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(revertBtn);

            if (jsonObj.get("workflow_state").toString().equals(WorkflowState.FOR_APPROVAL.state())) {
                findElement(revertBtn).click();
                Thread.sleep(DEFAULT_PAUSE);

                jsonObj.put("workflow_state", WorkflowState.LIVE.state());
                jsonObj.put("deleted", "false");

                jsonMain.put(name, jsonObj);

                FileWriter file = new FileWriter(sPathToFile + sFileJson);
                file.write(jsonMain.toJSONString().replace("\\", ""));
                file.flush();

                driver.get(pageUrl);
                Thread.sleep(DEFAULT_PAUSE);
                waitForElement(workflowStateSpan);

                System.out.println(jsonObj.get("presentation_title").toString()+ ": "+PAGE_NAME+" has been reverted to Live");
                return findElement(workflowStateSpan).getText();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean checkPresentationCh(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonSpeakers = (JSONObject) data.get(SPEAKERS);

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }
            JSONObject obj = (JSONObject) jsonMain.get(name);

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);
            waitForElement(commentsTxt);

            // Compare field values with entry data
            try {
                if (!new Select(findElement(timeHourSelect)).getFirstSelectedOption().getText().trim().equals(data.get("time_hour").toString())) {
                    System.out.println(findElement(timeHourSelect).getAttribute("value"));
                    System.out.println(data.get("time_hour").toString());
                    System.out.println("Fails time hour");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(timeMinSelect)).getFirstSelectedOption().getText().trim().equals(data.get("time_min").toString())) {
                    System.out.println(findElement(timeMinSelect).getAttribute("value"));
                    System.out.println(data.get("time_Min").toString());
                    System.out.println("Fails time min");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(pDateInput).getAttribute("value").equals(data.get("presentation_date_ch").toString())) {
                    System.out.println("Fails date");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(pTitle).getAttribute("value").equals(data.get("presentation_title_ch").toString())) {
                    System.out.println("Fails title");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(tagsInput).getAttribute("value").trim().equals(data.get("tags_ch").toString())) {
                    System.out.println(findElement(tagsInput).getAttribute("value"));
                    System.out.println(data.get("tags").toString());
                    System.out.println("Fails tags");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(speakerNameChk).getAttribute("value").trim().equals(jsonSpeakers.get("speaker_name").toString())) {
                    System.out.println(findElement(speakerNameChk).getAttribute("value"));
                    System.out.println(data.get("speaker_name").toString());
                    System.out.println("Fails speaker name");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(speakerPositionChk).getAttribute("value").trim().equals(jsonSpeakers.get("speaker_position").toString())) {
                    System.out.println(findElement(speakerNameChk).getAttribute("value"));
                    System.out.println(data.get("speaker_position").toString());
                    System.out.println("Fails speaker position");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                JavascriptExecutor js = (JavascriptExecutor)driver;
                if (!js.executeScript("return arguments[0].value;", driver.findElement(presentationFileInput)).equals("files/" + data.get("presentation_file_ch").toString())) {
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


            System.out.println(obj.get("presentation_title").toString()+ ": New "+PAGE_NAME+" has been checked");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String setupAsDeletedPresentation(String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();
        JSONObject jsonObj = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
                jsonObj = (JSONObject) jsonMain.get(name);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(commentsTxt);
            findElement(commentsTxt).sendKeys("Removing "+PAGE_NAME);
            findElement(deleteBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            try {
                Thread.sleep(DEFAULT_PAUSE*2);
                driver.get(pageUrl);
            } catch (UnhandledAlertException e) {
                driver.switchTo().alert().accept();
                Thread.sleep(DEFAULT_PAUSE*2);
                try {
                    driver.get(pageUrl);
                } catch (UnhandledAlertException e2) {
                    driver.switchTo().alert().accept();
                    Thread.sleep(DEFAULT_PAUSE*2);
                    driver.get(pageUrl);
                }
            }

            waitForElement(currentContentSpan);

            jsonObj.put("workflow_state", WorkflowState.FOR_APPROVAL.state());
            jsonObj.put("deleted", "true");

            jsonMain.put(name, jsonObj);

            FileWriter file = new FileWriter(sPathToFile + sFileJson);
            file.write(jsonMain.toJSONString().replace("\\", ""));
            file.flush();

            System.out.println(jsonObj.get("presentation_title").toString()+ ": "+PAGE_NAME+" set up as deleted");
            return findElement(currentContentSpan).getText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String removePresentation(JSONObject data, String name) throws InterruptedException {
        JSONObject jsonMain = new JSONObject();

        try {
            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            String pageUrl = getPageUrl(jsonMain, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            if (findElement(currentContentSpan).getText().equals(WorkflowState.DELETE_PENDING.state())) {

                waitForElement(commentsTxt);
                findElement(commentsTxt).sendKeys("Approving "+PAGE_NAME+" removal");
                findElement(publishBtn).click();

                Thread.sleep(DEFAULT_PAUSE*2);

                try {
                    Thread.sleep(DEFAULT_PAUSE*2);
                    driver.get(pageUrl);
                } catch (UnhandledAlertException e) {
                    driver.switchTo().alert().accept();
                    Thread.sleep(DEFAULT_PAUSE*2);
                    try {
                        driver.get(pageUrl);
                    } catch (UnhandledAlertException e2) {
                        driver.switchTo().alert().accept();
                        Thread.sleep(DEFAULT_PAUSE*2);
                        driver.get(pageUrl);
                    }
                }

                jsonMain.remove(name);

                FileWriter file = new FileWriter(sPathToFile + sFileJson);
                file.write(jsonMain.toJSONString().replace("\\", ""));
                file.flush();

                Thread.sleep(DEFAULT_PAUSE*2);
                driver.navigate().refresh();

                System.out.println(": New "+PAGE_NAME+" has been removed");
                return findElement(workflowStateSpan).getText();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean publicPresentation(JSONObject data, String name) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFileJson));
            JSONObject Obj = (JSONObject) jsonObject.get(name);

            String pageUrl = getPageUrl(jsonObject, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            String publicUrl = findElement(By.xpath("//span[contains(@id,'PageUrl')]")).getText();

            ((JavascriptExecutor)driver).executeScript("window.open();");

            Thread.sleep(DEFAULT_PAUSE);

            ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));


            try {
                driver.get(publicUrl);
            } catch (TimeoutException e) {
                driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
            }

            if (driver.getTitle().contains(name)) {
                driver.switchTo().window(tabs.get(1)).close();
                driver.switchTo().window(tabs.get(0));

                System.out.println(name + ": New Presentation Public has checked");
                return true;
            }
                else {
                driver.switchTo().window(tabs.get(1)).close();
                driver.switchTo().window(tabs.get(0));
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        }

        return null;
    }

    public Boolean publicPresentationCh(JSONObject data, String name) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFileJson));
            JSONObject Obj = (JSONObject) jsonObject.get(name);

            String pageUrl = getPageUrl(jsonObject, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            String publicUrl = findElement(By.xpath("//span[contains(@id,'PageUrl')]")).getText();

            ((JavascriptExecutor)driver).executeScript("window.open();");

            Thread.sleep(DEFAULT_PAUSE);

            ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));


            try {
                driver.get(publicUrl);
            } catch (TimeoutException e) {
                driver.findElement(By.tagName("body")).sendKeys("Keys.ESCAPE");
            }

            if (driver.getTitle().contains(data.get("presentation_title_ch").toString())){
                driver.switchTo().window(tabs.get(1)).close();
                driver.switchTo().window(tabs.get(0));

                System.out.println(data.get("presentation_title_ch").toString() + ": New Presentation Public has checked");
                return true;
            }  else {
                driver.switchTo().window(tabs.get(1)).close();
                driver.switchTo().window(tabs.get(0));
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        }

        return null;
    }

 /*   public Boolean previewPresentation(JSONObject data, String name) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFileJson));
            JSONObject Obj = (JSONObject) jsonObject.get(name);

            String pageUrl = getPageUrl(jsonObject, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            findElement(previewLnk).click();

            Thread.sleep(DEFAULT_PAUSE*3);
            //    ((JavascriptExecutor)driver).executeScript("window.open()");
            ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            //       driver.get(getPreviewPageUrl(jsonObject,name));

            dashboard.openPageFromMenu(investorBtn, presentationBtn);

            if (findElement(By.xpath("//span[contains(@class,'ModuleHeadline')][contains(text(),'" + name + "')]")) != null) {
                driver.switchTo().window(tabs.get(1)).close();
                Thread.sleep(DEFAULT_PAUSE * 3);
                driver.switchTo().window(tabs.get(0));

                System.out.println(name + ": New Presentation Preview has been checked");
                return true;
            } else {
                driver.switchTo().window(tabs.get(1)).close();
                Thread.sleep(DEFAULT_PAUSE);
                driver.switchTo().window(tabs.get(0));
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public Boolean previewPresentationCh(JSONObject data, String name) throws InterruptedException {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToFile + sFileJson));
            JSONObject Obj = (JSONObject) jsonObject.get(name);

            String pageUrl = getPageUrl(jsonObject, name);
            driver.get(pageUrl);
            Thread.sleep(DEFAULT_PAUSE);

            findElement(previewLnk).click();

            Thread.sleep(DEFAULT_PAUSE*3);
            //    ((JavascriptExecutor)driver).executeScript("window.open()");
            ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            //       driver.get(getPreviewPageUrl(jsonObject,name));

            dashboard.openPageFromMenu(investorBtn, presentationBtn);

            if (findElement(By.xpath("//span[contains(@class,'ModuleHeadline')][contains(text(),'" + data.get("presentation_title_ch") + "')]")) != null) {
                driver.switchTo().window(tabs.get(1)).close();
                Thread.sleep(DEFAULT_PAUSE * 3);
                driver.switchTo().window(tabs.get(0));

                System.out.println(data.get("presentation_title_ch") + ": New Presentation Preview has been checked");
                return true;
            } else {
                driver.switchTo().window(tabs.get(1)).close();
                Thread.sleep(DEFAULT_PAUSE);
                driver.switchTo().window(tabs.get(0));
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }*/

    public String getPageUrl(JSONObject obj, String name) {
        String sItemID = JsonPath.read(obj, "$.['"+name+"'].url_query.ItemID");
        String sLanguageId = JsonPath.read(obj, "$.['"+name+"'].url_query.LanguageId");
        String sSectionId = JsonPath.read(obj, "$.['"+name+"'].url_query.SectionId");
        return desktopUrl.toString()+"default.aspx?ItemID="+sItemID+"&LanguageId="+sLanguageId+"&SectionId="+sSectionId;
    }
}
