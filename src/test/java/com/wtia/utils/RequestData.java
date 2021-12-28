package com.wtia.utils;

import java.util.List;

public class RequestData {
    private int id;
    private List<String> timestamps;
    private int errorCode;
    private String errorMessage;
    private String units;
    private String format;

    public RequestData(int id, List<String> timestamps, int errorCode, String errorMessage, String units) {
        this.id = id;
        this.timestamps = timestamps;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.units = units;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(List<String> timestamps) {
        this.timestamps = timestamps;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
