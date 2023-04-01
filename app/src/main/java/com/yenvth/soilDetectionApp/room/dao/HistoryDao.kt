package com.yenvth.soilDetectionApp.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yenvth.soilDetectionApp.models.HistoryModel
import com.yenvth.soilDetectionApp.room.entity.HistoryRecord

@Dao
interface HistoryDao {
    fun getHistories() = getHistoryRecords().map { it.toModel() }

    @Query("SELECT * FROM histories ORDER BY timestamp DESC")
    fun getHistoryRecords(): List<HistoryRecord>

    @Insert
    fun insertHistory(record: HistoryRecord)

    @Query("DELETE FROM histories WHERE history_id = :historyId")
    fun deleteHistory(historyId: Int)

    @Query("DELETE FROM histories")
    fun deleteAllHistory()
}

internal fun HistoryRecord.toModel() = HistoryModel(
    historyId = this.historyId,
    soilId = this.soilId,
    timestamp = this.timestamp,
)