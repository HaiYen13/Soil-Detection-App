package com.yenvth.soilDetectionApp.soilDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yenvth.soilDetectionApp.MyApp
import com.yenvth.soilDetectionApp.models.SoilModel
import com.yenvth.soilDetectionApp.room.dao.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SoilDetailViewModel : ViewModel() {
    private val resourceDb = MyApp.getResourceDatabase()

    private val _soil = MutableLiveData<SoilModel>()
    val soil: LiveData<SoilModel> get() = _soil

    fun getSoilDetail(soilId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _soil.postValue(resourceDb.soilDao().getSoil(soilId).toModel())
        }
    }
}