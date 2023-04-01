package com.yenvth.soilDetectionApp.utils

import android.content.Context
import android.util.Log
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
        //InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
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
}