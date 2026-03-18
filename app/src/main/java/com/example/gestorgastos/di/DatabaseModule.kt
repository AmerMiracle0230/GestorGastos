package com.example.gestorgastos.di


import android.content.Context
import androidx.room.Room
import com.example.gestorgastos.data.dao.GastoDao
import com.example.gestorgastos.data.database.GastoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideGastoDatabase(
        @ApplicationContext context: Context
    ): GastoDatabase {
        return Room.databaseBuilder(
            context,
            GastoDatabase::class.java,
            "gastos_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGastoDao(
        database: GastoDatabase
    ): GastoDao {
        return database.gastoDao()
    }
}