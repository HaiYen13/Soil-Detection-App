package com.yenvth.soilDetectionApp.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.yenvth.soilDetectionApp.models.WardModel
import com.yenvth.soilDetectionApp.room.entity.WardRecord

@Dao
interface WardDao {
    fun getWards() = getWardRecords().map { it.toModel() }

    @Query("SELECT * FROM district")
    fun getWardRecords(): List<WardRecord>

    @Query("SELECT * FROM district WHERE id = :districtId")
    fun getWard(districtId: Int): WardRecord
}

internal fun WardRecord.toModel() = WardModel(
    wardId = this.wardId,
    wardName = this.wardName,
    type = this.type,
    districtId = this.districtId
)