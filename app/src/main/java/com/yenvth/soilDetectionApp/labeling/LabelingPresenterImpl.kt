package com.yenvth.soilDetectionApp.labeling

import android.app.Activity
import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.yenvth.soilDetectionApp.MyApp
import com.yenvth.soilDetectionApp.R
import com.yenvth.soilDetectionApp.room.entity.LabelRecord
import com.yenvth.soilDetectionApp.utils.CommonUtils


class LabelingPresenterImpl<V : LabelingView?>(
    private val mContext: Context,
    private val labelingView: LabelingView
) : LabelingPresenter<V> {
    private val db = MyApp.getDatabase()
    private val mStorage = FirebaseStorage.getInstance().reference

    override fun getLabels() {
        labelingView.onGetListLabelSuccess(db.labelDao().getLabels())
    }

    override fun addLabel(labelName: String, uri: Uri) {
        val mProgressDialog = CommonUtils.showLoadingDialog(mContext)
        val ref = mStorage.child("labels").child("$labelName${System.currentTimeMillis()}")
        ref.putFile(uri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { result ->
                    mProgressDialog.cancel()
                    val url = result.toString()
                    db.labelDao().insertLabel(
                        LabelRecord(
                            url = url,
                            labelName = labelName,
                            timestamp = System.currentTimeMillis()
                        )
                    )
                    labelingView.onAddLabelSuccess()
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

    override fun deleteLabel(labelId: Int) {
        db.labelDao().deleteLabel(labelId)
        labelingView.onDeleteLabelSuccess()
    }
}