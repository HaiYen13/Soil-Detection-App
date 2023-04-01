package com.yenvth.soilDetectionApp.models

data class DetectionModel(
    var detectionId: Int = 0,
    var url: String?,
    var label: String?,
    var timestamp: Long?
)