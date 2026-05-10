// archivo: StatsScreen.kt
// que hace: pantalla de estadisticas con graficos y resumen
// muestra: ingresos totales, gastos totales, gráfico circular, leyenda de categorias

package com.example.gestorgastos.screens.stats

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gestorgastos.components.FondoPantalla
import com.example.gestorgastos.components.TituloGradienteConFlecha
import com.example.gestorgastos.screens.stats.components.*
import com.example.gestorgastos.ui.theme.Success

@Composable
fun StatsScreen(
    viewModel: StatsViewModel = hiltViewModel(),
    onBack: () -> Unit,
    isDarkTheme: Boolean
) {
    // datos desde el viewmodel
    val gastos by viewModel.gastos.collectAsState()
    val ingresos by viewModel.ingresos.collectAsState()
    val categorias by viewModel.categorias.collectAsState()
    val moneda by viewModel.monedaActual.collectAsState()

    // calculos
    val totalGastos = gastos.sumOf { it.cantidad }
    val totalIngresos = ingresos.sumOf { it.cantidad }

    // gastos agrupados por categoria
    val gastosPorCategoria = gastos.groupBy { it.categoriaId }
        .mapValues { (_, lista) -> lista.sumOf { it.cantidad } }
        .toList()
        .sortedByDescending { it.second }

    val mapaCategorias = categorias.associateBy { it.id }

    // fondo según el tema
    val cardBackground = if (isDarkTheme) {
        Color.Black.copy(alpha = 0.4f)
    } else {
        Color.White.copy(alpha = 0.6f)
    }

    FondoPantalla(isDarkTheme = isDarkTheme) {
        Scaffold(containerColor = Color.Transparent) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = innerPadding.calculateTopPadding() - 25.dp,
                        bottom = innerPadding.calculateBottomPadding(),
                        start = 16.dp,
                        end = 16.dp
                    )
            ) {
                TituloGradienteConFlecha(
                    texto = "ESTADISTICAS",
                    onBackClick = onBack
                )

                Spacer(modifier = Modifier.height(16.dp))

                // tarjetas de resumen (ingresos y gastos)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ResumenStatsCard(
                        titulo = "INGRESOS",
                        icono = "💰",
                        total = totalIngresos,
                        color = Success,
                        isDarkTheme = isDarkTheme,
                        moneda = moneda,
                        modifier = Modifier.weight(1f)
                    )
                    ResumenStatsCard(
                        titulo = "GASTOS",
                        icono = "💸",
                        total = totalGastos,
                        color = MaterialTheme.colorScheme.error,
                        isDarkTheme = isDarkTheme,
                        moneda = moneda,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // grafico circular y leyenda
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .clip(RoundedCornerShape(20.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBackground),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        if (gastosPorCategoria.isNotEmpty()) {
                            // mostrar gráfico si hay gastos
                            PieChartComponent(
                                gastosPorCategoria = gastosPorCategoria,
                                mapaCategorias = mapaCategorias,
                                isDarkTheme = isDarkTheme
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            CategoryLegend(
                                gastosPorCategoria = gastosPorCategoria,
                                mapaCategorias = mapaCategorias,
                                totalGastos = totalGastos,
                                isDarkTheme = isDarkTheme
                            )
                        } else {
                            // mostrar mensaje si no hay gastos
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(250.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text("📭", fontSize = 48.sp)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "No hay gastos registrados",
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = "Agrega gastos para ver estadisticas",
                                        fontSize = 12.sp,
                                        color = Color.Gray.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}