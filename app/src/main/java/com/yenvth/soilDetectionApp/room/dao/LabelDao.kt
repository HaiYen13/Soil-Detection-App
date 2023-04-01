package com.yenvth.soilDetectionApp.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yenvth.soilDetectionApp.models.LabelModel
import com.yenvth.soilDetectionApp.room.entity.LabelRecord

@Dao
interface LabelDao {
    fun getLabels() = getLabelRecords().map { it.toModel() }

    @Query("SELECT * FROM labels")
    fun getLabelRecords(): List<LabelRecord>

    @Insert
    fun insertLabel(record: LabelRecord)

    @Query("DELETE FROM labels WHERE label_id = :labelId")
    fun deleteLabel(labelId: Int)

    @Query("DELETE FROM labels")
    fun deleteAllLabel()
}

internal fun LabelRecord.toModel() = LabelModel(
    labelId = this.labelId,
    labelName = this.labelName,
    url = this.url,
    timestamp = this.timestamp,
)