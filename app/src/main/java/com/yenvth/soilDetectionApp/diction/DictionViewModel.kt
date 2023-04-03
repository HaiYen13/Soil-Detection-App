package com.yenvth.soilDetectionApp.diction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yenvth.soilDetectionApp.MyApp
import com.yenvth.soilDetectionApp.models.SoilModel
import com.yenvth.soilDetectionApp.room.entity.HistoryRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DictionViewModel : ViewModel() {
    private val db = MyApp.getDatabase()

    private val _saveStatus = MutableLiveData<Int>()
    val saveStatus: LiveData<Int> get() = _saveStatus

    private val _soils = MutableLiveData<List<SoilModel>>()
    val soils: LiveData<List<SoilModel>> get() = _soils

    fun saveSearchHistory(historyRecord: HistoryRecord) {
        viewModelScope.launch(Dispatchers.IO) {
            db.historyDao().insertHistory(historyRecord)
            _saveStatus.postValue(1)
        }
    }

    fun getListSoil(queryString: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _soils.postValue(db.soilDao().getSoils())
        }
    }
}