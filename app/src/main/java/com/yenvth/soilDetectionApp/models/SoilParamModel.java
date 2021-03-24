package com.yenvth.soilDetectionApp.models;

public class SoilParamModel {
    private int province_id;
    private int district_id;
    private int ward_id;
    private double lat;
    private double lon;

    public SoilParamModel() {
    }

    public SoilParamModel(int province_id, int district_id, int ward_id, double lat, double lon) {
        this.province_id = province_id;
        this.district_id = district_id;
        this.ward_id = ward_id;
        this.lat = lat;
        this.lon = lon;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public int getWard_id() {
        return ward_id;
    }

    public void setWard_id(int ward_id) {
        this.ward_id = ward_id;
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
}
