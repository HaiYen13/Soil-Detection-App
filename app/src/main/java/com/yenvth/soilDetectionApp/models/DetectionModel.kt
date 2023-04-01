package com.yenvth.soilDetectionApp.models;

public class DetectionModel {
    private String url;
    private String label;
    private long timestamp;

    public DetectionModel(String url, String label, long timestamp) {
        this.url = url;
        this.label = label;
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
