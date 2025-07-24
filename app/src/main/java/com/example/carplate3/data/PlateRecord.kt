package com.example.carplate3.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plate_records")
data class PlateRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val plateNumber: String,
    val timestamp: Long = System.currentTimeMillis(),
    val imagePath: String? = null
)