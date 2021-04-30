package com.yenvth.soilDetectionApp.models;

public class SoilModel {
    private int soil_id;
    private String soil_code;
    private String name_vi;
    private String name_en;
    private String url;
    private String description;
//    private double lat;
//    private double lon;
    private long timestamp;

    public SoilModel() {
    }

    public int getSoil_id() {
        return soil_id;
    }

    public void setSoil_id(int soil_id) {
        this.soil_id = soil_id;
    }

    public String getSoil_code() {
        return soil_code;
    }

    public void setSoil_code(String soil_code) {
        this.soil_code = soil_code;
    }

    public String getName_vi() {
        return name_vi;
    }

    public void setName_vi(String name_vi) {
        this.name_vi = name_vi;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public double getLat() {
//        return lat;
//    }
//
//    public void setLat(double lat) {
//        this.lat = lat;
//    }
//
//    public double getLon() {
//        return lon;
//    }
//
//    public void setLon(double lon) {
//        this.lon = lon;
//    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
