package com.yenvth.soilDetectionApp.models;

public class SoilModel {
    private int soil_id;
    private String soil_name;
    private String url;
    private String description;
    private double pH;
    private double n2o; //Nito
    private double humidity; //Độ ẩm
    private double lat;
    private double lon;

    public String getSoil_name() {
        return soil_name;
    }

    public void setSoil_name(String soil_name) {
        this.soil_name = soil_name;
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

    public double getpH() {
        return pH;
    }

    public void setpH(double pH) {
        this.pH = pH;
    }

    public double getN2o() {
        return n2o;
    }

    public void setN2o(double n2o) {
        this.n2o = n2o;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getSoil_id() {
        return soil_id;
    }

    public void setSoil_id(int soil_id) {
        this.soil_id = soil_id;
    }
}
