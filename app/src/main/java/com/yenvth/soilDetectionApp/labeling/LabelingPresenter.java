package com.yenvth.soilDetectionApp.labeling;

import android.net.Uri;

public interface LabelingPresenter<V extends LabelingView> {
    void getListLabels();

    void addImageLabelFirebase(Uri uri);

    void addLabel(String labelName, String url);

    void deleteLabel(String label_id);
}
