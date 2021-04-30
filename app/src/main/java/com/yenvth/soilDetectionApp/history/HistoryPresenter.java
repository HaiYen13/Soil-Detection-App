package com.yenvth.soilDetectionApp.history;

public interface HistoryPresenter<V extends HistoryView> {
    void getListHistories();

    void deleteHistory(String history_id);

    void deleteAllHistories();
}
