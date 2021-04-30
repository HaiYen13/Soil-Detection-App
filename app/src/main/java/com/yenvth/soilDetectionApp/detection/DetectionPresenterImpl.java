package com.yenvth.soilDetectionApp.detection;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.yenvth.soilDetectionApp.base.BasePresenter;
import com.yenvth.soilDetectionApp.models.DetectionModel;
import com.yenvth.soilDetectionApp.utils.CommonUtils;
import com.yenvth.soilDetectionApp.utils.Constant;

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
        final StorageReference ref;
        ref = mStorage.child("detection").child(mDatabase.push().getKey());

        ref.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri1 -> {
                    hideLoading();
                    String url = uri1.toString();
                    detectionView.onSaveImageSuccess(url);
                }))
                .addOnFailureListener(e -> {
                            hideLoading();
                            CommonUtils.showError((Activity) mContext, "Lấy đường dẫn thất bại");
                        }
                );
    }

    @Override
    public void saveLabelDetection(DetectionModel model) {
        mDatabase.child("detections").push().setValue(model);
    }
}
