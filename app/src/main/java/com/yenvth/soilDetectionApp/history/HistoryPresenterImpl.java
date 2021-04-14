package com.yenvth.soilDetectionApp.history;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yenvth.soilDetectionApp.api.HistoryAPI;
import com.yenvth.soilDetectionApp.base.BasePresenter;
import com.yenvth.soilDetectionApp.base.BaseResponse;
import com.yenvth.soilDetectionApp.models.HistoryModel;
import com.yenvth.soilDetectionApp.utils.CommonUtils;
import com.yenvth.soilDetectionApp.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistoryPresenterImpl<V extends HistoryView> extends BasePresenter implements HistoryPresenter<V> {
    private Context mContext;
    private HistoryView historyView;
    private DatabaseReference mDatabase;
    private SharedPreferences sp;
    private String uid;
    private Retrofit retrofit;

    public HistoryPresenterImpl(Context mContext, HistoryView historyView) {
        this.mContext = mContext;
        this.historyView = historyView;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sp = mContext.getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        uid = sp.getString("uid", "");
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void getListHistories() {
        showLoading(mContext);

        HistoryAPI historyAPI = retrofit.create(HistoryAPI.class);
        Call<List<HistoryModel>> call = historyAPI.getListHistories(uid);
        Log.d("Request url", call.request().url() + "");
        call.enqueue(new Callback<List<HistoryModel>>() {
            @Override
            public void onResponse(Call<List<HistoryModel>> call, Response<List<HistoryModel>> response) {
                hideLoading();
                if (response.body() != null) {
                    historyView.onGetListHistorySuccess((ArrayList<HistoryModel>) response.body());
                }
            }

            @Override
            public void onFailure(Call<List<HistoryModel>> call, Throwable t) {
                hideLoading();
                CommonUtils.showError((Activity) mContext, "Lấy thông tin thất bại");
            }
        });
    }

    @Override
    public void deleteHistory(int history_id) {
        showLoading(mContext);

        HistoryAPI historyAPI = retrofit.create(HistoryAPI.class);
        Call<BaseResponse> call = historyAPI.deleteHistory(history_id);
        Log.d("Request url", call.request().url() + "");
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                hideLoading();
                if (response.body() != null) {
                    historyView.onDeleteHistorySuccess();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                hideLoading();
//                CommonUtils.showError((Activity) mContext, "Lấy thông tin thất bại");
            }
        });
    }

    @Override
    public void deleteAllHistories() {
        showLoading(mContext);

        HistoryAPI historyAPI = retrofit.create(HistoryAPI.class);
        Call<BaseResponse> call = historyAPI.deleteAllHistories(uid);
        Log.d("Request url", call.request().url() + "");
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                hideLoading();
                if (response.body() != null) {
                    historyView.onDeleteAllHistoriesSuccess();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                hideLoading();
//                CommonUtils.showError((Activity) mContext, "Lấy thông tin thất bại");
            }
        });
    }
}
