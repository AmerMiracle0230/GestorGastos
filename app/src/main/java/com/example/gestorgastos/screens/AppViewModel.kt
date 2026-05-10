// archivo: AppViewModel.kt
// que hace: estado global de la aplicacion
// gestiona: tema, moneda, meta de ahorro
// los datos persisten en DataStore

package com.example.gestorgastos.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestorgastos.data.datastore.Preferencias
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val preferencias: Preferencias
) : ViewModel() {

    // tema: "sistema", "claro", "oscuro"
    val temaActual: StateFlow<String> = preferencias.tema
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = "sistema"
        )

    fun guardarTema(tema: String) {
        viewModelScope.launch {
            preferencias.guardarTema(tema)
        }
    }

    // moneda: "€", "$", "£"
    val monedaActual: StateFlow<String> = preferencias.moneda
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = "€"
        )

    fun guardarMoneda(moneda: String) {
        viewModelScope.launch {
            preferencias.guardarMoneda(moneda)
        }
    }

    // meta de ahorro: valor en euros
    @Suppress("unused")
    val metaAhorro: StateFlow<Double> = preferencias.metaAhorro
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = 0.0
        )

    @Suppress("unused")
    fun guardarMetaAhorro(meta: Double) {
        viewModelScope.launch {
            preferencias.guardarMetaAhorro(meta)
        }
    }
}