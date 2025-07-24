package com.example.carplate3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.carplate3.data.PlateDatabase
import com.example.carplate3.data.PlateRecord
import com.example.carplate3.data.PlateRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class PlateViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PlateRepository
    val allRecords: StateFlow<List<PlateRecord>>

    // 儲存最近識別的車牌和時間
    private val lastRecognizedPlates = mutableMapOf<String, Long>()

    init {
        val dao = PlateDatabase.getDatabase(application).plateRecordDao()
        repository = PlateRepository(dao)
        allRecords = repository.allRecords.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )
    }

    fun insertRecord(plateNumber: String, imagePath: String? = null) {
        val currentTime = System.currentTimeMillis()
        val lastRecognizedTime = lastRecognizedPlates[plateNumber]

        if (lastRecognizedTime == null ||
            currentTime - lastRecognizedTime > TimeUnit.MINUTES.toMillis(3)) {

            viewModelScope.launch {
                repository.insert(PlateRecord(
                    plateNumber = plateNumber,
                    timestamp = currentTime,
                    imagePath = imagePath
                ))
                lastRecognizedPlates[plateNumber] = currentTime
            }
        }
    }

    private fun cleanOldRecords() {
        val currentTime = System.currentTimeMillis()
        lastRecognizedPlates.entries.removeIf { (_, time) ->
            currentTime - time > TimeUnit.MINUTES.toMillis(3)
        }
    }

    fun deleteRecord(record: PlateRecord) {
        viewModelScope.launch {
            repository.delete(record)
        }
    }

    fun deleteAllRecords() {
        viewModelScope.launch {
            repository.deleteAll()
            lastRecognizedPlates.clear()
        }
    }
}