package com.yenvth.soilDetectionApp.models;

public class HistoryModel {
    private int historiesId;
    private int soilId;
    private String soilName;
    private String uid;
    private long timestamp;

    public HistoryModel() {
    }

    public HistoryModel(int soilId, String soilName, long timestamp) {
        this.soilId = soilId;
        this.soilName = soilName;
        this.timestamp = timestamp;
    }

    public int getHistoriesId() {
        return historiesId;
    }

    public void setHistoriesId(int historiesId) {
        this.historiesId = historiesId;
    }

    public int getSoilId() {
        return soilId;
    }

    public void setSoilId(int soilId) {
        this.soilId = soilId;
    }

    public String getSoilName() {
        return soilName;
    }

    public void setSoilName(String soilName) {
        this.soilName = soilName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
