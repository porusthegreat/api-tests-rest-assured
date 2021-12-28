package com.wtia.requests;

import com.wtia.utils.RequestData;

import java.util.HashMap;
import java.util.List;

public class BaseRequest {
    protected HashMap<String, String> setParams(RequestData requestData) {
        HashMap<String, String> params = new HashMap<String, String>();
        if(requestData.getTimestamps().size() > 0) {
            String timestampString = getTimeStampStringFromList(requestData.getTimestamps());
            params.put("timestamps", timestampString);
        }
        if(requestData.getUnits() != null)
            params.put("units", requestData.getUnits());

        if(requestData.getFormat() != null)
             params.put("format", requestData.getFormat());
        return params;
    }

    private String getTimeStampStringFromList(List<String> timestamps) {
        StringBuilder builder = new StringBuilder();
        for(String time: timestamps)
            builder.append(time).append(",");
        String temp = builder.toString();
        return temp.substring(0, temp.length() - 2);
    }
}
