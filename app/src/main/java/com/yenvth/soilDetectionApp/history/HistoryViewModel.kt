package com.yenvth.soilDetectionApp.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yenvth.soilDetectionApp.MyApp
import com.yenvth.soilDetectionApp.models.HistoryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {
    private val db = MyApp.getDatabase()

    private val _deleteStatus = MutableLiveData<Int>()
    val deleteStatus: LiveData<Int> get() = _deleteStatus

    private val _deleteAllStatus = MutableLiveData<Int>()
    val deleteAllStatus: LiveData<Int> get() = _deleteAllStatus

    private val _histories = MutableLiveData<List<HistoryModel>>()
    val histories: LiveData<List<HistoryModel>> get() = _histories

    fun getHistories() {
        viewModelScope.launch(Dispatchers.IO) {
            _histories.postValue(db.historyDao().getHistories())
        }
    }

    fun deleteHistory(historyId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            db.historyDao().deleteHistory(historyId)
            _deleteStatus.postValue(1)
        }
    }

    fun deleteAllHistories() {
        viewModelScope.launch(Dispatchers.IO) {
            db.historyDao().deleteAllHistory()
            _deleteAllStatus.postValue(1)
        }
    }
}