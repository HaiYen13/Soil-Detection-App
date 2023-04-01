package com.yenvth.soilDetectionApp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yenvth.soilDetectionApp.room.dao.DistrictDao
import com.yenvth.soilDetectionApp.room.dao.ProvinceDao
import com.yenvth.soilDetectionApp.room.dao.WardDao
import com.yenvth.soilDetectionApp.room.entity.DistrictRecord
import com.yenvth.soilDetectionApp.room.entity.ProvinceRecord
import com.yenvth.soilDetectionApp.room.entity.WardRecord

@Database(
    entities = [
        ProvinceRecord::class,
        DistrictRecord::class,
        WardRecord::class],
    version = 1
)

abstract class ResourceDatabase : RoomDatabase() {
    abstract fun provinceDao(): ProvinceDao
    abstract fun districtDao(): DistrictDao
    abstract fun wardDao(): WardDao
}