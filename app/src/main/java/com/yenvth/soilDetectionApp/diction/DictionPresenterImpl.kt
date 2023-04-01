package com.yenvth.soilDetectionApp.diction

import android.content.Context
import com.yenvth.soilDetectionApp.MyApp
import com.yenvth.soilDetectionApp.room.entity.HistoryRecord

class DictionPresenterImpl<V : DictionView?>(
    private val mContext: Context,
    private val dictionView: DictionView
) : DictionPresenter<V> {
    private val db = MyApp.getDatabase()

    override fun saveSearchHistory(historyRecord: HistoryRecord) {
        db.historyDao().insertHistory(historyRecord)
        dictionView.onSaveHistorySuccess()
    }

    override fun getListSoil(queryString: String) {
        val soils = db.soilDao().getSoils()
        dictionView.onGetListSoilSuccess(soils)
    }
}