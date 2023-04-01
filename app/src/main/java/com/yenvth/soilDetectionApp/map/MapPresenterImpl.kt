package com.yenvth.soilDetectionApp.map

import android.content.Context

class MapPresenterImpl<V : MapView?>(
    private val mContext: Context,
    private val mapView: MapView
) : MapPresenter<V> {

    override fun getListSoilByProvince(provinceId: Int) {

    }
}