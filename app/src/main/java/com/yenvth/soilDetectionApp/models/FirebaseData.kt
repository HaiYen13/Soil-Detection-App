package com.yenvth.soilDetectionApp.models

data class FirebaseData(
    val province_soils: List<ProvinceSoilModel>,
    val soils: List<SoilFirebaseModel>,
)

data class ProvinceSoilModel(
    val province_id: Int,
    val province_name: String,
    val soil_id: Int,
    val soil_name: String
)

data class SoilFirebaseModel(
    val description: String,
    val is_delete:Int,
    val lat: String,
    val lon: String,
    val name_en: String,
    val name_vi: String,
    val soil_code: String,
    val timestamp: Long,
    val url: String,
)