package com.example.gestorgastos.screens.pdf

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestorgastos.data.entity.Gasto
import com.example.gestorgastos.data.repository.GastoRepository
import com.example.gestorgastos.utils.PdfGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PdfViewModel @Inject constructor(
    private val repository: GastoRepository
) : ViewModel() {

    private val _gastos = MutableStateFlow<List<Gasto>>(emptyList())
    val gastos: StateFlow<List<Gasto>> = _gastos

    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> = _total

    init {
        viewModelScope.launch {
            repository.obtenerTodosLosGastos().collect { lista ->
                _gastos.value = lista
                _total.value = lista.sumOf { it.cantidad }
            }
        }
    }

    fun generarPDF(context: Context): File? {
        return PdfGenerator.generarInforme(
            context = context,
            gastos = _gastos.value,
            nombreArchivo = "gastos_${System.currentTimeMillis()}.pdf"
        )
    }
}