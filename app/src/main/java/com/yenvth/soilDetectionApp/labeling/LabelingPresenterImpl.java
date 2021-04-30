package com.yenvth.soilDetectionApp.labeling;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.yenvth.soilDetectionApp.base.BasePresenter;
import com.yenvth.soilDetectionApp.models.LabelingModel;
import com.yenvth.soilDetectionApp.utils.CommonUtils;
import com.yenvth.soilDetectionApp.utils.Constant;

import java.util.ArrayList;

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
        mDatabase.child("labels").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<LabelingModel> list = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    LabelingModel model = snapshot1.getValue(LabelingModel.class);
                    model.setLabelId(snapshot1.getKey());
                    list.add(model);
                }
                hideLoading();
                labelingView.onGetListLabelSuccess(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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
        LabelingModel labelingModel = new LabelingModel();
        labelingModel.setUrl(url);
        labelingModel.setLabelName(labelName);
        labelingModel.setUid(uid);
        labelingModel.setTimestamp(System.currentTimeMillis());
        mDatabase.child("labels").child(uid).push().setValue(labelingModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        hideLoading();
                        labelingView.onAddLabelSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideLoading();
                        CommonUtils.showError((Activity) mContext, "Lấy thông tin thất bại");
                    }
                });
    }

    @Override
    public void deleteLabel(String label_id) {
        showLoading(mContext);
        mDatabase.child("labels").child(uid).child(label_id).setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        hideLoading();
                        labelingView.onDeleteLabelSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideLoading();
                        CommonUtils.showError((Activity) mContext, "Lấy thông tin thất bại");
                    }
                });
    }
}
