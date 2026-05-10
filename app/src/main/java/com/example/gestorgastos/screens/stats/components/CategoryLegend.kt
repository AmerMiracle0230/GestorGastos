// archivo: CategoryLegend.kt
// que hace: leyenda de categorias con círculo de color, nombre y porcentaje
// usado en: StatsScreen

package com.example.gestorgastos.screens.stats.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestorgastos.data.entity.Categoria
import kotlin.math.roundToInt

@Composable
fun CategoryLegend(
    gastosPorCategoria: List<Pair<Int, Double>>,
    mapaCategorias: Map<Int, Categoria>,
    totalGastos: Double,
    isDarkTheme: Boolean
) {
    val primaryColor = MaterialTheme.colorScheme.primary

    val cardBackground = if (isDarkTheme) {
        Color.Black.copy(alpha = 0.4f)
    } else {
        Color.White.copy(alpha = 0.6f)
    }

    // si no hay gastos, mostrar mensaje
    if (gastosPorCategoria.isEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = primaryColor.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = cardBackground),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "No hay categorias con gastos",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
        return
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = primaryColor.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "📋 DETALLE POR CATEGORIAS",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            gastosPorCategoria.forEachIndexed { index, (catId, total) ->
                val categoria = mapaCategorias[catId]
                if (categoria != null) {
                    val porcentaje = if (totalGastos > 0) {
                        ((total / totalGastos) * 100).roundToInt()
                    } else 0

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // círculo de color de la categoria
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(RoundedCornerShape(7.dp))
                                .background(Color(categoria.color))
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        // emoji + nombre
                        Text(
                            text = "${categoria.icono} ${categoria.nombre}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        // porcentaje
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(categoria.color).copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "$porcentaje%",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(categoria.color),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    // separador entre items (usando HorizontalDivider en lugar de Divider)
                    if (index < gastosPorCategoria.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 4.dp),
                            thickness = 0.5.dp,
                            color = Color.Gray.copy(alpha = 0.2f)
                        )
                    }
                }
            }
        }
    }
}