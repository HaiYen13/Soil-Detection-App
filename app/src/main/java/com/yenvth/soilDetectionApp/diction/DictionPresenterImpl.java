package com.yenvth.soilDetectionApp.diction;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yenvth.soilDetectionApp.base.BasePresenter;
import com.yenvth.soilDetectionApp.models.HistoryModel;
import com.yenvth.soilDetectionApp.models.SoilModel;
import com.yenvth.soilDetectionApp.utils.CommonUtils;
import com.yenvth.soilDetectionApp.utils.Constant;

import java.util.ArrayList;

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
        mDatabase.child("histories").push().setValue(historyModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dictionView.onSaveHistorySuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        CommonUtils.showError((Activity) mContext, "Lấy thông tin thất bại");
                    }
                });
    }

    @Override
    public void getListSoil(String queryString) {

//        SoilAPI soilAPI = retrofit.create(SoilAPI.class);
//        Call<List<SoilModel>> call = soilAPI.getListSoils(queryString);
//        Log.d("Request url", call.request().url() + "");
//        call.enqueue(new Callback<List<SoilModel>>() {
//            @Override
//            public void onResponse(Call<List<SoilModel>> call, Response<List<SoilModel>> response) {
//                if (response.body() != null) {
////                    hideLoading();
//                    dictionView.onGetListSoilSuccess((ArrayList<SoilModel>) response.body());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<SoilModel>> call, Throwable t) {
////                hideLoading();
//                CommonUtils.showError((Activity) mContext, "Lấy thông tin thất bại");
//            }
//        });
        mDatabase.child("soils").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<SoilModel> list = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    SoilModel soilModel = snapshot1.getValue(SoilModel.class);
                    soilModel.setSoil_id(Integer.parseInt(snapshot1.getKey()));
                    if (soilModel.getName_vi().contains(queryString) || soilModel.getName_en().contains(queryString)) {
                        list.add(soilModel);
                    }
                }
                dictionView.onGetListSoilSuccess(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                CommonUtils.showError((Activity) mContext, "Lấy thông tin thất bại");
            }
        });
    }
}
