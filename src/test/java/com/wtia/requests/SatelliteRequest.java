package com.wtia.requests;

import com.wtia.response.PositionsResponse;
import com.wtia.response.SatelliteResponse;
import com.wtia.utils.ApiUrlMapper;
import com.wtia.utils.RequestData;
import com.wtia.utils.ResourceHelper;
import com.wtia.utils.ResponseHelper;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SatelliteRequest extends BaseRequest {

    public Response getSatellites(String version) {
        String url = ApiUrlMapper.GET_SATELLITES.getUrlPath(version);
        return ResourceHelper.get(url);
    }

    public SatelliteResponse mapSatelliteResponseObject(Response response){
        Assert.assertEquals(response.getStatusCode(), 200);
        List<SatelliteResponse> satelliteResponseList = (List<SatelliteResponse>) ResponseHelper.getResponseAsObject(response.getBody().asString(), List.class, SatelliteResponse.class);
        return satelliteResponseList.get(0);
    }

    public SatelliteResponse getSatelliteResponse(String version){
        return mapSatelliteResponseObject(getSatellites(version));
    }

    public Response getSatellitesById(String version, Integer satelliteId, String units) {
        RequestData requestData = new RequestData(satelliteId, Collections.EMPTY_LIST, 0, null,  units);
        HashMap<String, String> params = setParams(requestData);
        String url = ApiUrlMapper.GET_SATELLITE_BY_ID.getUrlPath(version,satelliteId);
        return ResourceHelper.get(url, params);
    }

    public PositionsResponse mapSatellitesByIdResponse(Response satellitePosition) {
        Assert.assertEquals(satellitePosition.getStatusCode(), 200);
        PositionsResponse positionsResponse = (PositionsResponse) ResponseHelper.getResponseAsObject(satellitePosition.getBody().asString(), PositionsResponse.class);
        return positionsResponse;
    }

    public PositionsResponse getSatellitesByIdResponse(String version, Integer satelliteId, String units){
        return mapSatellitesByIdResponse(getSatellitesById(version, satelliteId, units));
    }

}
