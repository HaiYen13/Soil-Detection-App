package com.yenvth.soilDetectionApp.labeling

import com.yenvth.soilDetectionApp.models.LabelModel

interface LabelingView {
    fun onGetListLabelSuccess(list: List<LabelModel>)
    fun onAddLabelSuccess()
    fun onDeleteLabelSuccess()
    fun showLoading()
    fun hideLoading()
}