package com.yenvth.soilDetectionApp.utils;

import com.yenvth.soilDetectionApp.R;

public class Constant {
    public static final String PREFERENCE_NAME = "PREFERENCE_NAME";

    public static final int LOGIN_HISTORY_REQUEST = 1;
    public static final int LOGIN_LABEL_REQUEST = 2;

    public static final String BASE_SWAGGER_URL = "https://virtserver.swaggerhub.com/yenvth99/SoilDictionary/1.0.0/";
    public static  String BASE_URL = "http://192.168.0.101:9191/"; // Chỉnh ip để lấy CSDL trong máy tính
    public static final String BASE_HEROKU_URL = "";

    public class MapEnum {

        public static final double HANOT_LAT = 21.042032590437085;
        public static final double HANOT_LON = 105.82704595974407;

        public static final int WORLD = 1;
        public static final int LANDMASS = 5;
        public static final int HANOI_ZOOM = 6;
        public static final int CITY = 10;
        public static final int STREETS = 15;
        public static final int BUILDING = 20;
    }

    public static int getColorArea(String area) {
        switch (area) {
            case "ao":
                return R.color.orthic_acrisols;
            case "i":
                return R.color.litholsols;
            case "lc":
                return R.color.chromic_luvisols;
            case "af":
                return R.color.ferric_acrisols;
            case "ag":
                return R.color.gleyic_acrisols;
            case "je":
                return R.color.eutric_fluvisols;
            case "ge":
                return R.color.eutric_gleysoils;
            case "jt":
                return R.color.thionic_fluvisols;
            case "re":
                return R.color.eutric_regosols;
            case "gd":
                return R.color.dystric_gleysols;
            case "fr":
                return R.color.rhodic_ferrasols;
            case "fa":
                return R.color.acric_ferrasols;
            case "vp":
                return R.color.pellic_vertisols;
            case "od":
                return R.color.dystric_histosols;
            case "fo":
                return R.color.orthic_ferrasols;
            default:
                return R.color.other;
        }
    }
}
