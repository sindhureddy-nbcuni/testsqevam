package vamservice.tests;


import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

import vamservice.utils.*;
import vamservice.utils.configReader;

import static io.restassured.RestAssured.*;



public class CouchBaseTest extends BaseTest {

    public static Response couchbaseResponse;


    @Test(priority = -1)
    public void Testing_Couchbase_is_up_and_running() {
        extentTest = extentReports.startTest("Couchbase should be Up before any test runs");


        for (int i = 1; i < 11; i++) {
            try {
                RestAssured.baseURI = configReader.getProperty("couchbase" + i);
                couchbaseResponse = get("");

                Assert.assertEquals(200, couchbaseResponse.statusCode());
                extentTest.log(LogStatus.PASS,configReader.getProperty("couchbase" + i));
                extentTest.log(LogStatus.INFO,"Milliseconds:  "+couchbaseResponse.getTimeIn(TimeUnit.MILLISECONDS));
                extentReports.endTest(extentTest);

            }catch(Throwable e){
                extentTest.log(LogStatus.INFO,e.fillInStackTrace());
                extentTest.log(LogStatus.INFO,"No connection with Couchbase");
                throw e;
            }
        }
        extentTest.log(LogStatus.PASS,"Couchbase is Active");

    }




}
