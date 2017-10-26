package pageobjects.api.PressReleaseFilter;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FiltersAPI {
    public static String url = "https://flfa9qo2eb.execute-api.us-east-1.amazonaws.com/dev/v1/filters";
    public static String xApiKey = "w90bwvYsrg8eEtTbFsPsManZM8aOWLPGKjGhZ0Ue";
    public static String xQ4OrgId = "ea98f2a4d7c94bf294bb929d67c266e5";
    public static String contentType = "application/json";
    
    public static String nameFieldPath = System.getProperty("user.dir") + "/src/test/java/pageobjects/api/PressReleaseFilter/Json/AddNewEditFilter/nameField.json";
    public static String termTypeFieldPath  = System.getProperty("user.dir") + "/src/test/java/pageobjects/api/PressReleaseFilter/Json/AddNewEditFilter/termTypeField.json";
    public static String termFieldPath = System.getProperty("user.dir") + "/src/test/java/pageobjects/api/PressReleaseFilter/Json/AddNewEditFilter/termField.json";
    public static String typeFieldPath = System.getProperty("user.dir") + "/src/test/java/pageobjects/api/PressReleaseFilter/Json/AddNewEditFilter/typeField.json";
    public static String sortOrderFieldPath = System.getProperty("user.dir") + "/src/test/java/pageobjects/api/PressReleaseFilter/Json/AddNewEditFilter/sortOrderField.json";
    public static String idFieldPath = System.getProperty("user.dir") + "/src/test/java/pageobjects/api/PressReleaseFilter/Json/AddNewEditFilter/idField.json";

    public static String addFilterResponsePath = System.getProperty("user.dir") + "/src/test/java/pageobjects/api/PressReleaseFilter/Json/addFilterResponse.json";
    public static String editFilterResponsePath = System.getProperty("user.dir") + "/src/test/java/pageobjects/api/PressReleaseFilter/Json/editFilterResponse.json";
    public static String getAllFiltersResponsePath = System.getProperty("user.dir") + "/src/test/java/pageobjects/api/PressReleaseFilter/Json/getAllFilterResponse.json";
    public static String getOneFilterResponsePath = System.getProperty("user.dir") + "/src/test/java/pageobjects/api/PressReleaseFilter/Json/getOneFilterResponse.json";
    public static String deleteFilterResponsePath = System.getProperty("user.dir") + "/src/test/java/pageobjects/api/PressReleaseFilter/Json/deleteFilterResponse.json";

    public static String addFilter () throws Exception {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "/src/test/java/pageobjects/api/PressReleaseFilter/Json/body.json"));
        JSONObject jsonObject = (JSONObject) obj;
        StringBuffer result = new StringBuffer();
        String UUID = new String();

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);
            post.setHeader("x-api-key", xApiKey);
            post.setHeader("x-q4org-id", xQ4OrgId);
            post.setHeader("content-type", contentType);

            String payload = jsonObject.toString();

            StringEntity entity = new StringEntity(payload,
                    ContentType.APPLICATION_FORM_URLENCODED);
            post.setEntity(entity);
            HttpResponse response = client.execute(post);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser responseParser = new JSONParser();
        JSONObject jsonResponse = (JSONObject) responseParser.parse(result.toString());

        JSONObject dataObject = (JSONObject) jsonResponse.get("Data");

        UUID = dataObject.get("Id").toString();

        System.out.println("Printing POST response");
        System.out.println(jsonResponse);
        try (FileWriter file = new FileWriter(addFilterResponsePath)) {
            file.write(jsonResponse.toJSONString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return UUID;
    }

    public static Boolean editFilter (String UUID) throws Exception {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "/src/test/java/pageobjects/api/PressReleaseFilter/Json/editedBody.json"));
        JSONObject jsonObject = (JSONObject) obj;
        StringBuffer result = new StringBuffer();
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpPatch patch = new HttpPatch(url + "/" + UUID);
            patch.setHeader("x-api-key", xApiKey);
            patch.setHeader("x-q4org-id", xQ4OrgId);
            patch.setHeader("content-type", contentType);

            String payload = jsonObject.toString();

            StringEntity entity = new StringEntity(payload,
                    ContentType.APPLICATION_FORM_URLENCODED);
            patch.setEntity(entity);
            HttpResponse response = client.execute(patch);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser responseParser = new JSONParser();
        JSONObject jsonResponse = (JSONObject) responseParser.parse(result.toString());
        System.out.println("Printing PATCH response");
        System.out.println(jsonResponse);
        try (FileWriter file = new FileWriter(editFilterResponsePath)) {
            file.write(jsonResponse.toJSONString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean getAllFilters () throws Exception {
        StringBuffer result = new StringBuffer();

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(url);
            get.setHeader("x-api-key", xApiKey);
            get.setHeader("x-q4org-id", xQ4OrgId);

            HttpResponse response = client.execute(get);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser responseParser = new JSONParser();
        JSONObject jsonResponse = (JSONObject) responseParser.parse(result.toString());
        System.out.println("Printing GET response");
        System.out.println(jsonResponse);
        try (FileWriter file = new FileWriter(getAllFiltersResponsePath)) {
            file.write(jsonResponse.toJSONString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean getOneFilter (String UUID) throws Exception {
        StringBuffer result = new StringBuffer();

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(url + "/" + UUID);
            get.setHeader("x-api-key", xApiKey);
            get.setHeader("x-q4org-id", xQ4OrgId);

            HttpResponse response = client.execute(get);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser responseParser = new JSONParser();
        JSONObject jsonResponse = (JSONObject) responseParser.parse(result.toString());
        System.out.println("Printing GET ONE response");
        System.out.println(jsonResponse);
        try (FileWriter file = new FileWriter(getOneFilterResponsePath)) {
            file.write(jsonResponse.toJSONString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean deleteFilter (String UUID) throws Exception {
        StringBuffer result = new StringBuffer();

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpDelete delete = new HttpDelete(url + "/" + UUID);
            delete.setHeader("x-api-key", xApiKey);
            delete.setHeader("x-q4org-id", xQ4OrgId);

            HttpResponse response = client.execute(delete);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser responseParser = new JSONParser();
        JSONObject jsonResponse = (JSONObject) responseParser.parse(result.toString());
        System.out.println("Printing DELETE response");
        System.out.println(jsonResponse);
        try (FileWriter file = new FileWriter(deleteFilterResponsePath)) {
            file.write(jsonResponse.toJSONString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean fieldCheck (String field) {
        String dataSetPath;
        switch (field) {
            case "NAME":
                dataSetPath = nameFieldPath;
                break;
            case "TERMTYPE":
                dataSetPath = termTypeFieldPath;
                break;
            case "TERM":
                dataSetPath = termFieldPath;
                break;
            case "TYPE":
                dataSetPath = typeFieldPath;
                break;
            case "SORTORDER":
                dataSetPath = sortOrderFieldPath;
                break;
            case "ID":
                dataSetPath = idFieldPath;
                break;
            default:
                System.out.println("ERROR! Invalid field, " + field + " does not exist!");
                return false;
        }

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(dataSetPath));
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        catch (ParseException e) {e.printStackTrace();}
        catch (NullPointerException e) {e.printStackTrace();}

        //temp
        return true;

    }
    

}
