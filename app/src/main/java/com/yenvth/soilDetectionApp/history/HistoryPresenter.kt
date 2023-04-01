package com.yenvth.soilDetectionApp.history

interface HistoryPresenter<V : HistoryView?> {
    fun getHistories()
    fun deleteHistory(historyId: Int)
    fun deleteAllHistories()
}