package pageobjects.ContentAdmin.Events;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;
import pageobjects.AbstractPageObject;
import pageobjects.PageAdmin.WorkflowState;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static specs.AbstractSpec.propUIContentAdmin;

/**
 * Created by charleszheng on 2017-11-10.
 */

public class EventTimezone extends AbstractPageObject{
    private static By startTimeHourSelect, startTimeMinSelect,endTimeHourSelect, endTimeMinSelect;
    private static By eventStartDate, eventEndDate, publishBtn, eventTitle, timeZone, timeZoneDisplay;
    private static By moduleTime, tabEvent, iconRSS, calendarDownload, moduleHeadline;
    private static By startAA, endAA;
    private static By currentContentSpan;
    private static By activeChk, saveBtn, deleteBtn, addNewLink;
    private static By workflowStateSpan, commentsTxt, saveAndSubmitBtn;
    private static String PathToJsonFile, pathToFile, calFile, jsonFile;
    private static final long DEFAULT_PAUSE = 2500;
    private final String PAGE_NAME="Event";
    private String timeZoneDisplayName, chicagotestRequestUrl, requestBody, chicagotestPastEventUrl;

    public EventTimezone(WebDriver driver) {
        super(driver);
        startTimeHourSelect = By.xpath(propUIContentAdmin.getProperty("select_StartTimeHH"));
        startTimeMinSelect = By.xpath(propUIContentAdmin.getProperty("select_StartTimeMM"));
        endTimeHourSelect = By.xpath(propUIContentAdmin.getProperty("select_EndTimeHH"));
        endTimeMinSelect = By.xpath(propUIContentAdmin.getProperty("select_EndTimeMM"));
        eventStartDate = By.xpath(propUIContentAdmin.getProperty("input_StartDate"));
        eventEndDate = By.xpath(propUIContentAdmin.getProperty("input_EndDate"));
        eventTitle = By.xpath(propUIContentAdmin.getProperty("input_eventTitle"));
        timeZone = By.xpath(propUIContentAdmin.getProperty("select_StartTimeZone"));
        activeChk = By.xpath(propUIContentAdmin.getProperty("chk_Active"));
        saveBtn = By.xpath(propUIContentAdmin.getProperty("btn_Save"));
        deleteBtn = By.xpath(propUIContentAdmin.getProperty("btn_Delete"));
        publishBtn = By.xpath(propUIContentAdmin.getProperty("btn_Publish"));
        addNewLink = By.xpath(propUIContentAdmin.getProperty("input_AddNew"));
        workflowStateSpan = By.xpath(propUIContentAdmin.getProperty("select_WorkflowState"));
        commentsTxt = By.xpath(propUIContentAdmin.getProperty("txtarea_Comments"));
        saveAndSubmitBtn = By.xpath(propUIContentAdmin.getProperty("btn_SaveAndSubmit"));
        currentContentSpan = By.xpath(propUIContentAdmin.getProperty("span_CurrentContent"));
        moduleTime = By.xpath(propUIContentAdmin.getProperty("module_Time"));
        timeZoneDisplay = By.xpath(propUIContentAdmin.getProperty("input_TimeZoneDisplay"));
        tabEvent = By.xpath(propUIContentAdmin.getProperty("tab_Event"));
        iconRSS = By.xpath(propUIContentAdmin.getProperty("icon_RSS"));
        calendarDownload = By.xpath(propUIContentAdmin.getProperty("link_CalendarDownload"));
        moduleHeadline = By.xpath(propUIContentAdmin.getProperty("module_Headline"));
        startAA = By.xpath(propUIContentAdmin.getProperty("select_StartTimeAM"));
        endAA = By.xpath(propUIContentAdmin.getProperty("select_EndTimeAM"));

        PathToJsonFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_EventList");
        pathToFile = System.getProperty("user.dir") + propUIContentAdmin.getProperty("dataPath_EventFileList");
        jsonFile = propUIContentAdmin.getProperty("json_RequestBody");
        calFile = propUIContentAdmin.getProperty("ics_Calendar");
        chicagotestRequestUrl = "https://chicagotest.q4web.com/services/EventService.svc/GetEventList";
        chicagotestPastEventUrl = "https://chicagotest.q4web.com/feed/Event.svc/GetEventList?apiKey=FA452A31FC824D31A0037232CD96B59E&eventSelection=0&eventDateFilter=0&includeFinancialReports=true&includePresentations=true&includePressReleases=true&sortOperator=1&pageSize=-1&tagList=&includeTags=true&year=-1&excludeSelection=0&_=1510331819528";
    }

