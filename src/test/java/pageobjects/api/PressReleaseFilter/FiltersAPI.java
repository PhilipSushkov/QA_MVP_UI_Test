package pageobjects.api.PressReleaseFilter;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.*;
import static specs.AbstractSpec.propFilterAPI;


import static util.Functions.getSchemaValidation;

public class FiltersAPI {
    private static String url;
    private static String xApiKey;
    private static String xQ4OrgId;
    private static String contentType;

    private static String workingBodyPath;
    private static String nameFieldPath;
    private static String termTypeFieldPath;
    private static String termFieldPath;
    private static String typeFieldPath;
    private static String sortOrderFieldPath;
    private static String idFieldPath;

    private static String addFilterResponsePath;
    private static String editFilterResponsePath;
    private static String getAllFiltersResponsePath;
    private static String getOneFilterResponsePath;
    private static String deleteFilterResponsePath;

    private static String expectedDeleteFilterResponse;
    private static String expectedGetOneFilterResponse;

    private static String getOneFilterResponseFileName;
    private static String getOneFilterResponsePartialPath;

    private static String getOneFilterSchemaFileName;
    private static String getOneFilterSchemaPartialPath;

    private static String getAllFiltersResponseFileName;
    private static String getAllFiltersResponsePartialPath;

    private static String getAllFiltersSchemaFileName;
    private static String getAllFiltersSchemaPartialPath;

    private static String placeholderUUID;
    private static String shortUUID;
    private static String longUUID;

    public FiltersAPI() {
        url = propFilterAPI.getProperty("urlProp");
        xApiKey = propFilterAPI.getProperty("xApiKeyProp");
        xQ4OrgId = propFilterAPI.getProperty("xQ4OrgIdProp");
        contentType = propFilterAPI.getProperty("contentTypeProp");

        workingBodyPath = System.getProperty("user.dir") + propFilterAPI.getProperty("workingBodyPathProp");
        nameFieldPath = System.getProperty("user.dir") + propFilterAPI.getProperty("nameFieldPathProp");
        termTypeFieldPath = System.getProperty("user.dir") + propFilterAPI.getProperty("termTypeFieldPathProp");
        termFieldPath = System.getProperty("user.dir") + propFilterAPI.getProperty("termFieldPathProp");
        typeFieldPath = System.getProperty("user.dir") + propFilterAPI.getProperty("typeFieldPathProp");
        sortOrderFieldPath = System.getProperty("user.dir") + propFilterAPI.getProperty("sortOrderFieldPathProp");
        idFieldPath = System.getProperty("user.dir") + propFilterAPI.getProperty("idFieldPathProp");

        addFilterResponsePath = System.getProperty("user.dir") + propFilterAPI.getProperty("addFilterResponsePathProp");
        editFilterResponsePath = System.getProperty("user.dir") + propFilterAPI.getProperty("editFilterResponsePathProp");
        getAllFiltersResponsePath = System.getProperty("user.dir") + propFilterAPI.getProperty("getAllFiltersResponsePathProp");
        getOneFilterResponsePath = System.getProperty("user.dir") + propFilterAPI.getProperty("getOneFilterResponsePathProp");
        deleteFilterResponsePath = System.getProperty("user.dir") + propFilterAPI.getProperty("deleteFilterResponsePathProp");

        expectedDeleteFilterResponse = System.getProperty("user.dir") + propFilterAPI.getProperty("expectedDeleteFilterResponseProp");
        expectedGetOneFilterResponse = System.getProperty("user.dir") + propFilterAPI.getProperty("expectedGetOneFilterResponseProp");

        getOneFilterResponseFileName = propFilterAPI.getProperty("getOneFilterResponseFileNameProp");
        getOneFilterResponsePartialPath = System.getProperty("user.dir") + propFilterAPI.getProperty("getOneFilterResponsePartialPathProp");

        getOneFilterSchemaFileName = propFilterAPI.getProperty("getOneFilterSchemaFileNameProp");
        getOneFilterSchemaPartialPath = System.getProperty("user.dir") + propFilterAPI.getProperty("getOneFilterSchemaPartialPathProp");

        getAllFiltersResponseFileName = propFilterAPI.getProperty("getAllFiltersResponseFileNameProp");
        getAllFiltersResponsePartialPath = System.getProperty("user.dir") + propFilterAPI.getProperty("getAllFiltersResponsePartialPathProp");

        getAllFiltersSchemaFileName = propFilterAPI.getProperty("getAllFiltersSchemaFileNameProp");
        getAllFiltersSchemaPartialPath = System.getProperty("user.dir") + propFilterAPI.getProperty("getAllFiltersSchemaPartialPathProp");

        placeholderUUID = propFilterAPI.getProperty("placeholderUUIDProp");
        shortUUID = propFilterAPI.getProperty("shortUUIDProp");
        longUUID = propFilterAPI.getProperty("longUUIDProp");
    }

