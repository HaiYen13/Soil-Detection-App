package com.yenvth.soilDetectionApp.labeling;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yenvth.soilDetectionApp.base.BasePresenter;
import com.yenvth.soilDetectionApp.models.LabelingModel;
import com.yenvth.soilDetectionApp.utils.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LabelingPresenterImpl<V extends LabelingView> extends BasePresenter implements LabelingPresenter<V> {

    private Context mContext;
    private LabelingView labelingView;
    private DatabaseReference mDatabase;
    private SharedPreferences sp;
    private String uid;
    private Retrofit retrofit;

    public LabelingPresenterImpl(Context mContext, LabelingView labelingView) {
        this.mContext = mContext;
        this.labelingView = labelingView;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        sp = mContext.getSharedPreferences("auth", Context.MODE_PRIVATE);
        uid = sp.getString("uid", "");
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_SWAGGER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void getListLabels() {

    }

    @Override
    public void updateLabel(LabelingModel labelingModel) {

    }

    @Override
    public void deleteLabel(int label_id) {

    }
}
