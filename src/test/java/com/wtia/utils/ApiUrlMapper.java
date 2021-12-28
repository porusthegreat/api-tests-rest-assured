package com.wtia.utils;

public enum ApiUrlMapper {
    GET_SATELLITES("/%s/satellites"),
    GET_SATELLITE_BY_ID("/%s/satellites/%d"),
    GET_SATELLITE_POSITION("/%s/satellites/%d/positions"),
    GET_SATELLITE_TLE("/%s/satellites/%d/tles");

    private final String urlPath;
    protected static PropertiesReader propertiesReader = new PropertiesReader();

    ApiUrlMapper(String endPoint) {
        this.urlPath = endPoint;
    }

    public String getUrlPath(String version){
        String path =  String.format(this.urlPath, version);
        return propertiesReader.getBaseUrl() + path;
    }

    public String getUrlPath(String version, Integer id){
        String path =  String.format(this.urlPath, version, id);
        return propertiesReader.getBaseUrl() + path;
    }

}
