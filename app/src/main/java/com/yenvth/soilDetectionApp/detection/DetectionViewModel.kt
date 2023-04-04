package com.yenvth.soilDetectionApp.detection

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage
import com.yenvth.soilDetectionApp.MyApp
import com.yenvth.soilDetectionApp.R
import com.yenvth.soilDetectionApp.models.SoilModel
import com.yenvth.soilDetectionApp.utils.CommonUtils

class DetectionViewModel : ViewModel() {
    private val db = MyApp.getDatabase()
    private val resourceDb = MyApp.getResourceDatabase()
    private val mStorage = FirebaseStorage.getInstance().reference

    private val _soil = MutableLiveData<SoilModel>()
    val soil: LiveData<SoilModel> get() = _soil

    fun saveImageToFirebaseStorage(
        context: Context,
        uri: Uri,
        labelName: String,
        isCorrect: Boolean
    ) {
        val mProgressDialog = CommonUtils.showLoadingDialog(context)
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
                    (context as Activity),
                    context.getString(R.string.upload_failed),
                    false
                )
            }
    }
}