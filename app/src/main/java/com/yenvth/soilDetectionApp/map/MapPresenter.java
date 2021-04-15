package com.yenvth.soilDetectionApp.map;

public interface MapPresenter<V extends MapView> {
    void getListSoilByProvince(int provinceId);
}
