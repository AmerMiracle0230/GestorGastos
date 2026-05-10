// archivo: ResumenStatsCard.kt
// que hace: tarjeta de resumen para ingresos o gastos en pantalla de estadisticas
// muestra: icono, total con moneda, título
// usado en: StatsScreen

package com.example.gestorgastos.screens.stats.components

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
import java.util.Locale

@Composable
fun ResumenStatsCard(
    titulo: String,          // "INGRESOS" o "GASTOS"
    icono: String,           // emoji: "💰" o "💸"
    total: Double,           // cantidad total
    color: Color,            // color del texto (verde para ingresos, rojo para gastos)
    isDarkTheme: Boolean,    // tema oscuro o claro
    moneda: String,          // simbolo de moneda (€, $, £)
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary

    // fondo semitransparente según tema
    val cardBackground = if (isDarkTheme) {
        Color.Black.copy(alpha = 0.4f)
    } else {
        Color.White.copy(alpha = 0.6f)
    }

    Card(
        modifier = modifier
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
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // icono (emoji)
            Text(icono, fontSize = 20.sp)
            // total con moneda
            Text(
                text = String.format(Locale.getDefault(), "%.0f$moneda", total),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            // titulo
            Text(titulo, fontSize = 11.sp, color = color)
        }
    }
}