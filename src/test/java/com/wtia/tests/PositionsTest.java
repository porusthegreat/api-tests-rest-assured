package com.wtia.tests;

import com.wtia.requests.PositionsRequest;
import com.wtia.requests.SatelliteRequest;
import com.wtia.response.PositionsResponse;
import com.wtia.response.SatelliteResponse;
import com.wtia.utils.RequestData;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.*;


public class PositionsTest extends BaseTest {


    @DataProvider
    public static Object[][] units() {
        return new Object[][]{
                {"miles"},
                {"kilometers"},
                {null}
        };
    }

    @DataProvider
    public static Object[][] invalidData() {
        return new Object[][]{
                new Object[]{new RequestData(12345, Arrays.asList("1640016071", "1640016075"), 404, "satellite not found", null)},
                new Object[]{new RequestData(25544, Arrays.asList("1640016071invalid", "1640016071"), 400, "invalid timestamp in list: 1640016071invalid", "kilometers")},
                new Object[]{new RequestData(25544, Arrays.asList("1640016071", "1640079222"), 400, "Invalid Unit Provided", "randomUnit")} // Issue: Valid Response is returned even though unit is invalid.
        };
    }

    @Test(dataProvider = "units")
    public void verifyPositionDataForValidSatelliteId(String units) {
        SatelliteRequest satelliteRequest = new SatelliteRequest();
        SatelliteResponse satellite = satelliteRequest.getSatelliteResponse(version);

        //Get Satellite Data by ID
        List<PositionsResponse> expectedPositionsResponses = getExpectedPositions(satelliteRequest, version, satellite, units);
        List<String> timeStamps = getTimeStamps(expectedPositionsResponses);

        //Get Satellite Positions by TimeStamp and ID
        PositionsRequest positionsRequest = new PositionsRequest();
        List<PositionsResponse> positionsResponses = positionsRequest.getSatellitePositionsResponse(version, satellite, timeStamps, units);
        verifySatellitePositionResponse(units, satellite.getId(), timeStamps, positionsResponses);
    }

    @Test
    public void verifyPositionDataForMultipleTimeStamps(){
        SatelliteRequest satelliteRequest = new SatelliteRequest();
        SatelliteResponse satellite = satelliteRequest.getSatelliteResponse(version);

        //Get Satellite Data by ID
        String units = null;
        List<PositionsResponse> expectedPositionsResponses = getExpectedPositions(satelliteRequest, version, satellite, null);
        List<String> timeStamps = getTimeStamps(expectedPositionsResponses);

        PositionsRequest positionsRequest = new PositionsRequest();
        List<PositionsResponse> positionsResponses = positionsRequest.getSatellitePositionsResponse(version, satellite, timeStamps, null);
        verifyPositionsResponseWithSatelliteData(expectedPositionsResponses, positionsResponses);
    }

    @Test
    public void verifyPositionDataForDuplicateTimeStamps(){
        SatelliteRequest satelliteRequest = new SatelliteRequest();
        SatelliteResponse satellite = satelliteRequest.getSatelliteResponse(version);
        //Get Satellite Data by ID
        String units = null;
        List<String> timeStamps = Arrays.asList("1640016071", "1640016071");
        PositionsRequest positionsRequest = new PositionsRequest();
        List<PositionsResponse> positionsResponses = positionsRequest.getSatellitePositionsResponse(version, satellite, timeStamps, null);
        verifyPositionsResponseWithSatelliteDataForDuplicateTimeStamps(positionsResponses, new HashSet<String>(timeStamps));
    }

    private void verifyPositionsResponseWithSatelliteDataForDuplicateTimeStamps(List<PositionsResponse> positionsResponses, Set<String> timestamps) {
        Assert.assertEquals(positionsResponses.size(), timestamps.size(), "size should be same as unique timestamps size");
    }

