package com.wtia.tests;

import com.wtia.requests.SatelliteRequest;
import com.wtia.response.PositionsResponse;
import com.wtia.response.SatelliteResponse;
import com.wtia.utils.RequestData;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseTest {

    String version = "v1";

    protected void verifyUnitsInResponse(String units, List<PositionsResponse> positionsResponses, SoftAssert softAssert) {
        for (PositionsResponse position: positionsResponses) {
            String expectedUnits = units == null ? "kilometers" : units;
            softAssert.assertEquals(position.getUnits(), expectedUnits);
        }
    }

    protected List<PositionsResponse> getExpectedPositions(SatelliteRequest satelliteRequest, String version, SatelliteResponse satellite, String units) {
        List<PositionsResponse> expectedPositionsResponses = new ArrayList<PositionsResponse>();
        expectedPositionsResponses.add(satelliteRequest.getSatellitesByIdResponse(version, satellite.getId(), units));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        expectedPositionsResponses.add(satelliteRequest.getSatellitesByIdResponse(version, satellite.getId(), units));
        return expectedPositionsResponses;
    }

    protected List<String> getTimeStamps(List<PositionsResponse> expectedPositionsResponses) {
        List<String> timeStamps = new ArrayList<String>();
        for (PositionsResponse position: expectedPositionsResponses) timeStamps.add(position.getTimestamp());
        return timeStamps;
    }

}
