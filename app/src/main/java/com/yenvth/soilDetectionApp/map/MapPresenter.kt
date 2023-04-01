package com.yenvth.soilDetectionApp.map

interface MapPresenter<V : MapView?> {
    fun getListSoilByProvince(provinceId: Int)
}