package com.yenvth.soilDetectionApp.soilDetail

import android.content.Context
import com.yenvth.soilDetectionApp.MyApp
import com.yenvth.soilDetectionApp.room.dao.toModel

class SoilDetailPresenterImpl<V : SoilDetailView?>(
    private val mContext: Context,
    private val soilDetailView: SoilDetailView
) : SoilDetailPresenter<V> {
    val db = MyApp.getDatabase()
    private val resourceDb = MyApp.getResourceDatabase()

    override fun getSoilDetail(soilId: Int) {
        soilDetailView.onGetSoilDetails(resourceDb.soilDao().getSoil(soilId).toModel())
    }
}