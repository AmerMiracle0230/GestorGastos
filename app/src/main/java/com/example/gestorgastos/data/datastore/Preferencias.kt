// archivo: Preferencias.kt
// que hace: almacenamiento de preferencias del usuario usando DataStore
// guarda: moneda, tema, categorias destacadas, meta de ahorro

package com.example.gestorgastos.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.preferenciasDataStore by preferencesDataStore("ark_prefs")

@Singleton
class Preferencias @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    companion object {
        val MONEDA = stringPreferencesKey("moneda")
        val TEMA = stringPreferencesKey("tema")
        val CATEGORIAS_DESTACADAS = stringSetPreferencesKey("categorias_destacadas")
        val META_AHORRO = stringPreferencesKey("meta_ahorro")
    }

    // moneda
    val moneda: Flow<String> = context.preferenciasDataStore.data
        .map { preferences ->
            preferences[MONEDA] ?: "€"
        }

    suspend fun guardarMoneda(moneda: String) {
        context.preferenciasDataStore.edit { preferences ->
            preferences[MONEDA] = moneda
        }
    }

    // tema
    val tema: Flow<String> = context.preferenciasDataStore.data
        .map { preferences ->
            preferences[TEMA] ?: "sistema"
        }

    suspend fun guardarTema(tema: String) {
        context.preferenciasDataStore.edit { preferences ->
            preferences[TEMA] = tema
        }
    }

    // categorias destacadas
    val categoriasDestacadas: Flow<Set<Int>> = context.preferenciasDataStore.data
        .map { preferences ->
            val stringSet = preferences[CATEGORIAS_DESTACADAS] ?: return@map emptySet()
            stringSet.mapNotNull { it.toIntOrNull() }.toSet()
        }

    suspend fun guardarCategoriasDestacadas(ids: Set<Int>) {
        context.preferenciasDataStore.edit { preferences ->
            preferences[CATEGORIAS_DESTACADAS] = ids.map { it.toString() }.toSet()
        }
    }

    // meta de ahorro
    val metaAhorro: Flow<Double> = context.preferenciasDataStore.data
        .map { preferences ->
            preferences[META_AHORRO]?.toDoubleOrNull() ?: 0.0
        }

    suspend fun guardarMetaAhorro(meta: Double) {
        context.preferenciasDataStore.edit { preferences ->
            preferences[META_AHORRO] = meta.toString()
        }
    }
}