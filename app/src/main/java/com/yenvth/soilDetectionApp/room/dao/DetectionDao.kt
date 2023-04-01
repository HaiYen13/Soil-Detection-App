package com.yenvth.soilDetectionApp.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.yenvth.soilDetectionApp.models.DetectionModel
import com.yenvth.soilDetectionApp.room.entity.DetectionRecord

@Dao
interface DetectionDao {
    fun getDetections() = getDetectionRecords().map { it.toModel() }

    @Query("SELECT * FROM detections")
    fun getDetectionRecords(): List<DetectionRecord>
}

internal fun DetectionRecord.toModel() = DetectionModel(
    detectionId = this.detectionId,
    url = this.url,
    label = this.label,
    timestamp = this.timestamp,
)

internal fun DetectionModel.toRecord() = DetectionRecord(
    detectionId = this.detectionId,
    url = this.url,
    label = this.label,
    timestamp = this.timestamp,
)
