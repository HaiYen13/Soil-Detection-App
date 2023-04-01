package com.yenvth.soilDetectionApp.soilDetail

import com.yenvth.soilDetectionApp.models.SoilModel

interface SoilDetailView {
    fun onGetSoilDetails(soilModel: SoilModel)
}