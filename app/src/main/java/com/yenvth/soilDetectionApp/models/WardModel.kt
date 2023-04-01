package com.yenvth.soilDetectionApp.models

data class WardModel(
    var wardId: Int = 0,
    var districtId: Int = 0,
    var wardName: String? = null,
    var type: String? = null
) {
    override fun toString(): String {
        return wardName!!
    }
}