package com.yenvth.soilDetectionApp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detections")
data class DetectionRecord(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "detection_id")
    val detectionId: Int,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "label") val label: String?,
    @ColumnInfo(name = "timestamp") val timestamp: Long?
)