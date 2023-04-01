package com.yenvth.soilDetectionApp.models;

import androidx.annotation.NonNull;

public class WardModel {
    private int ward_id;
    private int district_id;
    private String ward_name;
    private String type;

    public WardModel() {
    }

    public WardModel(int ward_id, int district_id, String ward_name, String type) {
        this.ward_id = ward_id;
        this.district_id = district_id;
        this.ward_name = ward_name;
        this.type = type;
    }

    public int getWard_id() {
        return ward_id;
    }

    public void setWard_id(int ward_id) {
        this.ward_id = ward_id;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public String getWard_name() {
        return ward_name;
    }

    public void setWard_name(String ward_name) {
        this.ward_name = ward_name;
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
        return getWard_name();
    }
}
