package com.yenvth.soilDetectionApp.map;

public interface MapPresenter<V extends MapView> {
    void getListSoil(String queryString);
}
