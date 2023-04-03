package com.yenvth.soilDetectionApp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "district")
data class DistrictRecord(
    @PrimaryKey
    @ColumnInfo(name = "id") val districtId: Int = 0,
    @ColumnInfo(name = "name") val districtName: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "province_id") val provinceId: Int?
)