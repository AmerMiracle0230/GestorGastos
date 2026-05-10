// archivo: HomeViewModel.kt
// que hace: maneja los datos para la pantalla principal (Home)
// proporciona: saldo, ingresos, gastos, categorias destacadas, meta de ahorro

package com.example.gestorgastos.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestorgastos.data.datastore.Preferencias
import com.example.gestorgastos.data.entity.Categoria
import com.example.gestorgastos.data.repository.CategoriaRepository
import com.example.gestorgastos.data.repository.GastoRepository
import com.example.gestorgastos.data.repository.IngresoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

data class CategoriaConEstado(
    val categoria: Categoria,
    val gastado: Double,
    val objetivo: Double,
    val porcentaje: Float,
    val excedido: Boolean
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    @Suppress("unused") private val gastoRepository: GastoRepository,
    @Suppress("unused") private val ingresoRepository: IngresoRepository,
    @Suppress("unused") private val categoriaRepository: CategoriaRepository,
    private val preferencias: Preferencias
) : ViewModel() {

    // moneda actual desde DataStore
    val monedaActual: StateFlow<String> = preferencias.moneda
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "€"
        )

    // meta de ahorro desde DataStore
    val metaAhorro: StateFlow<Double> = preferencias.metaAhorro
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = 0.0
        )

    fun guardarMetaAhorro(nuevaMeta: Double) {
        viewModelScope.launch {
            preferencias.guardarMetaAhorro(nuevaMeta)
        }
    }

    // gastos del mes actual
    val gastos = gastoRepository.obtenerEntreFechas(inicioMes(), finMes())
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    // ingresos del mes actual
    val ingresos = ingresoRepository.obtenerEntreFechas(inicioMes(), finMes())
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    // saldo = ingresos - gastos
    val saldo: StateFlow<Double> = combine(
        gastos,
        ingresos
    ) { gastosList, ingresosList ->
        val totalGastos = gastosList.sumOf { it.cantidad }
        val totalIngresos = ingresosList.sumOf { it.cantidad }
        totalIngresos - totalGastos
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = 0.0
    )

    // total de ingresos del mes
    val ingresosTotales: StateFlow<Double> = ingresos
        .map { it.sumOf { ingreso -> ingreso.cantidad } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = 0.0
        )

    // gastos agrupados por categoria
    val gastosPorCategoria: StateFlow<Map<Int, Double>> = gastos
        .map { lista ->
            lista.groupBy { it.categoriaId }
                .mapValues { (_, gastosCat) -> gastosCat.sumOf { it.cantidad } }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyMap()
        )

    // categorias destacadas para mostrar en home
    val categoriasHome: StateFlow<List<CategoriaConEstado>> = combine(
        categoriaRepository.obtenerTodas(),
        gastosPorCategoria,
        preferencias.categoriasDestacadas
    ) { categorias, gastosMap, destacadasIds ->

        val categoriasDestacadas = categorias.filter {
            it.id in destacadasIds && (it.tipo == "gasto" || it.tipo == "ambos")
        }

        categoriasDestacadas.map { categoria ->
            val gastado = gastosMap[categoria.id] ?: 0.0
            val objetivo = if (categoria.objetivo > 0) categoria.objetivo else 200.0
            val porcentaje = (gastado / objetivo * 100).toFloat().coerceIn(0f, 100f)
            CategoriaConEstado(
                categoria = categoria,
                gastado = gastado,
                objetivo = objetivo,
                porcentaje = porcentaje,
                excedido = gastado > objetivo
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    // calcula el inicio del mes actual (timestamp)
    private fun inicioMes(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    // calcula el fin del mes actual (timestamp)
    private fun finMes(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }
}