package com.yenvth.soilDetectionApp.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.yenvth.soilDetectionApp.models.SoilModel
import com.yenvth.soilDetectionApp.room.entity.SoilRecord

@Dao
interface SoilDao {
    fun getSoils() = getSoilRecords().map { it.toModel() }

    @Query("SELECT * FROM soils")
    fun getSoilRecords(): List<SoilRecord>

    @Query("SELECT * FROM soils WHERE soil_id = :soilId")
    fun getSoil(soilId: Int): SoilRecord
}

internal fun SoilRecord.toModel() = SoilModel(
    soilId = this.soilId,
    soilCode = this.soilCode,
    nameVi = this.nameVi,
    nameEn = this.nameEn,
    url = this.url,
    description = this.description,
    lat = this.lat,
    lon = this.lon,
    timestamp = this.timestamp,
)