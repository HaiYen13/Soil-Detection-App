package com.yenvth.soilDetectionApp.history;

public interface HistoryPresenter<V extends HistoryView> {
    void getListHistories();

    void deleteHistory(int history_id);

    void deleteAllHistories();
}
