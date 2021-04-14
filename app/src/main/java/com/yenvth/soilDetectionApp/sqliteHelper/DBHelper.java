package com.yenvth.soilDetectionApp.sqliteHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yenvth.soilDetectionApp.models.ProvinceModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    //    private static String DB_NAME = "SOIL_DICTIONARY";
    private static String DB_PATH = "";
    private static String DB_NAME = "dvhcvn.db";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDataBase;
    private Cursor cursor;

    private final Context mContext;
    private boolean mNeedUpdate = false;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;
        copyDataBase();
        updateDataBase();
        this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }

    public void updateDataBase() {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    public boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    public void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        //InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }


    //-------------FUNCTION FOR PROVINCE TABLE------------------//
    public ArrayList<ProvinceModel> getProvinces() {
        mDataBase = getReadableDatabase();
        cursor = mDataBase.rawQuery("SELECT * FROM province", null);

        ArrayList<ProvinceModel> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String type = cursor.getString(cursor.getColumnIndex("type"));

            list.add(new ProvinceModel(id, name, type));
        }
        closeDB();
        return list;

    }
    //-------------FUNCTION FOR DISTRICT TABLE------------------//

//    public ArrayList<DistrictModel> getDistricts(int _province_id) {
//        mDataBase = getReadableDatabase();
//        cursor = mDataBase.rawQuery("SELECT * FROM district WHERE CAST(province_id AS DECIMAL) = " + _province_id, null);
//
//        ArrayList<DistrictModel> list = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            int id = cursor.getInt(cursor.getColumnIndex("id"));
//            String name = cursor.getString(cursor.getColumnIndex("name"));
//            String type = cursor.getString(cursor.getColumnIndex("type"));
//            int province_id = cursor.getInt(cursor.getColumnIndex("province_id"));
//
//            list.add(new DistrictModel(id, province_id, name, type));
//        }
//        closeDB();
//        return list;
//
//    }
//
//    //-------------FUNCTION FOR WARD TABLE------------------//
//    public ArrayList<WardModel> getWards(int _district_id) {
//        mDataBase = getReadableDatabase();
//        cursor = mDataBase.rawQuery("SELECT * FROM ward WHERE CAST(district_id AS DECIMAL) = " + _district_id, null);
//
//        ArrayList<WardModel> list = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            int id = cursor.getInt(cursor.getColumnIndex("id"));
//            String name = cursor.getString(cursor.getColumnIndex("name"));
//            String type = cursor.getString(cursor.getColumnIndex("type"));
//            int district_id = cursor.getInt(cursor.getColumnIndex("district_id"));
//
//            list.add(new WardModel(id, district_id, name, type));
//        }
//        closeDB();
//        return list;
//
//    }

    //Todo: Đóng Database
    private void closeDB() {
        close();
        if (cursor != null) cursor.close();
    }

}
