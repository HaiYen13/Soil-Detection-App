package com.yenvth.soilDetectionApp.models

data class ProvinceModel(
    var provinceId: Int = 0,
    var provinceName: String? = null,
    var type: String? = null
) {
    override fun toString(): String {
        return provinceName!!
    }
}