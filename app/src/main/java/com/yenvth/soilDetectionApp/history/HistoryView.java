package com.yenvth.soilDetectionApp.history;


import com.yenvth.soilDetectionApp.models.HistoryModel;

import java.util.ArrayList;

public interface HistoryView {
    void onGetListHistorySuccess(ArrayList<HistoryModel> historyModels);

    void onUpdateHistorySuccess();

    void onDeleteHistorySuccess();
}
