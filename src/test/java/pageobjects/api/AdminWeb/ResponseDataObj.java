package pageobjects.api.AdminWeb;

import org.json.simple.JSONObject;

/**
 * Created by philipsushkov on 2017-08-02.
 */

public class ResponseDataObj {
    private static JSONObject jsonResponse;
    private static long responseTime;
    private static int responseCode;

    public void setJsonResponse(JSONObject jsonResponseHar) {
        this.jsonResponse = jsonResponseHar;
    }

    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

    public void setResponseTime(long responseTimeHar) {
        this.responseTime = responseTimeHar;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseCode(int responseCodeHar) {
        this.responseCode = responseCodeHar;
    }

    public long getResponseCode() {
        return responseCode;
    }

}
