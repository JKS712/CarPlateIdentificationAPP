package com.example.carplate3.data

import kotlinx.coroutines.flow.Flow

class PlateRepository(private val plateRecordDao: PlateRecordDao) {
    val allRecords: Flow<List<PlateRecord>> = plateRecordDao.getAllRecords()

    suspend fun insert(record: PlateRecord) {
        plateRecordDao.insert(record)
    }

    suspend fun delete(record: PlateRecord) {
        plateRecordDao.delete(record)
    }

    suspend fun deleteAll() {
        plateRecordDao.deleteAll()
    }
}