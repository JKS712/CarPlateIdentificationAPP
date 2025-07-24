package com.example.carplate3.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlateRecord::class], version = 1)
abstract class PlateDatabase : RoomDatabase() {
    abstract fun plateRecordDao(): PlateRecordDao

    companion object {
        @Volatile
        private var INSTANCE: PlateDatabase? = null

        fun getDatabase(context: Context): PlateDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlateDatabase::class.java,
                    "plate_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}