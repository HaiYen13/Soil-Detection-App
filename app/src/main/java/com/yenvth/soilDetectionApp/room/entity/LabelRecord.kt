package com.yenvth.soilDetectionApp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "labels")
data class LabelRecord(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "label_id") val labelId: Int = 0,
    @ColumnInfo(name = "label_name") val labelName: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "timestamp") val timestamp: Long?
)