    private void verifyPositionsResponseWithSatelliteData(List<PositionsResponse> expectedPositionsResponses, List<PositionsResponse> positionsResponses) {
        SoftAssert softAssert = new SoftAssert();
        for(int i=0; i <expectedPositionsResponses.size(); i++){
            softAssert.assertEquals(expectedPositionsResponses.get(0).getTimestamp(), positionsResponses.get(0).getTimestamp(), "Position Responses timestamp should be same for a given TimeStamp from both the APIs at index: " + i);
            softAssert.assertEquals(expectedPositionsResponses.get(0).getLongitude(), positionsResponses.get(0).getLongitude(), "Position Responses getLongitude should be same for a given TimeStamp from both the APIs at index: " + i);
            softAssert.assertEquals(expectedPositionsResponses.get(0).getLatitude(), positionsResponses.get(0).getLatitude(), "Position Responses latitide should be same for a given TimeStamp from both the APIs at index: " + i);
            softAssert.assertEquals(expectedPositionsResponses.get(0).getDaynum(), positionsResponses.get(0).getDaynum(), "Position Responses getDaynum should be same for a given TimeStamp from both the APIs at index: " + i);
            softAssert.assertEquals(expectedPositionsResponses.get(0).getSolar_lat(), positionsResponses.get(0).getSolar_lat(), "Position Responses getSolar_lat should be same for a given TimeStamp from both the APIs at index: " + i);
            softAssert.assertEquals(expectedPositionsResponses.get(0).getSolar_lon(), positionsResponses.get(0).getSolar_lon(), "Position Responses getSolar_lon should be same for a given TimeStamp from both the APIs at index: " + i);
            softAssert.assertEquals(expectedPositionsResponses.get(0).getUnits(), positionsResponses.get(0).getUnits(), "Position Responses getUnits should be same for a given TimeStamp from both the APIs at index: " + i);
            softAssert.assertEquals(expectedPositionsResponses.get(0).getAltitude(), positionsResponses.get(0).getAltitude(), "Position Responses getAltitude should be same for a given TimeStamp from both the APIs at index: " + i);
            softAssert.assertEquals(expectedPositionsResponses.get(0).getFootprint(), positionsResponses.get(0).getFootprint(), "Position Responses getFootprint should be same for a given TimeStamp from both the APIs at index: " + i);
            softAssert.assertEquals(expectedPositionsResponses.get(0).getVisibility(), positionsResponses.get(0).getVisibility(), "Position Responses getVisibility should be same for a given TimeStamp from both the APIs at index: " + i);
            softAssert.assertEquals(expectedPositionsResponses.get(0).getId(), positionsResponses.get(0).getId(), "Position Responses getVisibility should be same for a given TimeStamp from both the APIs at index: " + i);
            softAssert.assertEquals(expectedPositionsResponses.get(0).getTimestamp(), positionsResponses.get(0).getTimestamp(), "Position Responses timestamp should be same for a given TimeStamp from both the APIs at index: " + i);
            softAssert.assertEquals(expectedPositionsResponses.get(0).getVelocity(), positionsResponses.get(0).getVelocity(), "Position Responses getVelocity should be same for a given TimeStamp from both the APIs at index: " + i);
        }
        softAssert.assertAll();
    }

    @Test(dataProvider = "invalidData")
    private void verifyErrorForSatellitePosition(RequestData requestData){
        String version = "v1";
        List<String> timeStampList = requestData.getTimestamps();
        PositionsRequest positionsRequest = new PositionsRequest();
        Response response = positionsRequest.getSatellitePositionById(version, requestData.getId(), timeStampList, requestData.getUnits());
        verifyErrorsForPositionRequest(requestData, response);
    }

    private void verifyErrorsForPositionRequest(RequestData requestData, Response response) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), requestData.getErrorCode());
        softAssert.assertEquals(response.getBody().jsonPath().get("error"), requestData.getErrorMessage());
        softAssert.assertAll();
    }

    private void verifySatellitePositionResponse(String units, Integer id, List<String> timeStamps, List<PositionsResponse> positionsResponses) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(positionsResponses.size(), timeStamps.size());
        for (int i=0; i< timeStamps.size(); i++){
            double latitude = Double.parseDouble(positionsResponses.get(i).getLatitude());
            double longitude = Double.parseDouble(positionsResponses.get(i).getLongitude());
            Assert.assertTrue(-90 <= latitude, "latitude should be valid");
            Assert.assertTrue(90 >= latitude, "latitude should be valid");
            Assert.assertTrue(-180 <= longitude, "longitude should be valid");
            Assert.assertTrue(180 >= longitude, "longitude should be valid");
            Assert.assertNotNull(positionsResponses.get(i).getFootprint(), "footprint  should not be null");
            Assert.assertEquals(positionsResponses.get(i).getId(),id,"Satellite ID should match");
        }
        verifyUnitsInResponse(units, positionsResponses, softAssert);
        softAssert.assertAll();
    }
}
