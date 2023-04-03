package com.yenvth.soilDetectionApp.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.yenvth.soilDetectionApp.models.DistrictModel
import com.yenvth.soilDetectionApp.room.entity.DistrictRecord

@Dao
interface DistrictDao {
    fun getDistricts() = getDistrictRecords().map { it.toModel() }

    @Query("SELECT * FROM district")
    fun getDistrictRecords(): List<DistrictRecord>

    @Query("SELECT * FROM district WHERE id = :districtId")
    fun getDistrict(districtId: Int): DistrictRecord
}

internal fun DistrictRecord.toModel() = DistrictModel(
    districtId = this.districtId,
    districtName = this.districtName,
    type = this.type,
    provinceId = this.provinceId ?: 0,
)