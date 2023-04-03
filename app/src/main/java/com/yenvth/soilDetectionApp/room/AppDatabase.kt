package com.yenvth.soilDetectionApp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yenvth.soilDetectionApp.room.dao.*
import com.yenvth.soilDetectionApp.room.entity.*

@Database(
    entities = [
        HistoryRecord::class,
        LabelRecord::class,
        DetectionRecord::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun labelDao(): LabelDao
    abstract fun detectionDao(): DetectionDao
}