package com.yenvth.soilDetectionApp.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yenvth.soilDetectionApp.MyApp
import com.yenvth.soilDetectionApp.models.ProvinceModel
import com.yenvth.soilDetectionApp.models.SoilModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    private val resourceDb = MyApp.getResourceDatabase()

    private val _provinceSoil = MutableLiveData<List<SoilModel>>()
    val provinceSoil: LiveData<List<SoilModel>> get() = _provinceSoil

    private val _provinces = MutableLiveData<List<ProvinceModel>>()
    val provinces: LiveData<List<ProvinceModel>> get() = _provinces

    fun getListSoilByProvince(provinceId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val soils = resourceDb.soilDao().getSoils().associateBy { it.soilId }
            val data = resourceDb.provinceSoilDao().getProvinceSoilRecords(provinceId).map {
                soils[it.soilId]
            }
            _provinceSoil.postValue(data as List<SoilModel>?)
        }
    }

    fun getProvinces() {
        viewModelScope.launch(Dispatchers.IO) {
            val provinces = resourceDb.provinceDao().getProvinces()
            _provinces.postValue(provinces)
        }
    }
}