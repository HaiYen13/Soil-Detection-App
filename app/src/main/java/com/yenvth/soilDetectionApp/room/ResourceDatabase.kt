package com.yenvth.soilDetectionApp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yenvth.soilDetectionApp.room.dao.*
import com.yenvth.soilDetectionApp.room.entity.*

@Database(
    entities = [
        ProvinceRecord::class,
        DistrictRecord::class,
        WardRecord::class,
        SoilRecord::class,
        ProvinceSoilRecord::class],
    version = 1
)

abstract class ResourceDatabase : RoomDatabase() {
    abstract fun provinceDao(): ProvinceDao
    abstract fun districtDao(): DistrictDao
    abstract fun wardDao(): WardDao
    abstract fun soilDao(): SoilDao
    abstract fun provinceSoilDao(): ProvinceSoilDao
}