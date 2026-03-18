package com.example.gestorgastos.screens.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestorgastos.data.datastore.Preferencias
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val preferencias: Preferencias
) : ViewModel() {

    // Moneda
    val monedaActual: StateFlow<String> = preferencias.moneda
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "€"
        )

    // Tema
    val temaActual: StateFlow<String> = preferencias.tema
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "sistema"
        )

    fun guardarMoneda(moneda: String) {
        viewModelScope.launch {
            preferencias.guardarMoneda(moneda)
        }
    }

    fun guardarTema(tema: String) {
        viewModelScope.launch {
            preferencias.guardarTema(tema)
        }
    }
}