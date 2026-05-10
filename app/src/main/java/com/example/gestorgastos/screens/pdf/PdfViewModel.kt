// archivo: PdfViewModel.kt
// que hace: maneja los datos para la pantalla PDF
// proporciona: gastos, ingresos, categorias, moneda
// funcion: generarPDF que llama a PdfGenerator

package com.example.gestorgastos.screens.pdf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestorgastos.data.datastore.Preferencias
import com.example.gestorgastos.data.entity.Categoria
import com.example.gestorgastos.data.entity.Gasto
import com.example.gestorgastos.data.entity.Ingreso
import com.example.gestorgastos.data.repository.CategoriaRepository
import com.example.gestorgastos.data.repository.GastoRepository
import com.example.gestorgastos.data.repository.IngresoRepository
import com.example.gestorgastos.utils.PdfGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PdfViewModel @Inject constructor(
    @Suppress("unused") private val gastoRepository: GastoRepository,
    @Suppress("unused") private val ingresoRepository: IngresoRepository,
    @Suppress("unused") private val categoriaRepository: CategoriaRepository,
    preferencias: Preferencias
) : ViewModel() {

    // moneda actual desde DataStore
    val monedaActual: StateFlow<String> = preferencias.moneda
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "€"
        )

    // lista de gastos
    val gastos: StateFlow<List<Gasto>> = gastoRepository.obtenerTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    // lista de ingresos
    val ingresos: StateFlow<List<Ingreso>> = ingresoRepository.obtenerTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    // lista de categorias
    val categorias: StateFlow<List<Categoria>> = categoriaRepository.obtenerTodas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    // genera el PDF con los datos actuales
    fun generarPDF(
        gastos: List<Gasto>,
        ingresos: List<Ingreso>,
        mapaCategorias: Map<Int, Categoria>,
        moneda: String
    ): File? {
        return PdfGenerator.generarInforme(gastos, ingresos, mapaCategorias, moneda)
    }
}