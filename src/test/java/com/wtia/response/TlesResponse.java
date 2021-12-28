package com.wtia.response;

public class TlesResponse {
    private String tle_timestamp;
    private String name;
    private String header;
    private String id;
    private String line2;
    private String line1;
    private String requested_timestamp;

    public String getTle_timestamp() {
        return tle_timestamp;
    }

    public void setTle_timestamp(String tle_timestamp) {
        this.tle_timestamp = tle_timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getRequested_timestamp() {
        return requested_timestamp;
    }

    public void setRequested_timestamp(String requested_timestamp) {
        this.requested_timestamp = requested_timestamp;
    }

    @Override
    public String toString() {
        return "TlesResponse{" +
                "tle_timestamp='" + tle_timestamp + '\'' +
                ", name='" + name + '\'' +
                ", header='" + header + '\'' +
                ", id='" + id + '\'' +
                ", line2='" + line2 + '\'' +
                ", line1='" + line1 + '\'' +
                ", requested_timestamp='" + requested_timestamp + '\'' +
                '}';
    }
}
