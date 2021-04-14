package com.yenvth.soilDetectionApp.detection;

import com.yenvth.soilDetectionApp.models.SoilDetectModel;

public interface DetectionView {
    void onSaveImageSuccess(String url);
    void onSaveImageFailed();
    void onDetectSuccess(SoilDetectModel soilDetectModel);
}
