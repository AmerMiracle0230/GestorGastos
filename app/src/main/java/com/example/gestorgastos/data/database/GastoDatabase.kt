package com.example.gestorgastos.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gestorgastos.data.dao.GastoDao
import com.example.gestorgastos.data.entity.Gasto

@Database(entities = [Gasto::class], version = 1)
abstract class GastoDatabase : RoomDatabase() {

    abstract fun gastoDao(): GastoDao

    companion object {
        @Volatile
        private var INSTANCE: GastoDatabase? = null

        fun getInstance(context: Context): GastoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GastoDatabase::class.java,
                    "gastos_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}