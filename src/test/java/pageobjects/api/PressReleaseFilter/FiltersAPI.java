package pageobjects.api.PressReleaseFilter;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.http.HttpResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;


public class FiltersAPI {
    public static boolean addFilter () throws Exception {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("/Users/victorl/Documents/GitHub/QA-WebCMS-Test/src/test/java/pageobjects/api/PressReleaseFilter/Json/body.json"));
            JSONObject jsonObject = (JSONObject) obj;
            HttpResponse<JsonNode> response = Unirest.post("https://flfa9qo2eb.execute-api.us-east-1.amazonaws.com/dev/v1/filters")
                    .header("x-api-key", "w90bwvYsrg8eEtTbFsPsManZM8aOWLPGKjGhZ0Ue")
                    .header("x-q4org-id", "ea98f2a4d7c94bf294bb929d67c266e5")
                    .header("content-type", "application/json")
                    .body(obj)
                    .asJson();
            System.out.println(response.getBody());
            return true;
        }
        catch (UnirestException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean getAllFilters () throws Exception {
        try {
            HttpResponse<JsonNode> response = Unirest.get("https://flfa9qo2eb.execute-api.us-east-1.amazonaws.com/dev/v1/filters")
                    .header("x-api-key", "w90bwvYsrg8eEtTbFsPsManZM8aOWLPGKjGhZ0Ue")
                    .header("x-q4org-id", "ea98f2a4d7c94bf294bb929d67c266e5")
                    .asJson();
            System.out.println(response.getBody());
            return true;
        }
        catch (UnirestException e)
        {
            e.printStackTrace();
            return false;
        }

    }
}
