package com.yenvth.soilDetectionApp.soilDetail

interface SoilDetailPresenter<V : SoilDetailView?> {
    fun getSoilDetail(soilId: Int)
}