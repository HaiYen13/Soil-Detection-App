package com.yenvth.soilDetectionApp.labeling;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.yenvth.soilDetectionApp.api.LabelAPI;
import com.yenvth.soilDetectionApp.base.BasePresenter;
import com.yenvth.soilDetectionApp.base.BaseResponse;
import com.yenvth.soilDetectionApp.models.LabelingModel;
import com.yenvth.soilDetectionApp.utils.CommonUtils;
import com.yenvth.soilDetectionApp.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LabelingPresenterImpl<V extends LabelingView> extends BasePresenter implements LabelingPresenter<V> {

    private Context mContext;
    private LabelingView labelingView;
    private DatabaseReference mDatabase;
    private SharedPreferences sp;
    private String uid;
    private Retrofit retrofit;
    private StorageReference mStorage;

    public LabelingPresenterImpl(Context mContext, LabelingView labelingView) {
        this.mContext = mContext;
        this.labelingView = labelingView;
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sp = mContext.getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);
        uid = sp.getString("uid", "");
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void getListLabels() {
        showLoading(mContext);
        LabelAPI labelAPI = retrofit.create(LabelAPI.class);
        Call<List<LabelingModel>> call = labelAPI.getListLabels(uid);
        Log.d("Request url", call.request().url() + "");
        call.enqueue(new Callback<List<LabelingModel>>() {
            @Override
            public void onResponse(Call<List<LabelingModel>> call, Response<List<LabelingModel>> response) {
                if (response.body() != null) {
                    hideLoading();
                    labelingView.onGetListLabelSuccess((ArrayList<LabelingModel>) response.body());
                }
            }

            @Override
            public void onFailure(Call<List<LabelingModel>> call, Throwable t) {
                hideLoading();
                CommonUtils.showError((Activity) mContext, "Lấy thông tin thất bại");
            }
        });
    }

    @Override
    public void addImageLabelFirebase(Uri uri) {
        showLoading(mContext);
        final StorageReference ref;
        ref = mStorage.child("labeling").child(mDatabase.push().getKey());

        ref.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> ref.getDownloadUrl().addOnSuccessListener(uri1 -> {
                    hideLoading();
                    String url = uri1.toString();
                    labelingView.addImageFirebaseSuccess(url);

                }))
                .addOnFailureListener(e -> {
                    hideLoading();
                    CommonUtils.showError((Activity) mContext, "Tải lên thất bại");
                });
    }

    @Override
    public void addLabel(String labelName, String url) {
        showLoading(mContext);
        LabelAPI labelAPI = retrofit.create(LabelAPI.class);
        LabelingModel labelingModel = new LabelingModel();
        labelingModel.setUrl(url);
        labelingModel.setLabelName(labelName);
        labelingModel.setUid(uid);
        labelingModel.setTimestamp(System.currentTimeMillis());
        Call<BaseResponse> call = labelAPI.addLabel(labelingModel);
        Log.d("Request url", call.request().url() + "");
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                hideLoading();
                if (response.body() != null) {
                    labelingView.onAddLabelSuccess();
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
    public void deleteLabel(int label_id) {
        showLoading(mContext);
        LabelAPI labelAPI = retrofit.create(LabelAPI.class);
        Call<BaseResponse> call = labelAPI.deleteLabel(label_id);
        Log.d("Request url", call.request().url() + "");
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                hideLoading();
                if (response.body() != null) {
                    labelingView.onDeleteLabelSuccess();
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
