package com.yenvth.soilDetectionApp.history;


import com.yenvth.soilDetectionApp.models.HistoryModel;

public interface HistoryPresenter<V extends HistoryView> {
    void getListHistories();

    void updateHistory(HistoryModel historyModel);

    void deleteHistory(int history_id);
}
