package com.yenvth.soilDetectionApp.history

import android.content.Context
import com.yenvth.soilDetectionApp.MyApp

class HistoryPresenterImpl<V : HistoryView?>(
    private val mContext: Context,
    private val historyView: HistoryView
) : HistoryPresenter<V> {
    private val db = MyApp.getDatabase()
    override fun getHistories() {
        historyView.onGetListHistorySuccess(db.historyDao().getHistories())
    }

    override fun deleteHistory(historyId: Int) {
        db.historyDao().deleteHistory(historyId)
        historyView.onDeleteHistorySuccess()
    }

    override fun deleteAllHistories() {
        db.historyDao().deleteAllHistory()
        historyView.onDeleteAllHistoriesSuccess()
    }
}