package com.yenvth.soilDetectionApp.soilDetail;

public interface SoilDetailPresenter<V extends SoilDetailView> {
    void getSoilDetail(int soil_id);
}
