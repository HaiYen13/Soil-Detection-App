package com.yenvth.soilDetectionApp.utils

import com.yenvth.soilDetectionApp.R

object Constant {
    const val PREFERENCE_NAME = "soil_pref"
    const val SOIL_DATABASE_NAME = "soil_db"
    const val RESOURCE_DATABASE_NAME = "resource_db"
    const val DIALOG_TIMEOUT = 15000L
    const val BASE_SWAGGER_URL = "https://virtserver.swaggerhub.com/yenvth99/SoilDictionary/1.0.0/"
    fun getColorArea(area: String?): Int {
        return when (area) {
            "ao" -> R.color.orthic_acrisols
            "i" -> R.color.litholsols
            "lc" -> R.color.chromic_luvisols
            "af" -> R.color.ferric_acrisols
            "ag" -> R.color.gleyic_acrisols
            "je" -> R.color.eutric_fluvisols
            "ge" -> R.color.eutric_gleysoils
            "jt" -> R.color.thionic_fluvisols
            "re" -> R.color.eutric_regosols
            "gd" -> R.color.dystric_gleysols
            "fr" -> R.color.rhodic_ferrasols
            "fa" -> R.color.acric_ferrasols
            "vp" -> R.color.pellic_vertisols
            "od" -> R.color.dystric_histosols
            "fo" -> R.color.orthic_ferrasols
            else -> R.color.other
        }
    }

    object MapEnum {
        const val HANOT_LAT = 21.042032590437085
        const val HANOT_LON = 105.82704595974407
        const val WORLD = 1
        const val LANDMASS = 5
        const val HANOI_ZOOM = 6
        const val CITY = 10
        const val STREETS = 15
        const val BUILDING = 20
    }
}