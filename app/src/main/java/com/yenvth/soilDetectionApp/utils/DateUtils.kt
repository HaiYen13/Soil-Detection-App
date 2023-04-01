package com.yenvth.soilDetectionApp.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun convertTimes(timestamp: Long, format: String?): String {
        val date = Date(timestamp)
        val sdf = SimpleDateFormat(format)
        return sdf.format(date)
    }
}