package com.yenvth.soilDetectionApp.models;

public class HistoryModel {
    private String histories_id;
    private String soil_id;
    private String soil_name;
    private long timestamp;

    public String getHistories_id() {
        return histories_id;
    }

    public void setHistories_id(String histories_id) {
        this.histories_id = histories_id;
    }

    public String getSoil_id() {
        return soil_id;
    }

    public void setSoil_id(String soil_id) {
        this.soil_id = soil_id;
    }

    public String getSoil_name() {
        return soil_name;
    }

    public void setSoil_name(String soil_name) {
        this.soil_name = soil_name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
