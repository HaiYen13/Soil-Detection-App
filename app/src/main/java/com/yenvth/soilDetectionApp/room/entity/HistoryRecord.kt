package com.yenvth.soilDetectionApp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "histories")
data class HistoryRecord(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "history_id")
    val historyId: Int = 0,
    @ColumnInfo(name = "soil_id")
    val soilId: Int?,
    @ColumnInfo(name = "timestamp")
    val timestamp: Long?
)