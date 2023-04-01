package com.yenvth.soilDetectionApp.diction;

import com.yenvth.soilDetectionApp.models.HistoryModel;

public interface DictionPresenter<V extends DictionView> {
    void saveSearchHistory(HistoryModel historyModel);
    void getListSoil(String queryString);
}
