package com.yenvth.soilDetectionApp.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.yenvth.soilDetectionApp.models.ProvinceModel
import com.yenvth.soilDetectionApp.room.entity.ProvinceRecord

@Dao
interface ProvinceDao {
    fun getProvinces() = getProvinceRecords().map { it.toModel() }

    @Query("SELECT * FROM province")
    fun getProvinceRecords(): List<ProvinceRecord>

    @Query("SELECT * FROM province WHERE id = :provinceId")
    fun getProvince(provinceId: Int): ProvinceRecord
}

internal fun ProvinceRecord.toModel() = ProvinceModel(
    provinceId = this.provinceId,
    provinceName = this.provinceName,
    type = this.type
)