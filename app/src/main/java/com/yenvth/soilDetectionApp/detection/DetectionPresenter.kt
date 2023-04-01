package com.yenvth.soilDetectionApp.detection

import android.net.Uri

interface DetectionPresenter<V : DetectionView?> {
    fun saveImageToFirebaseStorage(uri: Uri, labelName: String, isCorrect: Boolean)
}