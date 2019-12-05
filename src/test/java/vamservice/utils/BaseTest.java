package vamservice.utils;

import com.nbcuni.test.POJO.TestPOJO;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;
import vamservice.tests.Validate_VamResponse;
import vamservice.tests.Validate_VamResponseTest;

import java.io.*;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class BaseTest {

    private String configFile = "configuration.properties";
    private String envFile = "environment.properties";
    private Properties properties;
    private static Properties envProp;
    private PropertyParser propertyParser;
    protected static TestPOJO testPOJO;
    private static Validate_VamResponseTest responseVal;
    protected static SoftAssert softAssert;
    protected static ExtentReports extentReports;
    protected static ExtentTest extentTest;
    private Response responseDB;


    @BeforeSuite
    public void setup() throws IOException {
        propertyParser = new PropertyParser();
        properties = propertyParser.parseProperties(configFile);
        envProp = propertyParser.parseEnvProperties(envFile);
        testPOJO = new TestPOJO();
        responseVal = new Validate_VamResponseTest();
        softAssert = new SoftAssert();
        extentReports = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtentFiles/Report.html", true);
        extentReports.addSystemInfo("Environment", envProp.getProperty("currentenv").substring(10, 13).toUpperCase())
                .loadConfig(new File(System.getProperty("user.dir") + "/test-output/extent-config.xml"));


    }

    private static Response callApiUrl(String url) {

        Response response = given().
                contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get(url);
        return response;
    }

    private static String getUrl(TestPOJO testPojo) {
        String env = envProp.getProperty("currentenv");
        String url = env + "/v1/freewheel-params?appName="
                + testPojo.getAppName() + "&brand=" + testPojo.getBrand() + "&platform=" + testPojo.getPlatform()
                + "&streamType="
                + testPojo.getStreamType() + "&playerName=" + testPojo.getPlayerName() + "&playerVersion="
                + testPojo.getPlayerVersion()
                + "&deviceAdvertisingId="
                + testPojo.getDeviceAdvertisingId() + "&adServerContentId=" + testPojo.getAdServerContentId()
                + "&deviceAdvertisingTrackingConsent=" + testPojo.getDeviceAdvertisingTrackingConsent()
                + "&httpReferer=" + testPojo.getHttpReferer()
                + "&deviceAdvertisingIdType=" + testPojo.getDeviceAdvertisingIdType() + "&locationPostalCode="
                + testPojo.getLocationPostalCode() + "&mvpdHash=" + testPojo.getMvpdHash() + "&videoDurationInSeconds="
                + testPojo.getVideoDurationInSeconds() + "&appBundleId=" + testPojo.getAppBundleId()
                + "&locationLatitude=" + testPojo.getLocationLatitude() + "&locationLongitude="
                + testPojo.getLocationLongitude() + "&appBuild=" + testPojo.getAppBuild() + "&appVersion="
                + testPojo.getAppVersion() + "&cdnName=" + testPojo.getCdnName() + "&sdkVersion="
                + testPojo.getSdkVersion() + "&playerWidthPixels=" + testPojo.getPlayerWidthPixels()
                + "&playerHeightPixels=" + testPojo.getPlayerHeightPixels() + "&coppaApplies="
                + testPojo.getCoppaApplies() + "&isBingeViewer=" + testPojo.getIsBingeViewer() + "&obfuscatedFreewheelProfileId=" + testPojo.getObfuscatedFreewheelProfileId() + "&adCompatibilityEncodingProfile=" + testPojo.getAdCompatibilityEncodingProfile();
        extentTest.log(LogStatus.INFO, "URL : " + url);
        return url;
    }


    protected static void readDataFromSheet(TestPOJO testPojo, SoftAssert softAssert) {

        BufferedReader bufferedReader = null;
        String line = "";
        String splitBy = ",";

        try {
            bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/test/resources/DataSheet/testdata1.csv"));
            int rows = 0;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(splitBy, -1);
                if (rows != 0 && data[0].length() > 0) {
                    extentTest = extentReports.startTest("Validate_VamResponse_TC0_" + rows);
                    testPojo.setAppName(data[0]);
                    testPojo.setBrand(data[1]);
                    testPojo.setPlatform(data[2]);
                    testPojo.setStreamType(data[3]);
                    testPojo.setPlayerName(data[4]);
                    testPojo.setPlayerVersion(data[5]);
                    testPojo.setDeviceAdvertisingId(data[6]);
                    testPojo.setAdServerContentId(data[7]);
                    testPojo.setDeviceAdvertisingTrackingConsent(data[8]);
                    testPojo.setHttpReferer(data[9]);
                    testPojo.setDeviceAdvertisingIdType(data[10]);
                    testPojo.setLocationPostalCode(data[11]);
                    testPojo.setMvpdHash(data[12]);
                    testPojo.setVideoDurationInSeconds(data[13]);
                    testPojo.setAppBundleId(data[14]);
                    testPojo.setLocationLatitude(data[15]);
                    testPojo.setLocationLongitude(data[16]);
                    testPojo.setAppBuild(data[17]);
                    testPojo.setAppVersion(data[18]);
                    testPojo.setCdnName(data[19]);
                    testPojo.setSdkVersion(data[20]);
                    testPojo.setPlayerWidthPixels(data[21]);
                    testPojo.setPlayerHeightPixels(data[22]);
                    testPojo.setCoppaApplies(data[23]);
                    testPojo.setIsBingeViewer(data[24]);
                    testPojo.setObfuscatedFreewheelProfileId(data[25]);
                    testPojo.setAdCompatibilityEncodingProfile(data[26]);
                    if (testPojo.getAppName() != null) {
                        String url = getUrl(testPojo);
                        Response response = callApiUrl(url);
                        responseVal.validateResponse(response, data, softAssert, extentTest);
                    }
                    extentReports.endTest(extentTest);
                }
                rows++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @AfterSuite
    public void endTest() {

        //extentReports.endTest(extentTest);
        extentReports.flush();
        extentReports.close();
        softAssert.assertAll();
    }
}
