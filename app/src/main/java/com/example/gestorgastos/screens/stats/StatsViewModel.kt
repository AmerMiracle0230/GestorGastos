package com.example.gestorgastos.screens.stats


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestorgastos.data.entity.Gasto
import com.example.gestorgastos.data.repository.GastoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import com.example.gestorgastos.data.entity.Categoria
import com.example.gestorgastos.data.entity.Ingreso
import com.example.gestorgastos.data.repository.CategoriaRepository
import com.example.gestorgastos.data.repository.IngresoRepository
import kotlinx.coroutines.flow.*


@HiltViewModel
class StatsViewModel @Inject constructor(
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
}