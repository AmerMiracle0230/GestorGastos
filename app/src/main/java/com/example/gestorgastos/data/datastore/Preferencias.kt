package com.example.gestorgastos.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey  // ← ESTE SÍ EXISTE
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.preferenciasDataStore by preferencesDataStore("ark_prefs")

@Singleton
class Preferencias @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        val MONEDA = stringPreferencesKey("moneda")
        val TEMA = stringPreferencesKey("tema")
        val CATEGORIAS_DESTACADAS = stringSetPreferencesKey("categorias_destacadas")  // ← STRING SET
    }

    val moneda: Flow<String> = context.preferenciasDataStore.data
        .map { preferences ->
            preferences[MONEDA] ?: "€"
        }

    val tema: Flow<String> = context.preferenciasDataStore.data
        .map { preferences ->
            preferences[TEMA] ?: "sistema"
        }

    // Guardamos IDs como String, luego convertimos a Int
    val categoriasDestacadas: Flow<Set<Int>> = context.preferenciasDataStore.data
        .map { preferences ->
            preferences[CATEGORIAS_DESTACADAS]?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet()
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

    suspend fun guardarCategoriasDestacadas(ids: Set<Int>) {
        context.preferenciasDataStore.edit { preferences ->
            preferences[CATEGORIAS_DESTACADAS] = ids.map { it.toString() }.toSet()
        }
    }
}