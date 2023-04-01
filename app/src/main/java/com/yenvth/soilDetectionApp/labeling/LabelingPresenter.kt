package com.yenvth.soilDetectionApp.labeling

import android.net.Uri


interface LabelingPresenter<V : LabelingView?> {
    fun getLabels()
    fun addLabel(labelName: String, uri: Uri)
    fun deleteLabel(labelId: Int)
}