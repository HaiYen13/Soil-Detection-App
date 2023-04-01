package com.yenvth.soilDetectionApp.models

data class HistoryModel(
    var historyId: Int = 0,
    var soilId: Int? = 0,
    var soilName: String? = null,
    var timestamp: Long? = 0
)