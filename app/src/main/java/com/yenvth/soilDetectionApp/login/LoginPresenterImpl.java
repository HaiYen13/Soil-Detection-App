package com.yenvth.soilDetectionApp.login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.yenvth.soilDetectionApp.base.BasePresenter;
import com.yenvth.soilDetectionApp.utils.CommonUtils;
import com.yenvth.soilDetectionApp.utils.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPresenterImpl<V extends LoginView> extends BasePresenter implements LoginPresenter {
    private Context mContext;
    private DatabaseReference mDatabase;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String uid;
    private LoginView loginView;
    private Retrofit retrofit;

    public LoginPresenterImpl(Context mContext, LoginView loginView) {
        this.mContext = mContext;
        this.loginView = loginView;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sp = mContext.getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        uid = sp.getString("uid", "");
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void doLogin(String phoneNumber, String password) {
        showLoading(mContext);

        mDatabase.child("accounts").child("employees").child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Object pass = (Object) dataSnapshot.child("password").getValue();
                    String uid = (String) dataSnapshot.child("uid").getValue();
                    if (password.equals(pass.toString())) {
                        hideLoading();
                        loginView.onLoginSuccess(uid);
                    } else {
                        hideLoading();
                        CommonUtils.showError((Activity) mContext, "Số điện thoại hoặc mật khẩu không đúng.");
                    }
                } else {
                    hideLoading();
                    CommonUtils.showError((Activity) mContext, "Số điện thoại hoặc mật khẩu không đúng.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
