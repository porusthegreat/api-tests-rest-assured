package com.wtia.requests;

import com.wtia.response.TlesResponse;
import com.wtia.utils.ApiUrlMapper;
import com.wtia.utils.RequestData;
import com.wtia.utils.ResourceHelper;
import com.wtia.utils.ResponseHelper;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;

public class TleRequest extends BaseRequest {

    public Response getSatelliteTle(String version, Integer satelliteId) {
        return ResourceHelper.get(ApiUrlMapper.GET_SATELLITE_TLE.getUrlPath(version, satelliteId));
    }

    public Response getSatelliteTle(String version, RequestData requestData) {
        HashMap<String, String> params = setParams(requestData);
        return ResourceHelper.get(ApiUrlMapper.GET_SATELLITE_TLE.getUrlPath(version, requestData.getId()), params);
    }

    public TlesResponse mapSatelliteTleResponse(Response response) {
        Assert.assertEquals(response.getStatusCode(), 200);
        return (TlesResponse) ResponseHelper.getResponseAsObject(response.getBody().asString(), TlesResponse.class);
    }

    public TlesResponse getTleResponse(String version, Integer satelliteId) {
        return mapSatelliteTleResponse(getSatelliteTle(version, satelliteId));
    }
}
