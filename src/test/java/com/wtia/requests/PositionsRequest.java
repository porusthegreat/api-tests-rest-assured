package com.wtia.requests;

import com.wtia.response.PositionsResponse;
import com.wtia.response.SatelliteResponse;
import com.wtia.utils.ApiUrlMapper;
import com.wtia.utils.RequestData;
import com.wtia.utils.ResourceHelper;
import com.wtia.utils.ResponseHelper;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class PositionsRequest extends BaseRequest {
    public Response getSatellitePositionById(String version, Integer id, List<String> timestamps, String units) {
        RequestData requestData = new RequestData(id, timestamps, 0, null, units);
        HashMap<String, String> params = setParams(requestData);
        String url  = ApiUrlMapper.GET_SATELLITE_POSITION.getUrlPath(version, id);
        return ResourceHelper.get(url, params);
    }

    public List<PositionsResponse> mapSatellitePositionsResponse(Response response){
        Assert.assertEquals(response.getStatusCode(), 200);
        List<PositionsResponse> positionsResponses = (List<PositionsResponse>) ResponseHelper.getResponseAsObject(response.getBody().asString(), List.class, PositionsResponse.class);
        return positionsResponses;
    }


    public List<PositionsResponse> getSatellitePositionsResponse(String version, SatelliteResponse satellite, List<String> timestamps, String units) {
       return mapSatellitePositionsResponse(getSatellitePositionById(version, satellite.getId(), timestamps, units));
    }
}
