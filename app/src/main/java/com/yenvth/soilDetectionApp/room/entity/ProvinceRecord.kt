package com.yenvth.soilDetectionApp.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "province")
data class ProvinceRecord(
    @PrimaryKey
    @ColumnInfo(name = "id") val provinceId: Int = 0,
    @ColumnInfo(name = "name") val provinceName: String?,
    @ColumnInfo(name = "type") val type: String?
)