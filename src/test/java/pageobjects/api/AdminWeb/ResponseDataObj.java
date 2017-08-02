package pageobjects.api.AdminWeb;

import org.json.simple.JSONObject;

/**
 * Created by philipsushkov on 2017-08-02.
 */

public class ResponseDataObj {
    private static JSONObject jsonResponse;

    public void setJsonResponse(JSONObject jsonResponseHar) {
        this.jsonResponse = jsonResponseHar;
    }

    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

}
