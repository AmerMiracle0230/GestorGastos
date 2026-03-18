package com.example.gestorgastos.screens.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gestorgastos.components.TituloSecundario
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

@Composable
fun StatsScreen(
    viewModel: StatsViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val gastosPorCategoria by viewModel.gastosPorCategoria.collectAsState()
    val total by viewModel.total.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
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
            // Total general
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "GASTO TOTAL:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = String.format("%.2f€", total),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Gráfico de pastel (PieChart)
            if (gastosPorCategoria.isNotEmpty()) {
                AndroidView(
                    factory = { context ->
                        PieChart(context).apply {
                            description.isEnabled = false
                            isDrawHoleEnabled = true
                            holeRadius = 40f
                            transparentCircleRadius = 45f
                            legend.isEnabled = false
                            setUsePercentValues(true)
                            setEntryLabelColor(android.graphics.Color.BLACK)
                            setEntryLabelTextSize(12f)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    update = { chart ->
                        val entries = gastosPorCategoria.map { PieEntry(it.total, it.categoria) }
                        val dataSet = PieDataSet(entries, "").apply {
                            colors = gastosPorCategoria.map { it.color }
                            sliceSpace = 3f
                            valueTextSize = 14f
                            valueTextColor = android.graphics.Color.BLACK
                            valueFormatter = PercentFormatter(chart)
                        }

                        chart.data = PieData(dataSet)
                        chart.invalidate()
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Detalle por categoría
            Text(
                text = "DETALLE POR CATEGORÍA:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            gastosPorCategoria.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(Color(item.color))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(item.categoria)
                    }
                    val porcentaje = if (total > 0) (item.total / total * 100) else 0f
                    Text(String.format("%.2f€ (%.0f%%)", item.total, porcentaje))
                }
            }
        }
    }
}
