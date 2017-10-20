package pageobjects.EmailAdmin.Subscribers;

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
 * Created by charleszheng on 2017-10-18.
 */

public class SubscriberAdd extends AbstractPageObject {
    private static By moduleTitle, emailAddressInput, firstNameInput, lastNameInput, companyInput;
    private static By titleInput, activeCheckbox, validatedCheckbox, address1Input, saveButton, keywordInput;
    private static By address2Input, cityInput, provinceInput, postalCodeInput, countrySelect;
    private static By regionInput, telephoneNoInput, faxNoInput, notesTextarea,
            cancelBtn, deleteBtn, addNewLink, successMsg, successMsgDelete, searchBtn;

    private static String sPathToFile, sFileJson;
    private static JSONParser parser;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME = "Subscriber";

    public SubscriberAdd(WebDriver driver) {
        super(driver);
        moduleTitle = By.xpath(propUIEmailAdmin.getProperty("spanDivModule_Title"));

        emailAddressInput = By.xpath(propUIEmailAdmin.getProperty("input_EmailAddress"));
        firstNameInput = By.xpath(propUIEmailAdmin.getProperty("input_FirstName"));
        lastNameInput = By.xpath(propUIEmailAdmin.getProperty("input_LastName"));
        companyInput = By.xpath(propUIEmailAdmin.getProperty("input_Company"));
        titleInput = By.xpath(propUIEmailAdmin.getProperty("input_Title"));
        keywordInput = By.xpath(propUIEmailAdmin.getProperty("input_KeywordSearch"));

        activeCheckbox = By.xpath(propUIEmailAdmin.getProperty("chk_Active"));
        validatedCheckbox = By.xpath(propUIEmailAdmin.getProperty("chk_Validated"));

        titleInput = By.xpath(propUIEmailAdmin.getProperty("input_Title"));
        titleInput = By.xpath(propUIEmailAdmin.getProperty("input_Title"));
        titleInput = By.xpath(propUIEmailAdmin.getProperty("input_Title"));
        titleInput = By.xpath(propUIEmailAdmin.getProperty("input_Title"));
        titleInput = By.xpath(propUIEmailAdmin.getProperty("input_Title"));

        address1Input = By.xpath(propUIEmailAdmin.getProperty("input_Address1"));
        address2Input = By.xpath(propUIEmailAdmin.getProperty("input_Address2"));
        cityInput = By.xpath(propUIEmailAdmin.getProperty("input_City"));
        provinceInput = By.xpath(propUIEmailAdmin.getProperty("input_Province"));
        postalCodeInput = By.xpath(propUIEmailAdmin.getProperty("input_PostalCode"));

        countrySelect = By.xpath(propUIEmailAdmin.getProperty("select_Country"));
        regionInput = By.xpath(propUIEmailAdmin.getProperty("input_Region"));
        telephoneNoInput = By.xpath(propUIEmailAdmin.getProperty("input_TelephoneNo"));
        faxNoInput = By.xpath(propUIEmailAdmin.getProperty("input_FaxNo"));

        notesTextarea = By.xpath(propUIEmailAdmin.getProperty("txtarea_Notes"));

        saveButton = By.xpath(propUIEmailAdmin.getProperty("button_Save"));
        cancelBtn = By.xpath(propUIContentAdmin.getProperty("btn_Cancel"));
        deleteBtn = By.xpath(propUIEmailAdmin.getProperty("btn_Delete"));
        searchBtn = By.xpath(propUIEmailAdmin.getProperty("btn_Search"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));

        successMsg = By.xpath(propUIContentAdmin.getProperty("msg_Success"));
        successMsgDelete = By.xpath(propUIEmailAdmin.getProperty("success_Msg_Delete"));

        sPathToFile = System.getProperty("user.dir") + propUIEmailAdmin.getProperty("dataPath_Subscriber");
        sFileJson = propUIEmailAdmin.getProperty("json_Subscriber");

        parser = new JSONParser();
    }

