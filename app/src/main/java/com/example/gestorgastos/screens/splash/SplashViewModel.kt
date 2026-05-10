// archivo: SplashViewModel.kt
// que hace: maneja la logica de la pantalla de carga
// espera 2 segundos y luego cambia isLoading false para navegar a home

package com.example.gestorgastos.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    // estado de carga (true mientras se muestra el splash)
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            delay(2000) // esperar 2 segundos
            _isLoading.value = false // terminar el splash
        }
    }
}