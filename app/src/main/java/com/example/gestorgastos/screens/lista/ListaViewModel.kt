package com.example.gestorgastos.screens.lista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val categoriaRepository: CategoriaRepository
) : ViewModel() {

    val gastos: StateFlow<List<Gasto>> = gastoRepository.obtenerTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    val ingresos: StateFlow<List<Ingreso>> = ingresoRepository.obtenerTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    val categorias: StateFlow<List<Categoria>> = categoriaRepository.obtenerTodas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    fun eliminarGasto(gasto: Gasto) {
        viewModelScope.launch {
            gastoRepository.eliminar(gasto)
        }
    }

    fun eliminarIngreso(ingreso: Ingreso) {
        viewModelScope.launch {
            ingresoRepository.eliminar(ingreso)
        }
    }
}