package com.yenvth.soilDetectionApp.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yenvth.soilDetectionApp.base.BasePresenter;
import com.yenvth.soilDetectionApp.models.UserModel;
import com.yenvth.soilDetectionApp.utils.CommonUtils;
import com.yenvth.soilDetectionApp.utils.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenterImpl<V extends MainView> extends BasePresenter implements MainPresenter {
    private Context mContext;
    private DatabaseReference mDatabase;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String uid;
    private MainView mainView;
    private Retrofit retrofit;

    public MainPresenterImpl(Context mContext, MainView mainView) {
        this.mContext = mContext;
        this.mainView = mainView;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sp = mContext.getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        uid = sp.getString("uid", "");
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void getInformationAfterLogin() {
        showLoading(mContext);

        mDatabase.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    hideLoading();
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    mainView.onGetInformationSuccess(userModel);
                } else {
                    hideLoading();
                    CommonUtils.showError((Activity) mContext, "Lấy thông tin không thành công.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