    public String getTitle() {
        findElement(addNewLink).click();
        waitForElement(moduleTitle);
        String sTitle = getText(moduleTitle);
        findElement(cancelBtn).click();
        return sTitle;
    }

    public String saveSubscriber(JSONObject data, String name) {
        String email_address, mailing_lists, categories,
        first_name, last_name, company, title, address_line1, address_line2, city, province, postal_code, country, region, telephone_no, fax_no, notes;
        Boolean active, validated;
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

            email_address = data.get("email_address").toString();
            findElement(emailAddressInput).clear();
            findElement(emailAddressInput).sendKeys(email_address);
            jsonObj.put("email_address", email_address);

            first_name = data.get("first_name").toString();
            findElement(firstNameInput).clear();
            findElement(firstNameInput).sendKeys(first_name);
            jsonObj.put("first_name", first_name);

            last_name = data.get("last_name").toString();
            findElement(lastNameInput).clear();
            findElement(lastNameInput).sendKeys(last_name);
            jsonObj.put("last_name", last_name);

            company = data.get("company").toString();
            findElement(companyInput).clear();
            findElement(companyInput).sendKeys(company);
            jsonObj.put("company", company);

            title = data.get("title").toString();
            findElement(titleInput).clear();
            findElement(titleInput).sendKeys(title);
            jsonObj.put("title", title);

            address_line1 = data.get("address_line1").toString();
            findElement(address1Input).clear();
            findElement(address1Input).sendKeys(address_line1);
            jsonObj.put("address_line1", address_line1);

            address_line2 = data.get("address_line2").toString();
            findElement(address2Input).clear();
            findElement(address2Input).sendKeys(address_line2);
            jsonObj.put("address_line2", address_line2);

            city = data.get("city").toString();
            findElement(cityInput).clear();
            findElement(cityInput).sendKeys(city);
            jsonObj.put("city", city);

            province = data.get("province").toString();
            findElement(provinceInput).clear();
            findElement(provinceInput).sendKeys(province);
            jsonObj.put("province", province);

            postal_code = data.get("postal_code").toString();
            findElement(postalCodeInput).clear();
            findElement(postalCodeInput).sendKeys(postal_code);
            jsonObj.put("postal_code", postal_code);

            country = data.get("country").toString();
            findElement(countrySelect).sendKeys(country);
            jsonObj.put("country", country);

            region = data.get("region").toString();
            findElement(regionInput).clear();
            findElement(regionInput).sendKeys(region);
            jsonObj.put("region", region);

            telephone_no = data.get("telephone_no").toString();
            findElement(telephoneNoInput).clear();
            findElement(telephoneNoInput).sendKeys(telephone_no);
            jsonObj.put("telephone_no", telephone_no);

            fax_no = data.get("fax_no").toString();
            findElement(faxNoInput).clear();
            findElement(faxNoInput).sendKeys(fax_no);
            jsonObj.put("fax_no", fax_no);

            notes = data.get("notes").toString();
            findElement(notesTextarea).clear();
            findElement(notesTextarea).sendKeys(notes);
            jsonObj.put("notes", notes);

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

            //Save Validated checkbox
            validated = Boolean.parseBoolean(data.get("validated").toString());
            jsonObj.put("validated", Boolean.parseBoolean(data.get("validated").toString()));
            if (validated) {
                if (!Boolean.parseBoolean(findElement(validatedCheckbox).getAttribute("checked"))) {
                    findElement(validatedCheckbox).click();
                } else {
                }
            } else {
                if (!Boolean.parseBoolean(findElement(validatedCheckbox).getAttribute("checked"))) {
                } else {
                    findElement(validatedCheckbox).click();
                }
            }

            mailing_lists = data.get("mailing_lists").toString();
            if(checkElementExists(By.xpath("//label[text()='" + mailing_lists + "']")) != null){
            findElement(By.xpath("//label[text()='" + mailing_lists + "']")).click();
            jsonObj.put("mailing_lists", data.get("mailing_lists").toString());}


            categories = data.get("categories").toString();
            if(checkElementExists(By.xpath("//label[text()='" + categories + "']")) != null){
            findElement(By.xpath("//label[text()='" + categories + "']")).click();
            jsonObj.put("categories", data.get("categories").toString());}


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

    public String editSubscriber(JSONObject data, String name){
        try{
            By editBtn = By.xpath("//td[(text()='" + name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");
            findElement(keywordInput).sendKeys(name);
            findElement(searchBtn).click();

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
                    if (!data.get("email_address_ch").toString().isEmpty()) {
                        findElement(emailAddressInput).clear();
                        findElement(emailAddressInput).sendKeys(data.get("email_address_ch").toString());
                        jsonObj.put("email_address", data.get("email_address_ch").toString());
                    }
                } catch (NullPointerException e) {
                }

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

                try {
                    // Edit validated checkbox
                    if (Boolean.parseBoolean(data.get("validated_ch").toString())) {
                        if (!Boolean.parseBoolean(findElement(validatedCheckbox).getAttribute("checked"))) {
                            findElement(validatedCheckbox).click();
                            jsonObj.put("validated", true);
                        } else {
                        }
                    } else {
                        if (!Boolean.parseBoolean(findElement(validatedCheckbox).getAttribute("checked"))) {
                        } else {
                            findElement(validatedCheckbox).click();
                            jsonObj.put("validated", false);
                        }
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!data.get("mailing_lists_ch").toString().isEmpty()) {
                        findElement(By.xpath("//label[text()='" + data.get("mailing_lists") + "']")).click();
                        if(checkElementExists(By.xpath("//label[text()='" + data.get("mailing_lists_ch").toString() + "']")) != null){
                            findElement(By.xpath("//label[text()='" + data.get("mailing_lists_ch").toString() + "']")).click();
                            jsonObj.put("mailing_lists", data.get("mailing_lists_ch").toString());}
                    }
                } catch (NullPointerException e) {
                }

                try {
                    if (!data.get("categories_ch").toString().isEmpty()) {
                        findElement(By.xpath("//label[text()='" + data.get("categories").toString() + "']")).click();
                        if(checkElementExists(By.xpath("//label[text()='" + data.get("categories_ch").toString() + "']")) != null){
                            findElement(By.xpath("//label[text()='" + data.get("categories_ch").toString() + "']")).click();
                            jsonObj.put("categories", data.get("categories_ch").toString());}
                    }
                } catch (NullPointerException e) {
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

    public Boolean checkSubscriber (JSONObject data, String name){
        JSONObject jsonMain = new JSONObject();
        try {
            By editBtn = By.xpath("//td[(text()='" + name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");
            findElement(keywordInput).sendKeys(name);
            findElement(searchBtn).click();

            waitForElement(editBtn);
            findElement(editBtn).click();

            try {
                FileReader readFile = new FileReader(sPathToFile + sFileJson);
                jsonMain = (JSONObject) parser.parse(readFile);
            } catch (ParseException e) {
            }

            // Compare field values with entry data
            try {
                if (!findElement(emailAddressInput).getAttribute("value").equals(data.get("email_address").toString())) {
                    System.out.println(findElement(emailAddressInput).getAttribute("value"));
                    System.out.println(data.get("email_address").toString());
                    System.out.println("Fails email address");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(firstNameInput).getAttribute("value").equals(data.get("first_name").toString())) {
                    System.out.println(findElement(firstNameInput).getAttribute("value"));
                    System.out.println(data.get("first_name").toString());
                    System.out.println("Fails first name");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(lastNameInput).getAttribute("value").equals(data.get("last_name").toString())) {
                    System.out.println(findElement(lastNameInput).getAttribute("value"));
                    System.out.println(data.get("last_name").toString());
                    System.out.println("Fails last name");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(companyInput).getAttribute("value").equals(data.get("company").toString())) {
                    System.out.println(findElement(companyInput).getAttribute("value"));
                    System.out.println(data.get("company").toString());
                    System.out.println("Fails company");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(titleInput).getAttribute("value").equals(data.get("title").toString())) {
                    System.out.println(findElement(titleInput).getAttribute("value"));
                    System.out.println(data.get("title").toString());
                    System.out.println("Fails title");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(address1Input).getAttribute("value").equals(data.get("address_line1").toString())) {
                    System.out.println(findElement(address1Input).getAttribute("value"));
                    System.out.println(data.get("address_line1").toString());
                    System.out.println("Fails address line1");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(address2Input).getAttribute("value").equals(data.get("address_line2").toString())) {
                    System.out.println(findElement(address1Input).getAttribute("value"));
                    System.out.println(data.get("address_line2").toString());
                    System.out.println("Fails address line2");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(cityInput).getAttribute("value").equals(data.get("city").toString())) {
                    System.out.println(findElement(cityInput).getAttribute("value"));
                    System.out.println(data.get("city").toString());
                    System.out.println("Fails city");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(provinceInput).getAttribute("value").equals(data.get("province").toString())) {
                    System.out.println(findElement(provinceInput).getAttribute("value"));
                    System.out.println(data.get("province").toString());
                    System.out.println("Fails province");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(postalCodeInput).getAttribute("value").equals(data.get("postal_code").toString())) {
                    System.out.println(findElement(postalCodeInput).getAttribute("value"));
                    System.out.println(data.get("postal_code").toString());
                    System.out.println("Fails postal code");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(regionInput).getAttribute("value").equals(data.get("region").toString())) {
                    System.out.println(findElement(regionInput).getAttribute("value"));
                    System.out.println(data.get("region").toString());
                    System.out.println("Fails region");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(telephoneNoInput).getAttribute("value").equals(data.get("telephone_no").toString())) {
                    System.out.println(findElement(telephoneNoInput).getAttribute("value"));
                    System.out.println(data.get("telephone_no").toString());
                    System.out.println("Fails telephone no");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(faxNoInput).getAttribute("value").equals(data.get("fax_no").toString())) {
                    System.out.println(findElement(faxNoInput).getAttribute("value"));
                    System.out.println(data.get("fax_no").toString());
                    System.out.println("Fails fax no");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(notesTextarea).getAttribute("value").equals(data.get("notes").toString())) {
                    System.out.println(findElement(notesTextarea).getAttribute("value"));
                    System.out.println(data.get("notes").toString());
                    System.out.println("Fails notes");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(countrySelect)).getFirstSelectedOption().getText().trim().equals(data.get("country").toString())) {
                    System.out.println(findElement(countrySelect).getAttribute("value"));
                    System.out.println(data.get("country").toString());
                    System.out.println("Fails country");
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
                if (!findElement(validatedCheckbox).getAttribute("checked").equals(data.get("validated").toString())) {
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

    public Boolean checkSubscriberCh (JSONObject data, String name){
        JSONObject jsonMain = new JSONObject();
        By editBtn;
        try {
            if (data.containsKey("email_address_ch")) {
                editBtn = By.xpath("//td[(text()='" + data.get("email_address_ch").toString() + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");
                findElement(keywordInput).sendKeys(data.get("email_address_ch").toString());
                findElement(searchBtn).click();
            }
            else{
                editBtn = By.xpath("//td[(text()='" + name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");
                findElement(keywordInput).sendKeys(name);
                findElement(searchBtn).click();
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
                if (!findElement(emailAddressInput).getAttribute("value").equals(data.get("email_address_ch").toString())) {
                    System.out.println(findElement(emailAddressInput).getAttribute("value"));
                    System.out.println(data.get("email_address_ch").toString());
                    System.out.println("Fails email address");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(firstNameInput).getAttribute("value").equals(data.get("first_name").toString())) {
                    System.out.println(findElement(firstNameInput).getAttribute("value"));
                    System.out.println(data.get("first_name").toString());
                    System.out.println("Fails first name");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(lastNameInput).getAttribute("value").equals(data.get("last_name").toString())) {
                    System.out.println(findElement(lastNameInput).getAttribute("value"));
                    System.out.println(data.get("last_name").toString());
                    System.out.println("Fails last name");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(companyInput).getAttribute("value").equals(data.get("company").toString())) {
                    System.out.println(findElement(companyInput).getAttribute("value"));
                    System.out.println(data.get("company").toString());
                    System.out.println("Fails company");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(titleInput).getAttribute("value").equals(data.get("title").toString())) {
                    System.out.println(findElement(titleInput).getAttribute("value"));
                    System.out.println(data.get("title").toString());
                    System.out.println("Fails title");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(address1Input).getAttribute("value").equals(data.get("address_line1").toString())) {
                    System.out.println(findElement(address1Input).getAttribute("value"));
                    System.out.println(data.get("address_line1").toString());
                    System.out.println("Fails address line1");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(address2Input).getAttribute("value").equals(data.get("address_line2").toString())) {
                    System.out.println(findElement(address1Input).getAttribute("value"));
                    System.out.println(data.get("address_line2").toString());
                    System.out.println("Fails address line2");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(cityInput).getAttribute("value").equals(data.get("city").toString())) {
                    System.out.println(findElement(cityInput).getAttribute("value"));
                    System.out.println(data.get("city").toString());
                    System.out.println("Fails city");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(provinceInput).getAttribute("value").equals(data.get("province").toString())) {
                    System.out.println(findElement(provinceInput).getAttribute("value"));
                    System.out.println(data.get("province").toString());
                    System.out.println("Fails province");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(postalCodeInput).getAttribute("value").equals(data.get("postal_code").toString())) {
                    System.out.println(findElement(postalCodeInput).getAttribute("value"));
                    System.out.println(data.get("postal_code").toString());
                    System.out.println("Fails postal code");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(regionInput).getAttribute("value").equals(data.get("region").toString())) {
                    System.out.println(findElement(regionInput).getAttribute("value"));
                    System.out.println(data.get("region").toString());
                    System.out.println("Fails region");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(telephoneNoInput).getAttribute("value").equals(data.get("telephone_no").toString())) {
                    System.out.println(findElement(telephoneNoInput).getAttribute("value"));
                    System.out.println(data.get("telephone_no").toString());
                    System.out.println("Fails telephone no");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(faxNoInput).getAttribute("value").equals(data.get("fax_no").toString())) {
                    System.out.println(findElement(faxNoInput).getAttribute("value"));
                    System.out.println(data.get("fax_no").toString());
                    System.out.println("Fails fax no");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(notesTextarea).getAttribute("value").equals(data.get("notes").toString())) {
                    System.out.println(findElement(notesTextarea).getAttribute("value"));
                    System.out.println(data.get("notes").toString());
                    System.out.println("Fails notes");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!new Select(findElement(countrySelect)).getFirstSelectedOption().getText().trim().equals(data.get("country").toString())) {
                    System.out.println(findElement(countrySelect).getAttribute("value"));
                    System.out.println(data.get("country").toString());
                    System.out.println("Fails country");
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(activeCheckbox).getAttribute("checked").equals(data.get("active_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }

            try {
                if (!findElement(validatedCheckbox).getAttribute("checked").equals(data.get("validated_ch").toString())) {
                    return false;
                }
            } catch (NullPointerException e) {
            }


            System.out.println(": New "+PAGE_NAME+" has been checked");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String deleteSubscriber (JSONObject data, String name) {
        By editBtn;
        try {
            if (data.containsKey("email_address_ch")) {
                editBtn = By.xpath("//td[(text()='" + data.get("email_address_ch").toString() + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");
                findElement(keywordInput).sendKeys(data.get("email_address_ch").toString());
                findElement(searchBtn).click();
            }
            else{
                editBtn = By.xpath("//td[(text()='" + name + "')]/parent::tr/td/input[contains(@id, 'btnEdit')]");
                findElement(keywordInput).sendKeys(name);
                findElement(searchBtn).click();
            }
            waitForElement(editBtn);
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
