package pageobjects.EmailAdmin.EmailAlertList;

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
import static specs.AbstractSpec.propUISystemAdmin;

/**
 * Created by charleszheng on 2017-10-20.
 */

//Designed for qaensco.s1.q4web.newtest/admin/
//"System Tasks", "System Message" and "User Groups" might be different for different sites

public class EmailAlertListAdd extends AbstractPageObject {
    private static By moduleTitle, descriptionInput;
    private static By clientAdministratorChk, internalChk, publicSiteChk,
    systemAdministratorChk, systemAuthorChk, systemGuestChk, systemPreviewerChk, systemPublisherChk;
    private static By systemTaskSelect, systemMessageSelect;
    private static By cancelBtn, deleteBtn, addNewLink, saveButton;

    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME = "EmailAlertList";

    public EmailAlertListAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIEmailAdmin.getProperty("spanDivModule_Title_EmailAlertList"));

        descriptionInput = By.xpath(propUIEmailAdmin.getProperty("input_Description"));

        clientAdministratorChk = By.xpath(propUIEmailAdmin.getProperty("chk_ClientAdministrator"));
        internalChk = By.xpath(propUIEmailAdmin.getProperty("chk_Internal"));
        publicSiteChk = By.xpath(propUIEmailAdmin.getProperty("chk_PublicSite"));
        systemAdministratorChk = By.xpath(propUIEmailAdmin.getProperty("chk_SystemAdministrator"));
        systemAuthorChk = By.xpath(propUIEmailAdmin.getProperty("chk_SystemAuthor"));
        systemGuestChk = By.xpath(propUIEmailAdmin.getProperty("chk_SystemGuest"));
        systemPreviewerChk = By.xpath(propUIEmailAdmin.getProperty("chk_SystemPreviewer"));
        systemPublisherChk = By.xpath(propUIEmailAdmin.getProperty("chk_SystemPublisher"));

        systemTaskSelect = By.xpath(propUISystemAdmin.getProperty("select_SystemTask"));
        systemMessageSelect = By.xpath(propUISystemAdmin.getProperty("select_SystemMessage"));

        saveButton = By.xpath(propUIEmailAdmin.getProperty("button_Save"));
        cancelBtn = By.xpath(propUIContentAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUIEmailAdmin.getProperty("btn_Delete"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));



        sPathToFile = System.getProperty("user.dir") + propUIEmailAdmin.getProperty("dataPath_EmailAlertList");
        sFileJson = propUIEmailAdmin.getProperty("json_EmailAlert");

        parser = new JSONParser();
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveEmailAlertList(JSONObject data, String name) {
        String description, system_task, system_message;
        Boolean client_administrator, client_administrator_02, internal, public_site,
                system_administrator, system_author, system_guest, system_previewer,
                system_publisher;
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

            description = data.get("description").toString();
            findElement(descriptionInput).clear();
            findElement(descriptionInput).sendKeys(description);
            jsonObj.put("description", description);

            system_task = data.get("system_task").toString();
            findElement(systemTaskSelect).sendKeys(system_task);
            jsonObj.put("system_task", system_task);

            system_message = data.get("system_message").toString();
            findElement(systemMessageSelect).sendKeys(system_message);
            jsonObj.put("system_message", system_message);

            client_administrator = Boolean.parseBoolean(data.get("client_administrator").toString());
            jsonObj.put("client_administrator", Boolean.parseBoolean(data.get("client_administrator").toString()));
            if (client_administrator) {
                if (!Boolean.parseBoolean(findElement(clientAdministratorChk).getAttribute("checked"))) {
                    findElement(clientAdministratorChk).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(clientAdministratorChk).getAttribute("checked"))) {
                } else {
                    findElement(clientAdministratorChk).click();
                }
            }

            internal = Boolean.parseBoolean(data.get("internal").toString());
            jsonObj.put("internal", Boolean.parseBoolean(data.get("internal").toString()));
            if (internal) {
                if (!Boolean.parseBoolean(findElement(internalChk).getAttribute("checked"))) {
                    findElement(internalChk).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(internalChk).getAttribute("checked"))) {
                } else {
                    findElement(internalChk).click();
                }
            }

            public_site = Boolean.parseBoolean(data.get("public_site").toString());
            jsonObj.put("public_site", Boolean.parseBoolean(data.get("public_site").toString()));
            if (public_site) {
                if (!Boolean.parseBoolean(findElement(publicSiteChk).getAttribute("checked"))) {
                    findElement(publicSiteChk).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(publicSiteChk).getAttribute("checked"))) {
                } else {
                    findElement(publicSiteChk).click();
                }
            }

            system_administrator = Boolean.parseBoolean(data.get("system_administrator").toString());
            jsonObj.put("system_administrator", Boolean.parseBoolean(data.get("system_administrator").toString()));
            if (system_administrator) {
                if (!Boolean.parseBoolean(findElement(systemAdministratorChk).getAttribute("checked"))) {
                    findElement(systemAdministratorChk).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(systemAdministratorChk).getAttribute("checked"))) {
                } else {
                    findElement(systemAdministratorChk).click();
                }
            }

            system_author = Boolean.parseBoolean(data.get("system_author").toString());
            jsonObj.put("system_author", Boolean.parseBoolean(data.get("system_author").toString()));
            if (system_author) {
                if (!Boolean.parseBoolean(findElement(systemAuthorChk).getAttribute("checked"))) {
                    findElement(systemAuthorChk).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(systemAuthorChk).getAttribute("checked"))) {
                } else {
                    findElement(systemAuthorChk).click();
                }
            }

            system_guest = Boolean.parseBoolean(data.get("system_guest").toString());
            jsonObj.put("system_guest", Boolean.parseBoolean(data.get("system_guest").toString()));
            if (system_guest) {
                if (!Boolean.parseBoolean(findElement(systemGuestChk).getAttribute("checked"))) {
                    findElement(systemGuestChk).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(systemGuestChk).getAttribute("checked"))) {
                } else {
                    findElement(systemGuestChk).click();
                }
            }

            system_previewer = Boolean.parseBoolean(data.get("system_previewer").toString());
            jsonObj.put("system_previewer", Boolean.parseBoolean(data.get("system_previewer").toString()));
            if (system_previewer) {
                if (!Boolean.parseBoolean(findElement(systemPreviewerChk).getAttribute("checked"))) {
                    findElement(systemPreviewerChk).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(systemPreviewerChk).getAttribute("checked"))) {
                } else {
                    findElement(systemPreviewerChk).click();
                }
            }

            system_publisher = Boolean.parseBoolean(data.get("system_publisher").toString());
            jsonObj.put("system_publisher", Boolean.parseBoolean(data.get("system_publisher").toString()));
            if (system_publisher) {
                if (!Boolean.parseBoolean(findElement(systemPublisherChk).getAttribute("checked"))) {
                    findElement(systemPublisherChk).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(systemPublisherChk).getAttribute("checked"))) {
                } else {
                    findElement(systemPublisherChk).click();
                }
            }


            findElement(saveButton).click();
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

            System.out.println(name + ": "+PAGE_NAME+" has been created");
            return "PASS";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String editEmailAlertList (JSONObject data, String name){
        try{
            By editBtn = By.xpath("//td[(text()='" + name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");
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
                    if (!data.get("description_ch").toString().isEmpty()) {
                        findElement(descriptionInput).clear();
                        findElement(descriptionInput).sendKeys(data.get("description_ch").toString());
                        jsonObj.put("description_ch", data.get("description_ch").toString());
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!data.get("system_task_ch").toString().isEmpty()) {
                        findElement(systemTaskSelect).sendKeys(data.get("system_task_ch").toString());
                        jsonObj.put("system_task_ch", data.get("system_task_ch").toString());
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!data.get("system_message_ch").toString().isEmpty()) {
                        findElement(systemMessageSelect).sendKeys(data.get("system_message_ch").toString());
                        jsonObj.put("system_message_ch", data.get("system_message_ch").toString());
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (Boolean.parseBoolean(data.get("client_administrator_ch").toString())) {
                        if (!Boolean.parseBoolean(findElement(clientAdministratorChk).getAttribute("checked"))) {
                            findElement(clientAdministratorChk).click();
                            jsonObj.put("client_administrator", true);
                        } else {
                        }
                    } else {
                        if (!Boolean.parseBoolean(findElement(clientAdministratorChk).getAttribute("checked"))) {
                        } else {
                            findElement(clientAdministratorChk).click();
                            jsonObj.put("client_administrator", false);
                        }
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (Boolean.parseBoolean(data.get("internal_ch").toString())) {
                        if (!Boolean.parseBoolean(findElement(internalChk).getAttribute("checked"))) {
                            findElement(internalChk).click();
                            jsonObj.put("internal", true);
                        } else {
                        }
                    } else {
                        if (!Boolean.parseBoolean(findElement(internalChk).getAttribute("checked"))) {
                        } else {
                            findElement(internalChk).click();
                            jsonObj.put("internal", false);
                        }
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (Boolean.parseBoolean(data.get("public_site_ch").toString())) {
                        if (!Boolean.parseBoolean(findElement(publicSiteChk).getAttribute("checked"))) {
                            findElement(publicSiteChk).click();
                            jsonObj.put("public_site", true);
                        } else {
                        }
                    } else {
                        if (!Boolean.parseBoolean(findElement(publicSiteChk).getAttribute("checked"))) {
                        } else {
                            findElement(publicSiteChk).click();
                            jsonObj.put("public_site", false);
                        }
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (Boolean.parseBoolean(data.get("system_administrator_ch").toString())) {
                        if (!Boolean.parseBoolean(findElement(systemAdministratorChk).getAttribute("checked"))) {
                            findElement(systemAdministratorChk).click();
                            jsonObj.put("system_administrator", true);
                        } else {
                        }
                    } else {
                        if (!Boolean.parseBoolean(findElement(systemAdministratorChk).getAttribute("checked"))) {
                        } else {
                            findElement(systemAdministratorChk).click();
                            jsonObj.put("system_administrator", false);
                        }
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (Boolean.parseBoolean(data.get("system_author_ch").toString())) {
                        if (!Boolean.parseBoolean(findElement(systemAuthorChk).getAttribute("checked"))) {
                            findElement(systemAuthorChk).click();
                            jsonObj.put("system_author", true);
                        } else {
                        }
                    } else {
                        if (!Boolean.parseBoolean(findElement(systemAuthorChk).getAttribute("checked"))) {
                        } else {
                            findElement(systemAuthorChk).click();
                            jsonObj.put("system_author", false);
                        }
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (Boolean.parseBoolean(data.get("system_guest_ch").toString())) {
                        if (!Boolean.parseBoolean(findElement(systemGuestChk).getAttribute("checked"))) {
                            findElement(systemGuestChk).click();
                            jsonObj.put("system_guest", true);
                        } else {
                        }
                    } else {
                        if (!Boolean.parseBoolean(findElement(systemGuestChk).getAttribute("checked"))) {
                        } else {
                            findElement(systemGuestChk).click();
                            jsonObj.put("system_guest", false);
                        }
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (Boolean.parseBoolean(data.get("system_previewer_ch").toString())) {
                        if (!Boolean.parseBoolean(findElement(systemPreviewerChk).getAttribute("checked"))) {
                            findElement(systemPreviewerChk).click();
                            jsonObj.put("system_previewer", true);
                        } else {
                        }
                    } else {
                        if (!Boolean.parseBoolean(findElement(systemPreviewerChk).getAttribute("checked"))) {
                        } else {
                            findElement(systemPreviewerChk).click();
                            jsonObj.put("system_previewer", false);
                        }
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (Boolean.parseBoolean(data.get("system_publisher_ch").toString())) {
                        if (!Boolean.parseBoolean(findElement(systemPublisherChk).getAttribute("checked"))) {
                            findElement(systemPublisherChk).click();
                            jsonObj.put("system_publisher", true);
                        } else {
                        }
                    } else {
                        if (!Boolean.parseBoolean(findElement(systemPublisherChk).getAttribute("checked"))) {
                        } else {
                            findElement(systemPublisherChk).click();
                            jsonObj.put("system_publisher", false);
                        }
                    }
                } catch (NullPointerException e) {
                }

                findElement(saveButton).click();
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

    public Boolean checkEmailAlertList (JSONObject data, String name){
        JSONObject jsonMain = new JSONObject();
        try {
            By editBtn = By.xpath("//td[(text()='" + name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");

            waitForElement(editBtn);
            findElement(editBtn).click();

            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            // Compare field values with entry data
            try {
                if (!findElement(descriptionInput).getAttribute("value").equals(data.get("description").toString())) {
                    System.out.println(findElement(descriptionInput).getAttribute("value"));
                    System.out.println(data.get("description").toString());
                    System.out.println("Fails description");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(systemTaskSelect)).getFirstSelectedOption().getText().trim().equals(data.get("system_task").toString())) {
                    System.out.println(findElement(systemTaskSelect).getAttribute("value"));
                    System.out.println(data.get("system_task").toString());
                    System.out.println("Fails system task");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(systemMessageSelect)).getFirstSelectedOption().getText().trim().equals(data.get("system_message").toString())) {
                    System.out.println(findElement(systemMessageSelect).getAttribute("value"));
                    System.out.println(data.get("system_message").toString());
                    System.out.println("Fails system message");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(clientAdministratorChk).getAttribute("checked").equals(data.get("client_administrator").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(internalChk).getAttribute("checked").equals(data.get("internal").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(publicSiteChk).getAttribute("checked").equals(data.get("public_site").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(systemAdministratorChk).getAttribute("checked").equals(data.get("system_administrator").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(systemAuthorChk).getAttribute("checked").equals(data.get("system_author").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(systemGuestChk).getAttribute("checked").equals(data.get("system_guest").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(systemPreviewerChk).getAttribute("checked").equals(data.get("system_previewer").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(systemPublisherChk).getAttribute("checked").equals(data.get("system_publisher").toString())) {
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

    public Boolean checkEmailAlertListCh (JSONObject data, String name){
        JSONObject jsonMain = new JSONObject();
        By editBtn;
        try {
            if (data.containsKey("description_ch")) {
                editBtn = By.xpath("//td[(text()='" + data.get("description_ch").toString() + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");
            }
            else{
                editBtn = By.xpath("//td[(text()='" + name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");
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
                if (!findElement(descriptionInput).getAttribute("value").equals(data.get("description_ch").toString())) {
                    System.out.println(findElement(descriptionInput).getAttribute("value"));
                    System.out.println(data.get("description_ch").toString());
                    System.out.println("Fails description");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(systemTaskSelect)).getFirstSelectedOption().getText().trim().equals(data.get("system_task_ch").toString())) {
                    System.out.println(findElement(systemTaskSelect).getAttribute("value"));
                    System.out.println(data.get("system_task_ch").toString());
                    System.out.println("Fails system task");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(systemMessageSelect)).getFirstSelectedOption().getText().trim().equals(data.get("system_message_ch").toString())) {
                    System.out.println(findElement(systemMessageSelect).getAttribute("value"));
                    System.out.println(data.get("system_message_ch").toString());
                    System.out.println("Fails system message");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(clientAdministratorChk).getAttribute("checked").equals(data.get("client_administrator_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(internalChk).getAttribute("checked").equals(data.get("internal_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(publicSiteChk).getAttribute("checked").equals(data.get("public_site_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(systemAdministratorChk).getAttribute("checked").equals(data.get("system_administrator_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(systemAuthorChk).getAttribute("checked").equals(data.get("system_author_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(systemGuestChk).getAttribute("checked").equals(data.get("system_guest_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(systemPreviewerChk).getAttribute("checked").equals(data.get("system_previewer_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(systemPublisherChk).getAttribute("checked").equals(data.get("system_publisher_ch").toString())) {
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

    public String deleteEmailAlertList (JSONObject data, String name) {
        By editBtn;
        try {
            if (data.containsKey("description_ch")) {
                editBtn = By.xpath("//td[(text()='" + data.get("description_ch").toString() + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");
            }
            else{
                editBtn = By.xpath("//td[(text()='" + name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");
            }
            waitForElement(editBtn);
            findElement(editBtn).click();
            waitForElement(deleteBtn);
            findElement(deleteBtn).click();
            if (checkElementExists(editBtn) == null)
                return "DELETE SUCCESSFUL";
        } catch(NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

}
