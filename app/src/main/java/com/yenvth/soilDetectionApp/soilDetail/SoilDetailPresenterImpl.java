package com.yenvth.soilDetectionApp.soilDetail;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yenvth.soilDetectionApp.api.SoilAPI;
import com.yenvth.soilDetectionApp.base.BasePresenter;
import com.yenvth.soilDetectionApp.models.SoilModel;
import com.yenvth.soilDetectionApp.utils.CommonUtils;
import com.yenvth.soilDetectionApp.utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SoilDetailPresenterImpl<V extends SoilDetailView> extends BasePresenter implements SoilDetailPresenter<V> {

    private Context mContext;
    private SoilDetailView soilDetailView;
    private DatabaseReference mDatabase;
    private SharedPreferences sp;
    private String uid;
    private Retrofit retrofit;

    public SoilDetailPresenterImpl(Context mContext, SoilDetailView soilDetailView) {
        this.mContext = mContext;
        this.soilDetailView = soilDetailView;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sp = mContext.getSharedPreferences("auth", Context.MODE_PRIVATE);
        uid = sp.getString("uid", "");
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void getSoilDetail(int soil_id) {
        showLoading(mContext);
        mDatabase.child("soils").child(soil_id + "").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hideLoading();
                SoilModel soilModel = snapshot.getValue(SoilModel.class);
                soilModel.setSoil_id(Integer.parseInt(snapshot.getKey()));
                soilDetailView.onGetSoilDetails(soilModel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hideLoading();
                CommonUtils.showError((Activity) mContext, "Lấy thông tin thất bại");
            }
        });
    }
}
