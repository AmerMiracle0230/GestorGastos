// archivo: ListaViewModel.kt
// que hace: maneja los datos para la pantalla de lista de movimientos
// proporciona: gastos, ingresos, categorias, moneda
// funciones: eliminar gasto, eliminar ingreso

package com.example.gestorgastos.screens.lista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestorgastos.data.datastore.Preferencias
import com.example.gestorgastos.data.entity.Categoria
import com.example.gestorgastos.data.entity.Gasto
import com.example.gestorgastos.data.entity.Ingreso
import com.example.gestorgastos.data.repository.CategoriaRepository
import com.example.gestorgastos.data.repository.GastoRepository
import com.example.gestorgastos.data.repository.IngresoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaViewModel @Inject constructor(
    private val gastoRepository: GastoRepository,
    private val ingresoRepository: IngresoRepository,
    private val categoriaRepository: CategoriaRepository,
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

    // eliminar un gasto
    fun eliminarGasto(gasto: Gasto) {
        viewModelScope.launch {
            gastoRepository.eliminar(gasto)
        }
    }

    // eliminar un ingreso
    fun eliminarIngreso(ingreso: Ingreso) {
        viewModelScope.launch {
            ingresoRepository.eliminar(ingreso)
        }
    }
}