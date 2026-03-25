package com.example.gestorgastos.screens.stats

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gestorgastos.components.TituloSecundario
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun StatsScreen(
    viewModel: StatsViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val gastos by viewModel.gastos.collectAsState()
    val ingresos by viewModel.ingresos.collectAsState()
    val categorias by viewModel.categorias.collectAsState()

    // Calcular totales
    val totalGastos = gastos.sumOf { it.cantidad }
    val totalIngresos = ingresos.sumOf { it.cantidad }
    val saldo = totalIngresos - totalGastos

    // Agrupar gastos por categoría
    val gastosPorCategoria = gastos.groupBy { it.categoriaId }
        .mapValues { (_, lista) -> lista.sumOf { it.cantidad } }
        .toList()
        .sortedByDescending { it.second }

    // Agrupar ingresos por categoría
    val ingresosPorCategoria = ingresos.groupBy { it.categoriaId }
        .mapValues { (_, lista) -> lista.sumOf { it.cantidad } }
        .toList()
        .sortedByDescending { it.second }

    // Mapa de categorías
    val mapaCategorias = categorias.associateBy { it.id }

    // Formateador de fecha para el mes actual
    val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    val mesActual = dateFormat.format(Date())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TituloSecundario(
            texto = "ESTADÍSTICAS",
            onBackClick = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Mes actual
            Text(
                text = mesActual.uppercase(),
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Resumen de saldo
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (saldo >= 0)
                        Color(0xFF4CAF50).copy(alpha = 0.1f)
                    else
                        Color(0xFFF44336).copy(alpha = 0.1f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "SALDO TOTAL",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = String.format("%.2f€", saldo),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (saldo >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // GASTOS POR CATEGORÍA
            Text(
                text = "💸 GASTOS POR CATEGORÍA",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (gastosPorCategoria.isNotEmpty()) {
                gastosPorCategoria.forEach { (categoriaId, total) ->
                    val categoria = mapaCategorias[categoriaId]
                    if (categoria != null) {
                        val porcentaje = if (totalGastos > 0) (total / totalGastos * 100) else 0.0
                        CategoriaStatItem(
                            icono = categoria.icono,
                            nombre = categoria.nombre,
                            total = total,
                            porcentaje = porcentaje,
                            color = Color(categoria.color),
                            esIngreso = false
                        )
                    }
                }
            } else {
                Text(
                    text = "No hay gastos registrados",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // INGRESOS POR CATEGORÍA
            Text(
                text = "💰 INGRESOS POR CATEGORÍA",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
            )

            if (ingresosPorCategoria.isNotEmpty()) {
                ingresosPorCategoria.forEach { (categoriaId, total) ->
                    val categoria = mapaCategorias[categoriaId]
                    if (categoria != null) {
                        val porcentaje = if (totalIngresos > 0) (total / totalIngresos * 100) else 0.0
                        CategoriaStatItem(
                            icono = categoria.icono,
                            nombre = categoria.nombre,
                            total = total,
                            porcentaje = porcentaje,
                            color = Color(categoria.color),
                            esIngreso = true
                        )
                    }
                }
            } else {
                Text(
                    text = "No hay ingresos registrados",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Resumen rápido
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "📊 RESUMEN RÁPIDO",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total gastos:")
                        Text(String.format("%.2f€", totalGastos), color = Color(0xFFF44336))
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total ingresos:")
                        Text(String.format("%.2f€", totalIngresos), color = Color(0xFF4CAF50))
                    }
                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Promedio gasto diario:")
                        val diasDelMes = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH)
                        val promedioDiario = totalGastos / diasDelMes
                        Text(String.format("%.2f€", promedioDiario))
                    }
                }
            }
        }
    }
}

@Composable
fun CategoriaStatItem(
    icono: String,
    nombre: String,
    total: Double,
    porcentaje: Double,
    color: Color,
    esIngreso: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = icono, fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = nombre,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Text(
                text = String.format("%.2f€", total),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (esIngreso) Color(0xFF4CAF50) else Color(0xFFF44336)
            )
        }

        // Barra de progreso
        LinearProgressIndicator(
            progress = (porcentaje / 100).toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )

        // Porcentaje
        Text(
            text = String.format("%.1f%%", porcentaje),
            fontSize = 11.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}