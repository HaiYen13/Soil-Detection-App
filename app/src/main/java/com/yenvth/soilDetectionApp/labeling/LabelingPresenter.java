package com.yenvth.soilDetectionApp.labeling;

import com.yenvth.soilDetectionApp.models.LabelingModel;

public interface LabelingPresenter<V extends LabelingView> {
    void getListLabels();

    void updateLabel(LabelingModel labelingModel);

    void deleteLabel(int label_id);
}
