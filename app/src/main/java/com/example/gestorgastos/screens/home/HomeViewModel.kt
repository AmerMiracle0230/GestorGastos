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
    private val gastoRepository: GastoRepository,
    private val ingresoRepository: IngresoRepository,
    private val categoriaRepository: CategoriaRepository,
    private val preferencias: Preferencias
) : ViewModel() {

    // Obtener gastos e ingresos del mes actual
    val gastos = gastoRepository.obtenerEntreFechas(inicioMes(), finMes())
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    val ingresos = ingresoRepository.obtenerEntreFechas(inicioMes(), finMes())
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    // Calcular saldo
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

    // Calcular gastos por categoría
    val gastosPorCategoria: StateFlow<Map<Int, Double>> = gastos
        .map { lista ->
            lista.groupBy { it.categoriaId }
                .mapValues { (_, gastosCat) -> gastosCat.sumOf { it.cantidad } }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyMap()
        )

    // Calcular total de gastos
    val totalGastos: StateFlow<Double> = gastos
        .map { it.sumOf { g -> g.cantidad } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = 0.0
        )

    // CATEGORÍAS CON ESTADO - MUESTRA LAS 4 CON MÁS GASTO
    val categoriasHome: StateFlow<List<CategoriaConEstado>> = combine(
        categoriaRepository.obtenerTodas(),
        gastosPorCategoria,
        totalGastos,
        preferencias.categoriasDestacadas
    ) { categorias, gastosMap, totalGastosVal, destacadasIds ->

        // Calcular estado de TODAS las categorías de gasto
        val todasConEstado = categorias
            .filter { it.tipo == "gasto" || it.tipo == "ambos" }
            .map { categoria ->
                val gastado = gastosMap[categoria.id] ?: 0.0
                val objetivo = if (categoria.objetivo > 0) categoria.objetivo else 200.0
                val porcentaje = if (objetivo > 0) (gastado / objetivo * 100).toFloat().coerceIn(0f, 100f) else 0f
                val excedido = gastado > objetivo
                CategoriaConEstado(
                    categoria = categoria,
                    gastado = gastado,
                    objetivo = objetivo,
                    porcentaje = porcentaje,
                    excedido = excedido
                )
            }

        // Separar las que el usuario marcó y las que no
        val marcadas = todasConEstado.filter { it.categoria.id in destacadasIds }
        val noMarcadas = todasConEstado.filter { it.categoria.id !in destacadasIds }

        // Ordenar las no marcadas por gasto (mayor a menor)
        val noMarcadasOrdenadas = noMarcadas.sortedByDescending { it.gastado }

        // Calcular cuántas faltan para llegar a 4
        val cuantasMarcadas = marcadas.size
        val cuantasFaltan = (4 - cuantasMarcadas).coerceAtLeast(0)

        // Resultado: marcadas + las que más gasto tienen de las no marcadas
        val resultado = marcadas + noMarcadasOrdenadas.take(cuantasFaltan)

        resultado.take(4)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    // Fechas del mes actual
    private fun inicioMes(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

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