package com.yenvth.soilDetectionApp.history;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
        mDatabase.child("histories").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<HistoryModel> list = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    HistoryModel historyModel = snapshot1.getValue(HistoryModel.class);
                    historyModel.setHistoriesId(snapshot1.getKey());
                    list.add(historyModel);
                }
                hideLoading();
                historyView.onGetListHistorySuccess(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hideLoading();
            }
        });
    }

    @Override
    public void deleteHistory(String history_id) {
        showLoading(mContext);
        mDatabase.child("histories").child(uid).child(history_id+"").setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        hideLoading();
                        historyView.onDeleteHistorySuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideLoading();
                    }
                });
    }

    @Override
    public void deleteAllHistories() {
        showLoading(mContext);
        mDatabase.child("histories").child(uid).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        hideLoading();
                        historyView.onDeleteAllHistoriesSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideLoading();
                    }
                });
    }
}