    public String saveAndSubmitEvent(JSONObject data, String name) {
        By editBtn = By.xpath("//td[(text()='"+name+"')]/parent::tr/td/input[contains(@id, 'imgEdit')][contains(@id, 'Event')]");
        String time_zone, event_title;
        Boolean active;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat hourFormat = new SimpleDateFormat("h");
        DateFormat minuteFormat = new SimpleDateFormat("mm");
        DateFormat amFormat = new SimpleDateFormat("aa");
        Calendar cal = Calendar.getInstance();
        cal = getCalUnderTimeZone(data.get("time_zone").toString(), cal);
        waitForElement(addNewLink);
        findElement(addNewLink).click();
        waitForElement(saveBtn);
        try {
            cal.add(Calendar.MINUTE, 5);

            findElement(eventStartDate).clear();
            findElement(eventStartDate).sendKeys(dateFormat.format(cal.getTime()));

            findElement(eventEndDate).clear();
            findElement(eventEndDate).sendKeys(dateFormat.format(cal.getTime()));

            findElement(startTimeHourSelect).sendKeys(hourFormat.format(cal.getTime()));
            System.out.println(hourFormat.format(cal.getTime()));
            findElement(startTimeMinSelect).sendKeys(minuteFormat.format(cal.getTime()));

            findElement(startAA).sendKeys(amFormat.format(cal.getTime()));

            cal.add(Calendar.MINUTE, 5);

            findElement(endTimeHourSelect).sendKeys(hourFormat.format(cal.getTime()));

            findElement(endTimeMinSelect).sendKeys(minuteFormat.format(cal.getTime()));

            findElement(endAA).sendKeys(amFormat.format(cal.getTime()));

            time_zone = data.get("time_zone").toString();
            findElement(timeZone).sendKeys(time_zone);

            event_title= data.get("event_title").toString();
            findElement(eventTitle).clear();
            findElement(eventTitle).sendKeys(event_title);

            timeZoneDisplayName = findElement(timeZoneDisplay).getAttribute("value");

            // Save Active checkbox
            active = Boolean.parseBoolean(data.get("active").toString());
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

            waitForElement(commentsTxt);

            findElement(commentsTxt).sendKeys(data.get("comment").toString());
            findElement(saveAndSubmitBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(editBtn);
            findElement(editBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            System.out.println(name+ ": "+PAGE_NAME+" has been sumbitted");

            return findElement(workflowStateSpan).getText();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String publishEvent(JSONObject data, String name) throws InterruptedException {
        By editBtn = By.xpath("//td[(text()='"+name+"')]/parent::tr/td/input[contains(@id, 'imgEdit')][contains(@id, 'Event')]");

        try {
            waitForElement(publishBtn);
            findElement(publishBtn).click();
            Thread.sleep(DEFAULT_PAUSE*2);
            driver.navigate().refresh();

            System.out.println("New "+PAGE_NAME+" has been published");

            waitForElement(editBtn);
            findElement(editBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            return findElement(workflowStateSpan).getText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String setupAsDeletedEvent(String name) throws InterruptedException {
        By editBtn = By.xpath("//td[(text()='"+name+"')]/parent::tr/td/input[contains(@id, 'imgEdit')][contains(@id, 'Event')]");
        ArrayList<String> tabs = new ArrayList<> (driver.getWindowHandles());

        try {
            driver.switchTo().window(tabs.get(0));
            waitForElement(commentsTxt);
            findElement(commentsTxt).sendKeys("Removing "+PAGE_NAME);
            findElement(deleteBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(editBtn);
            findElement(editBtn).click();
            Thread.sleep(DEFAULT_PAUSE);

            waitForElement(currentContentSpan);

            System.out.println(PAGE_NAME+" set up as deleted");
            return findElement(currentContentSpan).getText();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Boolean removeEvent(JSONObject data, String name) throws InterruptedException {
        By editBtn = By.xpath("//td[(text()='"+name+"')]/parent::tr/td/input[contains(@id, 'imgEdit')][contains(@id, 'Event')]");

        try {
            if (findElement(currentContentSpan).getText().equals(WorkflowState.DELETE_PENDING.state())) {

                waitForElement(commentsTxt);
                findElement(commentsTxt).sendKeys("Approving "+PAGE_NAME+" removal");
                findElement(publishBtn).click();

                Thread.sleep(DEFAULT_PAUSE*2);
                driver.navigate().refresh();
                Thread.sleep(DEFAULT_PAUSE);

                System.out.println("New "+PAGE_NAME+" has been removed");

                if (doesElementExist(editBtn)){
                    return false;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public Boolean goToPublicSite (JSONObject data, String name) throws InterruptedException {
        try {
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

            return true;

        } catch (NullPointerException e) {
        }

        return null;
    }

    public boolean isModuleCorrect() {
        waitForElement(moduleTime);
        try {
            if (!findElement(moduleTime).getText().contains(timeZoneDisplayName)){
                System.out.println(findElement(moduleTime).getText());
                System.out.println(timeZoneDisplayName);
                System.out.println("Module check fails");
                return false;
            }
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean isRSSCorrect(JSONObject data, String name) throws InterruptedException {
        waitForElement(tabEvent);
        findElement(tabEvent).click();

        Thread.sleep(1000*60*5);

        waitForElement(iconRSS);
        String URLValue = findElement(iconRSS).getAttribute("href");

        List<WebElement> events = findElements(moduleHeadline);
        for (WebElement event : events) {
            if (event.getText().contentEquals(data.get("event_title").toString())) {
                try {
                    if (!getRSSValue(URLValue, name).contains(transferTimeZoneFormat(data.get("time_zone").toString()))) {
                        System.out.println(getRSSValue(URLValue, name));
                        System.out.println(transferTimeZoneFormat(data.get("time_zone").toString()));
                        return false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            } else {
                System.out.println("Event not found in Future Event");
            }
        }
        return false;
    }

    public boolean isCalendarCorrect(JSONObject data, String name) {
        waitForElement(calendarDownload);
        String url = findElement(calendarDownload).getAttribute("href");

        try {
            downloadUsingStream(url, pathToFile + calFile);
            FileInputStream fin = new FileInputStream(pathToFile + calFile);
            CalendarBuilder builder = new CalendarBuilder();
            net.fortuna.ical4j.model.Calendar calendar = builder.build(fin);
            ComponentList cl = calendar.getComponents();
            Component vTimezone = cl.getComponent("VTIMEZONE");
            String tzname = vTimezone.toString();
            if (!tzname.contains("EST")){
                System.out.println(tzname);
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e){
            e.printStackTrace();
        }

        return true;
    }

    public boolean isFeedCorrect(JSONObject data, String name) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(PathToJsonFile + jsonFile));
            requestBody = jsonObject.toString();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(ParseException e){
            e.printStackTrace();
        }
        JSONObject eventObject = postHttp(chicagotestRequestUrl, requestBody, name);
        if (!eventObject.get("TimeZone").toString().contentEquals(timeZoneDisplayName)){
            System.out.println(eventObject.get("TimeZone").toString());
            System.out.println(timeZoneDisplayName);
            return false;
        }
        return true;
    }

    public boolean isMovedToPastEvent(JSONObject data, String name) throws InterruptedException {
        Thread.sleep(1000*60*5);
        String response = new String();
        try {
            response = getHttp(chicagotestPastEventUrl);
        }catch(Exception e){
            e.printStackTrace();
        }
        if(!response.contains(name)){
            System.out.println(response);
        }
        return true;
    }

    private String transferTimeZoneFormat(String timeZone){
        return timeZone.split("\\)")[0].split("UTC")[1].replace(":","");
    }

    private static String getRSSValue(String URLValue, String name) throws Exception {
         String value = new String();
         URL urlValue = new URL(URLValue);
        InputStream in = urlValue.openStream();
        SAXBuilder builder = new SAXBuilder();
        try {
            Document document = (Document) builder.build(in);
            Element rootNode = document.getRootElement();
            Element channel = rootNode.getChild("channel");
            List elements = channel.getChildren("item");

            for (int ii = 0; ii < elements.size(); ii++) {
                Element node = (Element) elements.get(ii);
                String title = node.getChildText("title");
                System.out.println(title);
                if (title.contains(name)){
                    value = node.getChildText("pubDate");
                }
                /*System.out.println("Spot: " + node.getChildText("Quote"));
                System.out.println("Open: " + node.getChildText("Open"));
                System.out.println("Close: " + node.getChildText("Close"));*/
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (JDOMException e) {
            System.out.println(e.getMessage());
        }
        return value;
    }

    private static void downloadUsingStream(String urlStr, String file) throws IOException{
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

    private JSONObject postHttp(String url, String body, String name) {

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(body);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);

            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            try {
                JSONParser parser = new JSONParser();
                Object resultObject = parser.parse(json);
                JSONObject obj =(JSONObject) resultObject;
                JSONArray arr = (JSONArray) obj.get("GetEventListResult");
                for (int i = 0; i < arr.size(); i++) {
                    JSONObject e = (JSONObject) arr.get(i);
                    if (e.get("Title").toString().contentEquals(name)) {
                        return e;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException ex) {
        }
        return null;
    }

    private static String getHttp(String urlInput) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlInput);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }

    private static Calendar getCalUnderTimeZone(String timeZone, Calendar cal){
        switch(timeZone){
            case "(UTC-08:00) Pacific Standard Time":
                cal.add(Calendar.HOUR, -3);
                return cal;
            case "(UTC-07:00) Mountain Standard Time":
                cal.add(Calendar.HOUR, -2);
                return cal;
            case "(UTC-06:00) Central Standard Time":
                cal.add(Calendar.HOUR, -1);
                return cal;
            case "(UTC-05:00) Eastern Standard Time":
                return cal;
            case "(UTC-04:00) Atlantic Standard Time":
                cal.add(Calendar.HOUR, 1);
                return cal;
            case "(UTC+00:00) Greenwich Standard Time":
                cal.add(Calendar.HOUR, 5);
                return cal;
            case "(UTC+02:00) E. Europe Standard Time":
                cal.add(Calendar.HOUR, 7);
                return cal;
            default:
                return null;
        }
    }

}
