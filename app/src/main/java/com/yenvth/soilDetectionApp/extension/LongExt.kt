package com.yenvth.soilDetectionApp.extension

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Long?.toDate(): String {
    if (this == null) return ""
    val date = Date(this)
    val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
    return dateFormat.format(date)
}