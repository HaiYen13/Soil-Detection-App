package com.yenvth.soilDetectionApp.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yenvth.soilDetectionApp.room.entity.ProvinceSoilRecord

@Dao
interface ProvinceSoilDao {
    @Insert
    fun insertProvinceSoil(record: ProvinceSoilRecord)

    @Query("SELECT * FROM province_soils WHERE province_id = :provinceId")
    fun getProvinceSoilRecords(provinceId: Int): List<ProvinceSoilRecord>
}