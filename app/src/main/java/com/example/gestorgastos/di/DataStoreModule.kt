// archivo: DataStoreModule.kt
// que hace: provee la instancia de Preferencias para Hilt

package com.example.gestorgastos.di

import android.content.Context
import com.example.gestorgastos.data.datastore.Preferencias
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providePreferencias(
        @ApplicationContext context: Context
    ): Preferencias {
        return Preferencias(context)
    }
}