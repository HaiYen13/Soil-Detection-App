package com.yenvth.soilDetectionApp.detection;

import android.net.Uri;

import com.yenvth.soilDetectionApp.models.DetectionModel;

public interface DetectionPresenter<V extends DetectionView> {
    void saveImageToFirebaseStorage(Uri uri);
    void saveLabelDetection(DetectionModel model);
}
