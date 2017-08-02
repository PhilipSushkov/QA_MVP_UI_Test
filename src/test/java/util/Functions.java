package util;

import com.jayway.jsonpath.JsonPath;
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import net.lightbody.bmp.core.har.Har;
import org.apache.commons.io.FileUtils;
import org.im4java.core.CompareCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.StandardStream;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.*;

import java.io.*;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.Properties;

import javax.mail.*;
import javax.mail.search.SubjectTerm;
import com.sun.mail.gimap.GmailFolder;
import com.sun.mail.gimap.GmailRawSearchTerm;
import com.sun.mail.gimap.GmailStore;

/**
 * Created by philipsushkov on 2016-12-08.
 */

public class Functions {

    public static Properties ConnectToPropUI(String sPathSharedUIMap) {
        Properties propUI = null;
        String currentDir = null;

        try {
            propUI = new Properties();
            currentDir = System.getProperty("user.dir") + "/src/test/java/specs/";
            propUI.load(new FileInputStream(currentDir + sPathSharedUIMap));
            //System.out.println("File "+currentDir + sPathSharedUIMap+" load properly!");
        } catch (IOException e) {
            System.out.println("File "+currentDir + sPathSharedUIMap+" didn't load properly!");
        }

        return propUI;
    }

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static void WriteArrayToJSON(String[] args, String sPathToFile, String sArrayName) {
        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();

        try {
            obj = (JSONObject) parser.parse(new FileReader(sPathToFile));
        } catch (ParseException e) {
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0; i<args.length; i++) {
            list.add(args[i]);
        }

        obj.put(sArrayName, list);

        try {
            FileWriter file = new FileWriter(sPathToFile);
            file.write(obj.toJSONString());
            file.flush();
            file.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String[] ReadArrayFromJSON(String sPathToFile, String sArrayName) {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader(sPathToFile));
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray items = (JSONArray) jsonObject.get(sArrayName);
            String temp[] = new String[items.size()];

            int i = 0;
            for (Iterator<String> iterator = items.iterator(); iterator.hasNext(); i++) {
                temp[i] = iterator.next();
            }

            return temp;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Clear JSON file
    public static void ClearJSONfile(String sPathToFile) {
        try {
            FileWriter fileClear = new FileWriter(sPathToFile);
            fileClear.write("");
            fileClear.flush();
            fileClear.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONArray RemoveArrayItem(JSONArray jsonArr, String item) {
        for (int i=0; i<jsonArr.size(); i++) {
            if (jsonArr.get(i).toString().contains(item)) {
                jsonArr.remove(i);
                i--;
            }
        }
        return jsonArr;
    }

    public static String UrlAddSlash(String searchTerm, String sSlash, String sHttp) {
        searchTerm = searchTerm.trim();

        String sFirstChar = searchTerm.substring(0, 7);

        if (!sFirstChar.equals(sHttp)) {
            searchTerm = sHttp + searchTerm;
        }

        String sLastChar = searchTerm.substring(searchTerm.length()-1, searchTerm.length());
        if (sLastChar.equals(sSlash)) {
            return searchTerm;
        } else {
            searchTerm = searchTerm + sSlash;
            return searchTerm;
        }

    }

    public static String GetVersion(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String sVersion = js.executeScript("return GetVersionNumber();").toString();
        return sVersion;
    }

    public static int GetResponseCode(String urlString) throws IOException {
        try {
            URL url = new URL(urlString);
            HttpURLConnection huc = (HttpURLConnection)url.openConnection();
            huc.setRequestMethod("GET");
            //System.out.println(huc.getContentLength());
            //huc.connect();

            //System.out.println(Integer.toString(huc.getContentLength()));
            //System.out.println(Integer.toString(huc.getInputStream().available()));

            int iResponseCode = huc.getResponseCode();
            huc.disconnect();

            return iResponseCode;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 404;
        }
    }

    public static int GetResponseCodeSsl(String urlString) throws IOException {
        try {
            URL url = new URL(urlString);
            System.setProperty("https.proxyHost", "69.172.200.167");
            System.setProperty("https.proxyPort", "443");
            HttpsURLConnection huc = (HttpsURLConnection)url.openConnection();
            huc.setRequestMethod("GET");
            //System.out.println(huc.getContentLength());
            //huc.connect();

            //System.out.println(Integer.toString(huc.getContentLength()));
            //System.out.println(Integer.toString(huc.getInputStream().available()));

            int iResponseCode = huc.getResponseCode();
            huc.disconnect();

            return iResponseCode;
        }
        catch (Exception e) {
            e.printStackTrace();
            return 404;
        }
    }

    public static boolean compareImages(String exp, String cur, String diff) {
        // This instance wraps the compare command
        CompareCmd compare = new CompareCmd();

        // For metric-output
        compare.setErrorConsumer(StandardStream.STDERR);
        IMOperation cmpOp = new IMOperation();
        // Set the compare metric
        cmpOp.metric("mae");

        // Add the expected image
        cmpOp.addImage(exp);

        // Add the current image
        cmpOp.addImage(cur);

        // This stores the difference
        cmpOp.addImage(diff);

        try {
            // Do the compare
            compare.run(cmpOp);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static String takeScreenshot(WebDriver driver, String sShotName, String sPageName) {
        String path = null;

        try {
            // Take screenshot and save it in source object
            File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

            // Define path where Screenshot will be saved
            path = System.getProperty("user.dir") + "/src/test/java/specs/ImageComparison/ScreenShots/" + sPageName + "/" + sShotName + ".png";
            //System.out.println(path);

            //Copy the source file at the screenshot path
            FileUtils.copyFile(source,  new File(path));
            System.out.println("Screenshot " + sShotName + " captured");
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot:" + e.getMessage());
        } catch (WebDriverException wde) {
            System.out.println("Failed to capture screenshot:" + wde.getMessage());
        }

        return path;
    }

    public static void cleanTextFields(List<WebElement> fields) {
        for (WebElement e : fields) {
            e.clear();
        }
    }

    public static Message getSpecificMail(String user, String password, String subjectID) throws InterruptedException, IOException {
        //Use this one if the email in question has files

        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "gimap");
        Thread.sleep(10000);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println("Email checked at: " + dateFormat.format(date));

        try {
            Session session = Session.getDefaultInstance(props, null);
            GmailStore store = (GmailStore) session.getStore("gimap");
            store.connect("imap.gmail.com", user, password);
            GmailFolder inbox = (GmailFolder) store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message[] Messages = inbox.search(new GmailRawSearchTerm("subject:" + subjectID));

            if (Messages != null){
                for (int i = 0; i < Messages.length; i++){
                    return Messages[i];
                }
            }

            inbox.close(true);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Message[] getMail(String user, String password, String subjectID) {

        // Email account must have POP/IMAP enabled

        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "gimap");

        try {
            Session session = Session.getDefaultInstance(props, null);
            GmailStore store = (GmailStore) session.getStore("gimap");
            store.connect("imap.gmail.com", user, password);
            GmailFolder inbox = (GmailFolder) store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            return inbox.search(new SubjectTerm(subjectID));

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void deleteMail(String user, String password, String subjectID) {

        // Deletes email messages with a provided subject
        // Email account must have POP/IMAP enabled

        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "gimap");

        try {
            Session session = Session.getDefaultInstance(props, null);
            GmailStore store = (GmailStore) session.getStore("gimap");
            store.connect("imap.gmail.com", user, password);
            GmailFolder inbox = (GmailFolder) store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
            Message[] foundMessages = inbox.search(new GmailRawSearchTerm("subject:"+subjectID));
            if (foundMessages != null){
                for (int i = 0; i < foundMessages.length; i++){
                    foundMessages[i].setFlag(Flags.Flag.DELETED, true);
                    System.out.println(foundMessages[i].getSubject() + ", sent at " + foundMessages[i].getSentDate() + " has been deleted");
                }
            }
            inbox.close(true);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public static String getUrlFromData(JSONObject data) {
        String sUrl;
        try {
            JSONArray paramsArray = (JSONArray) data.get("params");
            sUrl = JsonPath.read(data, "$.url") + "?";
            for (int i=0; i<paramsArray.size(); i++) {
                String[] params = paramsArray.get(i).toString().split(":");
                sUrl = sUrl + params[0] + "=" + params[1] + "&";
            }
            sUrl = removeLastChar(sUrl);
        } catch (NullPointerException e) {
            sUrl = JsonPath.read(data, "$.url");
        }
        return sUrl;
    }

    public static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    // Write HAR Data in a File
    public static void writeHarToFile(Har har, String sPathToFile, String sFileName) {
        File harFile = new File(sPathToFile + sFileName);
        try {
            har.writeTo(harFile);
        } catch (IOException e) {
            System.out.println (e.toString());
            System.out.println("Could not find file: " + sFileName);
        }
    }

    public static de.sstoehr.harreader.model.Har readHarFromFile(String sPathToFile, String sFileName) {
        HarReader harReader = new HarReader();
        try {
            de.sstoehr.harreader.model.Har har = harReader.readFromFile(new File(sPathToFile + sFileName));
            System.out.println(har.getLog().getCreator().getName());
            return har;
        } catch (HarReaderException e) {
        }

        return null;
    }

}
