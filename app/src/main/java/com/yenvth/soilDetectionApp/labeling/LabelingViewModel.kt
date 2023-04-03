package com.yenvth.soilDetectionApp.labeling

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.yenvth.soilDetectionApp.MyApp
import com.yenvth.soilDetectionApp.R
import com.yenvth.soilDetectionApp.models.LabelModel
import com.yenvth.soilDetectionApp.room.entity.LabelRecord
import com.yenvth.soilDetectionApp.utils.CommonUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LabelingViewModel : ViewModel() {
    private val db = MyApp.getDatabase()
    private val mStorage = FirebaseStorage.getInstance().reference

    private val _addStatus = MutableLiveData<Int>()
    val addStatus: LiveData<Int> get() = _addStatus

    private val _deleteStatus = MutableLiveData<Int>()
    val deleteStatus: LiveData<Int> get() = _deleteStatus

    private val _labels = MutableLiveData<List<LabelModel>>()
    val labels: LiveData<List<LabelModel>> get() = _labels

    fun getLabels() {
        viewModelScope.launch(Dispatchers.IO) {
            _labels.postValue(db.labelDao().getLabels())
        }
    }

    fun addLabel(context: Context, labelName: String, uri: Uri) {
        val mProgressDialog = CommonUtils.showLoadingDialog(context)
        val ref = mStorage.child("labels").child("$labelName${System.currentTimeMillis()}")
        ref.putFile(uri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { result ->
                    mProgressDialog.cancel()
                    val url = result.toString()
                    viewModelScope.launch(Dispatchers.IO) {
                        db.labelDao().insertLabel(
                            LabelRecord(
                                url = url,
                                labelName = labelName,
                                timestamp = System.currentTimeMillis()
                            )
                        )
                        _addStatus.postValue(1)
                    }
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

    fun deleteLabel(labelId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            db.labelDao().deleteLabel(labelId)
            _deleteStatus.postValue(1)
        }
    }
}