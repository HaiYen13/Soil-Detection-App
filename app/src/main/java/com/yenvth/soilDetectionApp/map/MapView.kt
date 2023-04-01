package com.yenvth.soilDetectionApp.map

import com.yenvth.soilDetectionApp.models.SoilModel
import java.util.ArrayList

interface MapView {
    fun onGetListSoilSuccess(soilModels: ArrayList<SoilModel>?)
}