package com.yenvth.soilDetectionApp.soilDetail

import android.content.Context
import com.yenvth.soilDetectionApp.MyApp
import com.yenvth.soilDetectionApp.room.dao.toModel

class SoilDetailPresenterImpl<V : SoilDetailView?>(
    private val mContext: Context,
    private val soilDetailView: SoilDetailView
) : SoilDetailPresenter<V> {
    val db = MyApp.getDatabase()
    override fun getSoilDetail(soilId: Int) {
        soilDetailView.onGetSoilDetails(db.soilDao().getSoil(soilId).toModel())
    }
}