package com.yenvth.soilDetectionApp.models

data class DistrictModel(
    var districtId: Int = 0,
    var provinceId: Int = 0,
    var districtName: String? = null,
    var type: String? = null
) {
    override fun toString(): String {
        return districtName!!
    }
}