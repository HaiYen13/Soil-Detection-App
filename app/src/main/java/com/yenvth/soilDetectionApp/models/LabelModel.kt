package com.yenvth.soilDetectionApp.models

data class LabelModel(
    var labelId: Int = 0,
    var labelName: String? = null,
    var url: String? = null,
    var uid: String? = null,
    var timestamp: Long? = 0
)
