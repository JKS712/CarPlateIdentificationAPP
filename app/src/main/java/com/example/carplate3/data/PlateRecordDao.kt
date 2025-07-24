package com.example.carplate3.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlateRecordDao {
    @Query("SELECT * FROM plate_records ORDER BY timestamp DESC")
    fun getAllRecords(): Flow<List<PlateRecord>>

    @Insert
    suspend fun insert(record: PlateRecord)

    @Delete
    suspend fun delete(record: PlateRecord)

    @Query("DELETE FROM plate_records")
    suspend fun deleteAll()
}