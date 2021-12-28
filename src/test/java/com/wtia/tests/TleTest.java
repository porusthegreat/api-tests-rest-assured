package com.wtia.tests;

import com.wtia.requests.SatelliteRequest;
import com.wtia.requests.TleRequest;
import com.wtia.response.SatelliteResponse;
import com.wtia.response.TlesResponse;
import com.wtia.utils.RequestData;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Collections;

public class TleTest extends BaseTest {
    @Test
    public void verifyTleResponseForValidSatelliteId() {
        SatelliteRequest satelliteRequest = new SatelliteRequest();
        SatelliteResponse satellite = satelliteRequest.getSatelliteResponse("v1");

        //GetTLEsDataForTheSatellite
        TleRequest tleRequest = new TleRequest();
        TlesResponse tles = tleRequest.getTleResponse("v1", satellite.getId());

        //Verify TLE Data for the Satellite
        SoftAssert softAssert = new SoftAssert();
        verifyTleData(tles, satellite, softAssert);
        verifyBasicMetadata(tles, softAssert);
        softAssert.assertAll();
    }

    @Test()
    public void verifyTleResponseForInvalidSatelliteId() {

        //GetTLEsDataForTheSatellite
        TleRequest tleRequest = new TleRequest();
        Response response = tleRequest.getSatelliteTle(version, 12345);

        //Verify TLE Data for the Satellite
        verifyErrorMessage(response);
    }

    @Test
    public void verifyTleResponseTextFormat(){
        TleRequest tle = new TleRequest();
        RequestData requestData = new RequestData(25544, Collections.EMPTY_LIST, 0, null, null);
        requestData.setFormat("text");
        Response response = tle.getSatelliteTle(version, requestData);
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertFalse(StringUtils.isEmpty(response.getBody().asString()));
    }

    private void verifyErrorMessage(Response response) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.statusCode(), 404);
        softAssert.assertEquals(response.getBody().jsonPath().get("error"), "satellite not found");
        softAssert.assertEquals(response.getBody().jsonPath().get("status"), 404);
    }

    private void verifyTleData(TlesResponse tle, SatelliteResponse satellite, SoftAssert softAssert) {
        softAssert.assertEquals(tle.getId(), String.valueOf(satellite.getId()), "Satellite ID should match");
        softAssert.assertEquals(tle.getName(), satellite.getName(), "Satellite Name should match");
        softAssert.assertTrue(tle.getLine1().contains(String.valueOf(satellite.getId())), "Satellite Line 1 should contain satelliteID");
        softAssert.assertTrue(tle.getLine2().contains(String.valueOf(satellite.getId())), "Satellite Line 1 should contain satelliteID");

    }

    private void verifyBasicMetadata(TlesResponse tles, SoftAssert softAssert) {
        softAssert.assertTrue(tles.getLine1() != null, "Line 1 should not be null");
        softAssert.assertTrue(tles.getLine2() != null, "Line 2 should not be null");
        softAssert.assertTrue(tles.getRequested_timestamp() != null, "Requested TimeStamp cannot be null");
        softAssert.assertTrue(tles.getTle_timestamp() != null, "TLE TimeStamp cannot be null");
        softAssert.assertTrue(tles.getLine1().startsWith("1"), "Line 1 should start with 1");
        softAssert.assertTrue(tles.getLine2().startsWith("2"), "Line 2 should start with 2");
        softAssert.assertTrue(Integer.parseInt(tles.getTle_timestamp()) <= Integer.parseInt(tles.getRequested_timestamp()), "TLE time stamp cannot be greater than requested timestamp");
    }
}