    public JSONObject addEditFilter (String mode, JSONObject jsonObject, String UUID) throws Exception{
        StringBuffer result = new StringBuffer();
        String responsePath = null;
        String payload;
        HttpResponse response = null;
        try {
            HttpClient client = HttpClientBuilder.create().build();
            switch (mode) {
                case "POST":
                    responsePath = addFilterResponsePath;
                    HttpPost post = new HttpPost(url);
                    post.setHeader("x-api-key", xApiKey);
                    post.setHeader("x-q4org-id", xQ4OrgId);
                    post.setHeader("content-type", contentType);

                    payload = jsonObject.toString();

                    StringEntity entity1 = new StringEntity(payload, ContentType.APPLICATION_FORM_URLENCODED);
                    post.setEntity(entity1);
                    response = client.execute(post);
                    break;
                case "PATCH":
                    responsePath = editFilterResponsePath;
                    HttpPatch patch = new HttpPatch(url + "/" + UUID);
                    patch.setHeader("x-api-key", xApiKey);
                    patch.setHeader("x-q4org-id", xQ4OrgId);
                    patch.setHeader("content-type", contentType);

                    payload = jsonObject.toString();

                    StringEntity entity2 = new StringEntity(payload, ContentType.APPLICATION_FORM_URLENCODED);
                    patch.setEntity(entity2);
                    response = client.execute(patch);
                    break;
            }
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

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

        try (FileWriter file = new FileWriter(responsePath)) {
            file.write(jsonResponse.toJSONString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    public JSONObject getFilters (String mode, String UUID) throws Exception{
        StringBuffer result = new StringBuffer();
        String requestPath = null;
        String responsePath = null;

        switch (mode) {
            case "ALL":
                requestPath = url;
                responsePath = getAllFiltersResponsePath;
                break;
            case "ONE":
                requestPath = url + "/" + UUID;
                responsePath = getOneFilterResponsePath;
                break;
        }

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(requestPath);
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
        //System.out.println("Printing GET ONE response");
        //System.out.println(jsonResponse);
        try (FileWriter file = new FileWriter(responsePath)) {
            file.write(jsonResponse.toJSONString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    public JSONObject deleteFilter (String UUID) throws Exception {
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
        //System.out.println("Printing DELETE response");
        //System.out.println(jsonResponse);
        try (FileWriter file = new FileWriter(deleteFilterResponsePath)) {
            file.write(jsonResponse.toJSONString());
            file.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    public String dataSetPathSelector (String field) {
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
                return null;
        }
        return dataSetPath;
    }

    public boolean addFilterCheck (String field) {
        String dataSetPath = dataSetPathSelector(field);
        Boolean pass = true;
        int ii=0;

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(dataSetPath));
            JSONArray data = (JSONArray) jsonObject.get("data");
            for(ii=0; ii < data.size(); ii++) {
                String name = null;
                String message = null;
                String messageExpected = null;
                int idActualLength = 0;
                int idExpectedLength = 0;
                JSONObject dataGroup = (JSONObject) data.get(ii);

                JSONObject jsonResponse = addEditFilter("POST", dataGroup, placeholderUUID);

                ////ACTUAL RESULTS////
                Boolean success = (Boolean) jsonResponse.get("Success");
                if (!success) {
                    message = jsonResponse.get("Message").toString();
                } else {
                    JSONObject Data = (JSONObject) jsonResponse.get("Data");
                    String id = Data.get("Id").toString();
                    idActualLength = id.length();
                    deleteFilter(id);
                }

                ////EXPECTED RESULTS////
                JSONArray dataExpectedArray = (JSONArray) dataGroup.get("expectedResults");
                JSONObject dataExpected = (JSONObject) dataExpectedArray.get(0);
                Boolean successExpected = (Boolean) dataExpected.get("Success");
                if (!successExpected) {
                    messageExpected = dataExpected.get("Message").toString();
                } else {
                    JSONObject DataExpected = (JSONObject) dataExpected.get("Data");
                    String idExpected = DataExpected.get("Id").toString();
                    idExpectedLength = idExpected.length();
                }

                name = dataGroup.get("name").toString();
                System.out.println("TESTING Add for: " + name);
                System.out.println("Actual Response:\t" + jsonResponse);
                System.out.println("Expected Response:\t" + dataExpected);

                if ((success && successExpected && idActualLength == idExpectedLength) || (!success && !successExpected && message.equals(messageExpected))) {
                    System.out.println("RESULT: " + name + "    ----> PASS");
                } else {
                    System.out.println("RESULT: " + name + "    ----> FAIL");
                    pass = false;
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (pass) {
            System.out.println("****" + field + " TESTING PASS****");
        }
        else {
            System.out.println("****" + field + " TESTING FAIL****");
        }
        System.out.println(ii + " CASES TESTED\n");
        return pass;
    }

    public boolean editFilterCheck (String field) {
        String dataSetPath = dataSetPathSelector(field);
        Boolean pass = true;
        int ii=0;

        try {
            JSONParser parser1 = new JSONParser();
            Object obj = parser1.parse(new FileReader(workingBodyPath));
            JSONObject workingBody = (JSONObject) obj;

            JSONObject workingBodyResponse = addEditFilter("POST", workingBody, placeholderUUID);
            //System.out.println(workingBodyResponse);

            JSONObject workingBodyData = (JSONObject) workingBodyResponse.get("Data");
            String UUID = workingBodyData.get("Id").toString();

            JSONParser parser2 = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser2.parse(new FileReader(dataSetPath));
            JSONArray data = (JSONArray) jsonObject.get("data");
            for(ii=0; ii < data.size(); ii++) {
                String name = null;
                String message = "temp1";
                String messageExpected = "temp2";
                JSONObject dataGroup = (JSONObject) data.get(ii);

                //JSONObject jsonResponse = FiltersAPI.editFilter(dataGroup, UUID);
                JSONObject jsonResponse = addEditFilter("PATCH", dataGroup, UUID);

                ////ACTUAL RESULTS////
                Boolean success = (Boolean) jsonResponse.get("Success");
                if (!success) {
                    message = jsonResponse.get("Message").toString();
                }

                ////EXPECTED RESULTS////
                JSONArray dataExpectedArray = (JSONArray) dataGroup.get("expectedResults");
                JSONObject dataExpected = (JSONObject) dataExpectedArray.get(0);
                Boolean successExpected = (Boolean) dataExpected.get("Success");
                if (!successExpected) {
                    messageExpected = dataExpected.get("Message").toString();
                }

                name = dataGroup.get("name").toString();
                System.out.println("TESTING Edit for: " + name);
                System.out.println("Actual Response:\t" + jsonResponse);
                System.out.println("Expected Response:\t" + dataExpected);

                if ((!success && !successExpected && message.equals(messageExpected)) || (success && successExpected)) {
                    System.out.println("RESULT: " + name + "    ----> PASS");
                } else {
                    System.out.println("RESULT: " + name + "    ----> FAIL");
                    pass = false;
                }
                System.out.println();
            }
            deleteFilter(UUID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (pass) {
            System.out.println("****" + field + " TESTING PASS****");
        }
        else {
            System.out.println("****" + field + " TESTING FAIL****");
        }
        System.out.println(ii + " CASES TESTED\n");
        return pass;

    }

    public boolean deleteFilterCheck(String mode) throws Exception{
        String testUUID = null;
        String UUID = null;
        Boolean repeat = false;
        Boolean pass = false;

        if(mode.equals("VALID") || mode.equals("REPEAT")) {
            try {
                JSONParser parser1 = new JSONParser();
                Object obj = parser1.parse(new FileReader(workingBodyPath));
                JSONObject workingBody = (JSONObject) obj;

                JSONObject workingBodyResponse = addEditFilter("POST", workingBody, placeholderUUID);

                JSONObject workingBodyData = (JSONObject) workingBodyResponse.get("Data");
                UUID = workingBodyData.get("Id").toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JSONParser parser2 = new JSONParser();
        Object obj2 = parser2.parse(new FileReader(expectedDeleteFilterResponse));
        JSONObject expectedResponse = (JSONObject) obj2;
        JSONObject expectedResponseObject = new JSONObject();
        String expectedSuccessResponse = null;
        String expectedMessageResponse = null;
        String actualSuccessResponse = null;
        String actualMessageResponse = null;

        switch (mode) {
            case "INVALID":
                testUUID = placeholderUUID;
                break;
            case "SHORT":
                testUUID = shortUUID;
                break;
            case "LONG":
                testUUID = longUUID;
                break;
            case "VALID":
                testUUID = UUID;
                break;
            case "REPEAT":
                testUUID = UUID;
                repeat = true;
        }

        JSONObject jsonResponse = deleteFilter(testUUID);
        if (repeat) {
            jsonResponse = deleteFilter(testUUID);
        }

        expectedResponseObject = (JSONObject) expectedResponse.get(mode);
        expectedSuccessResponse = expectedResponseObject.get("Success").toString();
        actualSuccessResponse = jsonResponse.get("Success").toString();
        if(!mode.equals("VALID")) {
            expectedMessageResponse = expectedResponseObject.get("Message").toString();
            actualMessageResponse = jsonResponse.get("Message").toString();
        }

        if(mode.equals("VALID") && actualSuccessResponse.equals(expectedSuccessResponse))
            pass = true;
        else if (actualSuccessResponse.equals(expectedSuccessResponse) && actualMessageResponse.contains(expectedMessageResponse))
            pass = true;
        else
            pass = false;

        System.out.println("TESTING Delete for: " + mode);
        System.out.println("Actual Response:\t" + expectedResponseObject);
        System.out.println("Expected Response:\t" + jsonResponse);

        if(pass)
            System.out.println("RESULT: " + mode + "    ----> PASS");
        else
            System.out.println("RESULT: " + mode + "    ----> FAIL");
        System.out.println();

        return pass;
    }

    public boolean getOneFilterCheck(String mode) throws Exception {
        String testUUID = null;
        String UUID = null;
        Boolean pass = false;

        System.out.println("TESTING Get One Filter for: " + mode);

        if(mode.equals("VALID")) {
            try {
                JSONParser parser1 = new JSONParser();
                Object obj = parser1.parse(new FileReader(workingBodyPath));
                JSONObject workingBody = (JSONObject) obj;

                JSONObject workingBodyResponse = addEditFilter("POST", workingBody, placeholderUUID);

                JSONObject workingBodyData = (JSONObject) workingBodyResponse.get("Data");
                UUID = workingBodyData.get("Id").toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JSONParser parser2 = new JSONParser();
        Object obj2 = parser2.parse(new FileReader(expectedGetOneFilterResponse));
        JSONObject expectedResponse = (JSONObject) obj2;
        JSONObject expectedResponseObject = new JSONObject();
        String expectedSuccessResponse = null;
        String expectedMessageResponse = null;
        String actualSuccessResponse = null;
        String actualMessageResponse = null;

        switch (mode) {
            case "INVALID":
                testUUID = placeholderUUID;
                break;
            case "SHORT":
                testUUID = shortUUID;
                break;
            case "LONG":
                testUUID = longUUID;
                break;
            case "VALID":
                testUUID = UUID;
                break;
        }

        JSONObject jsonResponse = getFilters("ONE", testUUID);

        expectedResponseObject = (JSONObject) expectedResponse.get(mode);
        expectedSuccessResponse = expectedResponseObject.get("Success").toString();
        actualSuccessResponse = jsonResponse.get("Success").toString();
        if(!mode.equals("VALID")) {
            expectedMessageResponse = expectedResponseObject.get("Message").toString();
            actualMessageResponse = jsonResponse.get("Message").toString();
        }

        System.out.println("Actual Response:\t" + jsonResponse);
        System.out.println("Expected Response:\t" + expectedResponseObject);

        if(mode.equals("VALID") && actualSuccessResponse.equals(expectedSuccessResponse)) {
            pass = getSchemaValidation(getOneFilterSchemaPartialPath, getOneFilterResponsePartialPath, getOneFilterSchemaFileName, getOneFilterResponseFileName);
        }
        else if (actualSuccessResponse.equals(expectedSuccessResponse) && actualMessageResponse.contains(expectedMessageResponse))
            pass = true;
        else
            pass = false;

        if(pass)
            System.out.println("RESULT: " + mode + "    ----> PASS");
        else
            System.out.println("RESULT: " + mode + "    ----> FAIL");
        System.out.println();

        if(UUID != null){
            deleteFilter(UUID);
        }

        return pass;
    }

    public boolean getAllFiltersCheck () throws Exception {
        Boolean pass;

        System.out.println("TESTING Get All Filters Schema");
        getFilters("ALL", placeholderUUID);
        pass = getSchemaValidation(getAllFiltersSchemaPartialPath, getAllFiltersResponsePartialPath, getAllFiltersSchemaFileName, getAllFiltersResponseFileName);

        if(pass)
            System.out.println("RESULT: Get All Filters Schema    ----> PASS");
        else
            System.out.println("RESULT: Get All Filters Schema    ----> FAIL");
        System.out.println();

        return pass;

    }
}
