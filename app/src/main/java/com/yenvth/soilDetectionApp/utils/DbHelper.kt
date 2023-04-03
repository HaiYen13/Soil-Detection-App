package com.yenvth.soilDetectionApp.utils

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.yenvth.soilDetectionApp.MyApp
import com.yenvth.soilDetectionApp.models.FirebaseData
import com.yenvth.soilDetectionApp.room.entity.ProvinceSoilRecord
import com.yenvth.soilDetectionApp.room.entity.SoilRecord
import java.io.*

object DbHelper {
    private var DB_PATH = ""
    private const val DB_NAME = "dvhcvn.db"
    private fun isExist(context: Context): Boolean {
        DB_PATH = context.applicationInfo.dataDir + "/databases/"
        val dbFile = File(DB_PATH + DB_NAME)
        return dbFile.exists()
    }

    private fun copyDatabase(context: Context) {
        val mInput: InputStream = context.assets.open(DB_NAME)
        val mOutput: OutputStream = FileOutputStream(DB_PATH + DB_NAME)
        val mBuffer = ByteArray(1024)
        var mLength: Int
        while (mInput.read(mBuffer).also { mLength = it } > 0) mOutput.write(mBuffer, 0, mLength)
        mOutput.flush()
        mOutput.close()
        mInput.close()
    }

    fun copyDataBase(context: Context) {
        try {
            if (!isExist(context)) {
                copyDatabase(context)
            }
        } catch (e: IOException) {
            Log.d("DbHelper", "copy DB failed ${e.printStackTrace()}")
        }
    }

    fun importResources(context: Context, fileName: String) {
        val inputStream = context.assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val jsonString = String(buffer, Charsets.UTF_8)
        Gson().fromJson(jsonString, FirebaseData::class.java).also {
            it.soils.forEachIndexed { index, soil ->
                Log.d("soil", soil.toString())
                MyApp.getResourceDatabase().soilDao().insertSoil(
                    SoilRecord(
                        soilId = index + 1,
                        soilCode = soil.soil_code,
                        nameVi = soil.name_vi,
                        nameEn = soil.name_en,
                        description = soil.description,
                        url = soil.url,
                        lat = soil.lat.toDouble(),
                        lon = soil.lon.toDouble(),
                        timestamp = soil.timestamp,
                    )
                )
            }

            it.province_soils.forEachIndexed { index, provinceSoil ->
                Log.d("province_soil", provinceSoil.toString())
                MyApp.getResourceDatabase().provinceSoilDao().insertProvinceSoil(
                    ProvinceSoilRecord(
                        id = index + 1,
                        soilId = provinceSoil.soil_id,
                        provinceId = provinceSoil.province_id,
                        provinceName = provinceSoil.province_name
                    )
                )
            }
        }

    }
}