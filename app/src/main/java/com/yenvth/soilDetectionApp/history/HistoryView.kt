package com.yenvth.soilDetectionApp.history

import com.yenvth.soilDetectionApp.models.HistoryModel

interface HistoryView {
    fun onGetListHistorySuccess(historyModels: List<HistoryModel>)
    fun onDeleteHistorySuccess()
    fun onDeleteAllHistoriesSuccess()
}