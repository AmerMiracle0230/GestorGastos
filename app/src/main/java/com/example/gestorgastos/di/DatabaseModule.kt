package com.example.gestorgastos.di

import android.content.Context
import androidx.room.Room
import com.example.gestorgastos.data.dao.CategoriaDao
import com.example.gestorgastos.data.dao.GastoDao
import com.example.gestorgastos.data.dao.IngresoDao
import com.example.gestorgastos.data.database.AppDatabase
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
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "ark_database"
        )
        .fallbackToDestructiveMigration() // 🆕 ESTO EVITA CRASHES AL CAMBIAR ENTIDADES
        .build()
    }

    @Provides
    @Singleton
    fun provideGastoDao(database: AppDatabase): GastoDao = database.gastoDao()

    @Provides
    @Singleton
    fun provideIngresoDao(database: AppDatabase): IngresoDao = database.ingresoDao()

    @Provides
    @Singleton
    fun provideCategoriaDao(database: AppDatabase): CategoriaDao = database.categoriaDao()
}
