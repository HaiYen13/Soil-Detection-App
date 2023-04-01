package com.yenvth.soilDetectionApp.map;

import com.yenvth.soilDetectionApp.models.SoilModel;

import java.util.ArrayList;

public interface MapView {
    void onGetListSoilSuccess(ArrayList<SoilModel> soilModels);
}
