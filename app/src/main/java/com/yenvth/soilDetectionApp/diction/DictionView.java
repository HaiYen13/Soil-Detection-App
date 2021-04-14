package com.yenvth.soilDetectionApp.diction;

import com.yenvth.soilDetectionApp.models.SoilModel;

import java.util.ArrayList;

public interface DictionView {
    void onGetListSoilSuccess(ArrayList<SoilModel> soilModels);
    void onSaveHistorySuccess();
}
