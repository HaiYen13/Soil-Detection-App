package com.yenvth.soilDetectionApp.models;

import androidx.annotation.NonNull;

public class ProvinceModel {
    private int province_id;
    private String province_name;
    private String type;

    public ProvinceModel() {
    }

    public ProvinceModel(int province_id, String province_name, String type) {
        this.province_id = province_id;
        this.province_name = province_name;
        this.type = type;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
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
        return getProvince_name();
    }
}
