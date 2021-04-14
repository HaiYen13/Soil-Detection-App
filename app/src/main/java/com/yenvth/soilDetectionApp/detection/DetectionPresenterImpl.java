package com.yenvth.soilDetectionApp.detection;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.yenvth.soilDetectionApp.api.SoilAPI;
import com.yenvth.soilDetectionApp.base.BasePresenter;
import com.yenvth.soilDetectionApp.models.SoilDetectModel;
import com.yenvth.soilDetectionApp.utils.CommonUtils;
import com.yenvth.soilDetectionApp.utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetectionPresenterImpl<V extends DetectionView> extends BasePresenter implements DetectionPresenter<V> {
    private Context mContext;
    private DetectionView detectionView;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private SharedPreferences sp;
    private String uid;
    private Retrofit retrofit;

    public DetectionPresenterImpl(Context mContext, DetectionView detectionView) {
        this.mContext = mContext;
        this.detectionView = detectionView;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance().getReference();
        sp = mContext.getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        uid = sp.getString("uid", "");
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_SWAGGER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void saveImageToFirebaseStorage(Uri uri) {
        showLoading(mContext);
        detectionView.onSaveImageSuccess("url_test");
        hideLoading();
        return;
//        final StorageReference ref;
//        ref = mStorage.child("detection").child(mDatabase.push().getKey());
//
//        ref.putFile(uri)
//                .addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri1 -> {
//                    String url = uri1.toString();
//                    detectionView.onSaveImageSuccess(url);
//                }))
//                .addOnFailureListener(e -> detectionView.onSaveImageFailed());
    }

    @Override
    public void detectSoil(String url) {
        showLoading(mContext);
        SoilAPI soilAPI = retrofit.create(SoilAPI.class);
        Call<SoilDetectModel> call = soilAPI.detectSoils(url);
        Log.d("Request url", call.request().url() + "");

        call.enqueue(new Callback<SoilDetectModel>() {
            @Override
            public void onResponse(Call<SoilDetectModel> call, Response<SoilDetectModel> response) {
                hideLoading();
                if (response.body() != null) {
                    detectionView.onDetectSuccess((SoilDetectModel) response.body());
                }
            }

            @Override
            public void onFailure(Call<SoilDetectModel> call, Throwable t) {
                hideLoading();
                CommonUtils.showError((Activity) mContext, "Lấy thông tin thất bại");
            }
        });
    }
}
