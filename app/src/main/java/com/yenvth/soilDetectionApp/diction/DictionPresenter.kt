package com.yenvth.soilDetectionApp.diction

import com.yenvth.soilDetectionApp.room.entity.HistoryRecord

interface DictionPresenter<V : DictionView?> {
    fun saveSearchHistory(historyRecord: HistoryRecord)
    fun getListSoil(queryString: String)
}