package com.example.gestorgastos.screens.lista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestorgastos.data.entity.Gasto
import com.example.gestorgastos.data.repository.GastoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaViewModel @Inject constructor(
    private val repository: GastoRepository
) : ViewModel() {

    private val _gastos = MutableStateFlow<List<Gasto>>(emptyList())
    val gastos: StateFlow<List<Gasto>> = _gastos

    init {
        viewModelScope.launch {
            repository.obtenerTodosLosGastos().collect { lista ->
                _gastos.value = lista
            }
        }
    }

    fun eliminarGasto(gasto: Gasto) {
        viewModelScope.launch {
            repository.eliminarGasto(gasto)
        }
    }

    fun calcularTotal(): Double = _gastos.value.sumOf { it.cantidad }
}