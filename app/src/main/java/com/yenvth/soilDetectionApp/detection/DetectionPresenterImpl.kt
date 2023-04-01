package com.yenvth.soilDetectionApp.detection

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.yenvth.soilDetectionApp.R
import com.yenvth.soilDetectionApp.utils.CommonUtils

class DetectionPresenterImpl<V : DetectionView?>(
    private val mContext: Context,
) : DetectionPresenter<V> {
    private val mStorage = FirebaseStorage.getInstance().reference

    override fun saveImageToFirebaseStorage(uri: Uri, labelName: String, isCorrect: Boolean) {
        val mProgressDialog = CommonUtils.showLoadingDialog(mContext)
        val ref = mStorage.child("detections")
            .child("${labelName}-${isCorrect}-${System.currentTimeMillis()}")
        ref.putFile(uri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { result ->
                    mProgressDialog.cancel()
                    val url = result.toString()
                    Log.d("DetectionPresenterImpl", "Success $url")
                }
            }
            .addOnFailureListener {
                mProgressDialog.cancel()
                CommonUtils.showSnackBar(
                    (mContext as Activity),
                    mContext.getString(R.string.upload_failed),
                    false
                )
            }
    }
}