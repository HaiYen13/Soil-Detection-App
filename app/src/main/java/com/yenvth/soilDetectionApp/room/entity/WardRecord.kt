package com.yenvth.soilDetectionApp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ward")
data class WardRecord(
    @PrimaryKey @ColumnInfo(name = "id") val wardId: Int = 0,
    @ColumnInfo(name = "name") val wardName: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "district_id") val districtId: Int?
)