package com.example.gestorgastos.screens.stats

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestorgastos.data.entity.Gasto
import com.example.gestorgastos.data.repository.GastoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class GastoPorCategoria(
    val categoria: String,
    val total: Float,
    val color: Int
)

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repository: GastoRepository
) : ViewModel() {

    private val _gastosPorCategoria = MutableStateFlow<List<GastoPorCategoria>>(emptyList())
    val gastosPorCategoria: StateFlow<List<GastoPorCategoria>> = _gastosPorCategoria

    private val _total = MutableStateFlow(0f)
    val total: StateFlow<Float> = _total

    // Colores para cada categoría
    private val colores = mapOf(
        "Comida" to Color(0xFF4CAF50).toArgb(),      // Verde
        "Transporte" to Color(0xFF2196F3).toArgb(),  // Azul
        "Ocio" to Color(0xFFFF9800).toArgb(),        // Naranja
        "Salud" to Color(0xFFF44336).toArgb(),       // Rojo
        "Otros" to Color(0xFF9C27B0).toArgb()        // Morado
    )

    init {
        viewModelScope.launch {
            repository.obtenerTodosLosGastos().collect { gastos ->
                calcularEstadisticas(gastos)
            }
        }
    }

    private fun calcularEstadisticas(gastos: List<Gasto>) {
        // Agrupar por categoría y sumar
        val gastosPorCat = gastos
            .groupBy { it.categoria }
            .map { (categoria, lista) ->
                val sumaTotal = lista.sumOf { it.cantidad }.toFloat()
                GastoPorCategoria(
                    categoria = categoria,
                    total = sumaTotal,
                    color = colores[categoria] ?: Color.Gray.toArgb()
                )
            }
            .sortedByDescending { it.total }

        _gastosPorCategoria.value = gastosPorCat
        _total.value = gastosPorCat.sumOf { it.total.toDouble() }.toFloat()
    }
}

// Extensión para convertir Color de Compose a ARGB de Android (Int)
fun Color.toArgb(): Int {
    return (this.value shr 32).toInt()
}
