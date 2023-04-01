package com.yenvth.soilDetectionApp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.room.Room
import com.yenvth.soilDetectionApp.room.AppDatabase
import com.yenvth.soilDetectionApp.room.ResourceDatabase
import com.yenvth.soilDetectionApp.utils.Constant.RESOURCE_DATABASE_NAME
import com.yenvth.soilDetectionApp.utils.Constant.SOIL_DATABASE_NAME
import com.yenvth.soilDetectionApp.utils.DbHelper

class MyApp : Application() {
    companion object {
        private lateinit var database: AppDatabase
        fun getDatabase(): AppDatabase = database

        private lateinit var resourceDatabase: ResourceDatabase
        fun getResourceDatabase(): ResourceDatabase = resourceDatabase
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        DbHelper.copyDataBase(this)
        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java, SOIL_DATABASE_NAME
        ).build()
//        resourceDatabase = Room.databaseBuilder(
//            this,
//            ResourceDatabase::class.java, RESOURCE_DATABASE_NAME
//        ).build()
    }
}