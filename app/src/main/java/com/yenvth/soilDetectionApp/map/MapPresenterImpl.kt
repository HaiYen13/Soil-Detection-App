package com.yenvth.soilDetectionApp.map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yenvth.soilDetectionApp.api.SoilAPI;
import com.yenvth.soilDetectionApp.base.BasePresenter;
import com.yenvth.soilDetectionApp.models.SoilModel;
import com.yenvth.soilDetectionApp.utils.CommonUtils;
import com.yenvth.soilDetectionApp.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapPresenterImpl<V extends MapView> extends BasePresenter implements MapPresenter<V> {
    private Context mContext;
    private MapView mapView;
    private DatabaseReference mDatabase;
    private SharedPreferences sp;
    private String uid;
    private Retrofit retrofit;

    public MapPresenterImpl(Context mContext, MapView mapView) {
        this.mContext = mContext;
        this.mapView = mapView;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sp = mContext.getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        uid = sp.getString("uid", "");
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void getListSoilByProvince(int provinceId) {
        showLoading(mContext);
        SoilAPI soilAPI = retrofit.create(SoilAPI.class);
        Call<List<SoilModel>> call = soilAPI.getSoilByProvince(provinceId);
        Log.d("Request url", call.request().url() + "");

        call.enqueue(new Callback<List<SoilModel>>() {
            @Override
            public void onResponse(Call<List<SoilModel>> call, Response<List<SoilModel>> response) {
                hideLoading();
                if (response.body() != null) {
                    mapView.onGetListSoilSuccess((ArrayList<SoilModel>) response.body());
                }
            }

            @Override
            public void onFailure(Call<List<SoilModel>> call, Throwable t) {
                hideLoading();
                CommonUtils.showError((Activity) mContext, "Lấy thông tin thất bại");
            }
        });
    }
}
