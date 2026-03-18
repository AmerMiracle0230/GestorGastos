package com.example.gestorgastos.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.preferenciasDataStore by preferencesDataStore("gestor_prefs")

@Singleton
class Preferencias @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        val MONEDA = stringPreferencesKey("moneda")
        val TEMA = stringPreferencesKey("tema")
    }

    val moneda: Flow<String> = context.preferenciasDataStore.data
        .map { preferences ->
            preferences[MONEDA] ?: "€"
        }

    val tema: Flow<String> = context.preferenciasDataStore.data
        .map { preferences ->
            preferences[TEMA] ?: "sistema"
        }

    suspend fun guardarMoneda(moneda: String) {
        context.preferenciasDataStore.edit { preferences ->
            preferences[MONEDA] = moneda
        }
    }

    suspend fun guardarTema(tema: String) {
        context.preferenciasDataStore.edit { preferences ->
            preferences[TEMA] = tema
        }
    }
}