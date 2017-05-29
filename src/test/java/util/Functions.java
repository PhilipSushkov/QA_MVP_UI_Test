package util;

import com.sun.mail.gimap.GmailFolder;
import com.sun.mail.gimap.GmailRawSearchTerm;
import com.sun.mail.gimap.GmailStore;
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
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.Properties;

import javax.mail.*;

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

    public static boolean compareImages (String exp, String cur, String diff) {
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

    public static String takeScreenshot (WebDriver driver, String sShotName, String sPageName) {
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

    public static Message getRecentMail(String user, String password, String subjectID) {

        // Gets the first email message whose subject contains subjectID
        // Use with javax.mail api

        try {

            Properties properties = new Properties();

            properties.put("mail.pop3.host", "pop.gmail.com");
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            Store store = emailSession.getStore("pop3s");

            store.connect("pop.gmail.com", user, password);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();

            for (int i = 0; i < messages.length; i++) {
                if (messages[i].getSubject().contains(subjectID)) {
                    return messages[i];
                }
            }

            return null;

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Message getSpecificMail(String user, String password, String subjectID, String date) {

        try {

            Properties properties = new Properties();

            properties.put("mail.pop3.host", "pop.gmail.com");
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            Store store = emailSession.getStore("pop3s");

            store.connect("pop.gmail.com", user, password);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");

            Message[] messages = emailFolder.getMessages();


            for (int i = 0; i < messages.length; i++) {
                if (messages[i].getSubject().contains(subjectID) && (date.equals(dateFormat.format(messages[i].getSentDate())))) {
                    return messages[i];
                }
            }

            return null;

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void deleteMail(String user, String password, String subjectID) {
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


    public static void cleanTextFields(List<WebElement> fields) {
        for (WebElement e : fields) {
            e.clear();
        }
    }
}
