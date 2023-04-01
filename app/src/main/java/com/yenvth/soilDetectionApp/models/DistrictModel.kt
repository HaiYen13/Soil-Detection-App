package com.yenvth.soilDetectionApp.models;

import androidx.annotation.NonNull;

public class DistrictModel {
    private int district_id;
    private int province_id;
    private String district_name;
    private String type;

    public DistrictModel() {
    }

    public DistrictModel(int district_id, int province_id, String district_name, String type) {
        this.district_id = district_id;
        this.province_id = province_id;
        this.district_name = district_name;
        this.type = type;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return getDistrict_name();
    }
}
