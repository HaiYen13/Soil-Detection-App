package com.yenvth.soilDetectionApp.labeling;

import com.yenvth.soilDetectionApp.models.LabelingModel;

import java.util.ArrayList;

public interface LabelingView {
    void onGetListLabelSuccess(ArrayList<LabelingModel> list);

    void addImageFirebaseSuccess(String url);

    void onAddLabelSuccess();

    void onDeleteLabelSuccess();
}
