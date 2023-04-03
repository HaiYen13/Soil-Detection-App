package com.yenvth.soilDetectionApp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "province_soils")
data class ProvinceSoilRecord(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int? = 0,
    @ColumnInfo(name = "soil_id") val soilId: Int,
    @ColumnInfo(name = "province_id") val provinceId: Int?,
    @ColumnInfo(name = "province_name") val provinceName: String?
)