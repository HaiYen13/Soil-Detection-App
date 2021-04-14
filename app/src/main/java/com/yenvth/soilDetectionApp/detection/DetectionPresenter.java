package com.yenvth.soilDetectionApp.detection;

import android.net.Uri;

public interface DetectionPresenter<V extends DetectionView> {
    void saveImageToFirebaseStorage(Uri uri);
    void detectSoil(String url);
}
