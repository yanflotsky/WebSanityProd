package com.websanity.utils;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

/**
 * Utility class for API operations
 */
public class APIs {

    /**
     * Get App Settings for Mobile Capture
     * @param username User's username
     * @param password User's password
     * @param identifier Device identifier
     * @param appIdentifier Application identifier
     * @return JSON response as string
     */
    public static String getAppSettingsMobileCapture(String username, String password, String identifier, String appIdentifier) {
        String url = System.getProperty("env.url") + "/rest/user/getAppSettings";
        String requestBody = mobileCaptureSettingsBody(username, password, identifier, appIdentifier);
        return simplePostRequest(url, requestBody);
    }

    /**
     * Create request body for Mobile Capture settings
     * @param username User's username
     * @param password User's password
     * @param identifier Device identifier
     * @param appIdentifier Application identifier
     * @return JSON request body as string
     */
    public static String mobileCaptureSettingsBody(String username, String password, String identifier, String appIdentifier) {
        String b = "[\n" +
                "    {\n" +
                "        \"class\": \"telemessage.web.services.IPDeviceAuthenticationDetails\",\n" +
                "        \"username\": \"" + username + "\",\n" +
                "        \"password\": \"" + password + "\",\n" +
                "        \"identifier\": \"" + identifier + "\",\n" +
                "        \"appIdentifier\": \"" + appIdentifier + "\"\n" +
                "    }\n" +
                "]";
        return b;
    }

    /**
     * Get a specific value from Mobile Capture GetAppSettings response by field name
     * @param valname Name of the field to retrieve
     * @param username User's username
     * @param password User's password
     * @param identifier Device identifier
     * @param appIdentifier Application identifier
     * @return Value of the specified field
     */
    public static String getValueFromMobileCaptureGetAppSettingsByName(String valname, String username, String password, String identifier, String appIdentifier) {
        String jsonResponse = getAppSettingsMobileCapture(username, password, identifier, appIdentifier);
        JSONArray jsonArray = new JSONArray(jsonResponse);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        JSONArray userFields = jsonObject.getJSONArray("userFields");
        String value = null;
        for (int i = 0; i < userFields.length(); i++) {
            JSONObject userField = userFields.getJSONObject(i);
            String name = userField.getString("name");
            if (name.equals(valname)) {
                value = userField.getString("value");
                System.out.println(value);
                break;
            }
        }
        return value;
    }

    /**
     * Execute a simple POST request
     * @param url Target URL
     * @param requestBody Request body as string
     * @return Response body as string
     */
    public static String simplePostRequest(String url, String requestBody) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post(url)
                .then()
                .extract().response();
        return response.body().asString();
    }
}
