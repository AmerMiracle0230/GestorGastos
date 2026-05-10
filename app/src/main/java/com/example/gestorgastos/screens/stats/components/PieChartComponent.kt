// archivo: PieChartComponent.kt
// que hace: grafico circular de gastos por categoria
// usado en: StatsScreen

package com.example.gestorgastos.screens.stats.components

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.example.gestorgastos.data.entity.Categoria

@Composable
fun PieChartComponent(
    gastosPorCategoria: List<Pair<Int, Double>>,
    mapaCategorias: Map<Int, Categoria>,
    isDarkTheme: Boolean
) {
    val textColor = if (isDarkTheme) android.graphics.Color.WHITE else android.graphics.Color.DKGRAY

    AndroidView(
        factory = { context ->
            PieChart(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    350.dpToPx(context)
                )

                description.isEnabled = false
                isDrawHoleEnabled = true
                holeRadius = 45f
                transparentCircleRadius = 0f
                setHoleColor(android.graphics.Color.TRANSPARENT)
                setDrawCenterText(true)
                centerText = "📊\nGASTOS"
                setCenterTextSize(16f)
                setCenterTextColor(textColor)

                legend.isEnabled = false
                setUsePercentValues(true)
                setEntryLabelColor(textColor)
                setEntryLabelTextSize(25f)
                setExtraOffsets(20f, 10f, 20f, 10f)
                animateY(800)
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = { chart ->
            val entries = gastosPorCategoria.map { (catId, total) ->
                val categoria = mapaCategorias[catId]
                PieEntry(total.toFloat(), categoria?.id ?: 0)
            }

            val dataSet = PieDataSet(entries, "").apply {
                colors = gastosPorCategoria.map { (catId, _) ->
                    mapaCategorias[catId]?.color ?: android.graphics.Color.GRAY
                }

                valueTextColor = textColor
                valueTextSize = 13f
                sliceSpace = 2f

                xValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                valueLinePart1Length = 0.5f
                valueLinePart2Length = 0.4f
                valueLineColor = textColor
                valueLineWidth = 1.2f

                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return "${value.toInt()}%"
                    }
                }
            }

            val data = PieData(dataSet).apply {
                setValueFormatter(object : ValueFormatter() {
                    override fun getPieLabel(value: Float, pieEntry: PieEntry?): String {
                        val categoriaId = pieEntry?.data as? Int
                        val categoria = mapaCategorias[categoriaId ?: -1]
                        val emoji = categoria?.icono ?: "❓"
                        val porcentaje = value.toInt()
                        return "$emoji $porcentaje%"
                    }
                })
                setValueTextColor(textColor)
                setValueTextSize(13f)
            }

            chart.data = data
            chart.invalidate()
        }
    )
}

fun Int.dpToPx(context: android.content.Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}