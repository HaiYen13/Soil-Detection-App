package com.yenvth.soilDetectionApp.diction;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yenvth.soilDetectionApp.api.HistoryAPI;
import com.yenvth.soilDetectionApp.api.SoilAPI;
import com.yenvth.soilDetectionApp.base.BasePresenter;
import com.yenvth.soilDetectionApp.base.BaseResponse;
import com.yenvth.soilDetectionApp.models.HistoryModel;
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
        sp = mContext.getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        uid = sp.getString("uid", "");
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void saveSearchHistory(HistoryModel historyModel) {
        HistoryAPI historyAPI = retrofit.create(HistoryAPI.class);
        historyModel.setUid(uid);
        Call<BaseResponse> call = historyAPI.saveHistory(historyModel);
        Log.d("Request url", call.request().url() + "");
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                hideLoading();
                if (response.body() != null) {
                    dictionView.onSaveHistorySuccess();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                hideLoading();
                CommonUtils.showError((Activity) mContext, "Lấy thông tin thất bại");
            }
        });
    }

    @Override
    public void getListSoil(String queryString) {
//        showLoading(mContext);

        SoilAPI soilAPI = retrofit.create(SoilAPI.class);
        Call<List<SoilModel>> call = soilAPI.getListSoils(queryString);
        Log.d("Request url", call.request().url() + "");
        call.enqueue(new Callback<List<SoilModel>>() {
            @Override
            public void onResponse(Call<List<SoilModel>> call, Response<List<SoilModel>> response) {
                if (response.body() != null) {
//                    hideLoading();
                    dictionView.onGetListSoilSuccess((ArrayList<SoilModel>) response.body());
                }
            }

            @Override
            public void onFailure(Call<List<SoilModel>> call, Throwable t) {
//                hideLoading();
                CommonUtils.showError((Activity) mContext, "Lấy thông tin thất bại");
            }
        });
    }
}
