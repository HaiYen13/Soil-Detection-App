package com.yenvth.soilDetectionApp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "soils")
data class SoilRecord(
    @PrimaryKey @ColumnInfo(name = "soil_id") val soilId: Int,
    @ColumnInfo(name = "soil_code") val soilCode: String?,
    @ColumnInfo(name = "name_vi") val nameVi: String?,
    @ColumnInfo(name = "name_en") val nameEn: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "lat") val lat: Double?,
    @ColumnInfo(name = "lon") val lon: Double?,
    @ColumnInfo(name = "timestamp") val timestamp: Long?,
)