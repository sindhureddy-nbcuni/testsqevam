package vamservice.tests;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;


    public class Validate_VamResponse {

            public void validateResponse(Response response, String[] data, SoftAssert softAssert, ExtentTest extentTest) {
                // TODO Auto-generated method stub

                String responseBody = response.getBody().asString();
                System.out.println(responseBody);
                //Gson gson = new GsonBuilder().setLenient()
                JsonObject bodyObject = new JsonParser().parse(responseBody).getAsJsonObject();
                JsonObject globalParamObject = bodyObject.getAsJsonObject("globalParameters");
                JsonObject keyValuesObject = bodyObject.getAsJsonObject("keyValues");
                int index = 27;
                if (globalParamObject != null) {
                    for (String key : globalParamObject.keySet()) {
                        if (!key.equalsIgnoreCase("vip") && !key.equalsIgnoreCase("vprn") && !key.equalsIgnoreCase("pvrn")) {
                            // Verifying actual key value does not matches expected value
                            if (!globalParamObject.get(key).getAsString().equalsIgnoreCase(data[index])) {
                                extentTest.log(LogStatus.ERROR, "Value did not match for \"" + key
                                        + "\".<br/> Expected Value : " + data[index] + "<br/> Actual Value : " + globalParamObject.get(key).getAsString());
                                softAssert.assertTrue(false, "Value did not match for " + key);
                            } else {
                                extentTest.log(LogStatus.INFO, "Value matched for \"" + key + "\".");
                            }
                            index++;
                        }
                    }
                }
                index = 39;
                if (keyValuesObject != null) {
                    for (String key : keyValuesObject.keySet()) {
                        if(!key.equalsIgnoreCase("ltlg")) {
                            // Verifying actual key value does not matches expected value
                            if (!keyValuesObject.get(key).getAsString().equalsIgnoreCase(data[index])) {
                                extentTest.log(LogStatus.ERROR, "Value did not match for \"" + key
                                        + "\".<br/> Expected Value : " + data[index] + "<br/> Actual Value : " + keyValuesObject.get(key).getAsString());
                                softAssert.assertTrue(false, "Value did not match for " + key);
                            } else {
                                extentTest.log(LogStatus.INFO, "Value matched for \"" + key + "\".");
                            }

                            index++;
                        }
                    }
                }
            }
        }
