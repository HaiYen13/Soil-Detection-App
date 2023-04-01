package com.yenvth.soilDetectionApp.diction

import com.yenvth.soilDetectionApp.models.SoilModel

interface DictionView {
    fun onGetListSoilSuccess(soilModels: List<SoilModel>)
    fun onSaveHistorySuccess()
}