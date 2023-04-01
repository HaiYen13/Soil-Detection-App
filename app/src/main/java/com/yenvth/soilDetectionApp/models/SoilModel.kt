package com.yenvth.soilDetectionApp.models

data class SoilModel(
    var soilId: Int = 0,
    var soilCode: String? = null,
    var nameVi: String? = null,
    var nameEn: String? = null,
    var url: String? = null,
    var description: String? = null,
    var lat: Double? = 0.0,
    var lon: Double? = 0.0,
    var timestamp: Long? = 0
)
