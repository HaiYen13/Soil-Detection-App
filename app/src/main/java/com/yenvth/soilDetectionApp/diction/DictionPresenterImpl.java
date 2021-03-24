package com.yenvth.soilDetectionApp.diction;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yenvth.soilDetectionApp.api.ResourceAPI;
import com.yenvth.soilDetectionApp.api.SoilAPI;
import com.yenvth.soilDetectionApp.base.BasePresenter;
import com.yenvth.soilDetectionApp.models.DistrictModel;
import com.yenvth.soilDetectionApp.models.ProvinceModel;
import com.yenvth.soilDetectionApp.models.SoilModel;
import com.yenvth.soilDetectionApp.models.SoilParamModel;
import com.yenvth.soilDetectionApp.models.WardModel;
import com.yenvth.soilDetectionApp.utils.CommonUtils;
import com.yenvth.soilDetectionApp.utils.Constant;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DictionPresenterImpl<V extends DictionView> extends BasePresenter implements DictionPresenter<V> {
    private Context mContext;
    private DictionView dictionView;
    private DatabaseReference mDatabase;
    private SharedPreferences sp;
    private String uid;
    private Retrofit retrofit;

    public DictionPresenterImpl(Context mContext, DictionView dictionView) {
        this.mContext = mContext;
        this.dictionView = dictionView;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sp = mContext.getSharedPreferences("auth", Context.MODE_PRIVATE);
        uid = sp.getString("uid", "");
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_SWAGGER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void getListSoil(String queryString, SoilParamModel soilParamModel) {
        SoilAPI soilAPI = retrofit.create(SoilAPI.class);
        Call<List<SoilModel>> call = soilAPI.getListSoils(
                queryString,
                soilParamModel.getProvince_id(),
                soilParamModel.getDistrict_id(),
                soilParamModel.getWard_id(),
                soilParamModel.getLat(),
                soilParamModel.getLon());
        call.enqueue(new Callback<List<SoilModel>>() {
            @Override
            public void onResponse(Call<List<SoilModel>> call, Response<List<SoilModel>> response) {
                if (response.body() != null) {
                    dictionView.onGetListSoilSuccess((ArrayList<SoilModel>) response.body());
                }
            }

            @Override
            public void onFailure(Call<List<SoilModel>> call, Throwable t) {
                CommonUtils.showError((Activity) mContext, "Lấy thông tin thất bại");
            }
        });
    }

    @Override
    public void getListProvince() {
        ResourceAPI resourceAPI = retrofit.create(ResourceAPI.class);
        Call<List<ProvinceModel>> call = resourceAPI.getListProvinces();
        call.enqueue(new Callback<List<ProvinceModel>>() {
            @Override
            public void onResponse(Call<List<ProvinceModel>> call, Response<List<ProvinceModel>> response) {
                if (response.body() != null) {
                    dictionView.onGetListProvinceSuccess((ArrayList<ProvinceModel>) response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ProvinceModel>> call, Throwable t) {
                CommonUtils.showError((Activity) mContext, "Lấy thông tin thất bại");
            }
        });
    }

    @Override
    public void getListDistrict(int province_id) {
        ResourceAPI resourceAPI = retrofit.create(ResourceAPI.class);
        Call<List<DistrictModel>> call = resourceAPI.getListDistricts(province_id);
        call.enqueue(new Callback<List<DistrictModel>>() {
            @Override
            public void onResponse(Call<List<DistrictModel>> call, Response<List<DistrictModel>> response) {
                if (response.body() != null) {
                    dictionView.onGetListDistrictSuccess((ArrayList<DistrictModel>) response.body());
                }
            }

            @Override
            public void onFailure(Call<List<DistrictModel>> call, Throwable t) {
                CommonUtils.showError((Activity) mContext, "Lấy thông tin thất bại");
            }
        });

    }

    @Override
    public void getListWard(int district_id) {
        ResourceAPI resourceAPI = retrofit.create(ResourceAPI.class);
        Call<List<WardModel>> call = resourceAPI.getListWards(district_id);
        call.enqueue(new Callback<List<WardModel>>() {
            @Override
            public void onResponse(Call<List<WardModel>> call, Response<List<WardModel>> response) {
                if (response.body() != null) {
                    dictionView.onGetListWardSuccess((ArrayList<WardModel>) response.body());
                }
            }

            @Override
            public void onFailure(Call<List<WardModel>> call, Throwable t) {
                CommonUtils.showError((Activity) mContext, "Lấy thông tin thất bại");
            }
        });
    }
}
