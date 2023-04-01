package com.yenvth.soilDetectionApp.utils

import java.lang.StringBuilder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.IOException
import android.content.Context

object FileUtils {
    fun readFile(context: Context, fileName: String?): String {
        val result = StringBuilder()
        try {
            val `is` = context.assets.open(fileName!!)
            val reader = BufferedReader(InputStreamReader(`is`, "utf-8"))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                result.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return result.toString()
    }
}