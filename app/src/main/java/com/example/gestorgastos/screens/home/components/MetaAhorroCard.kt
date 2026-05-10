// archivo: MetaAhorroCard.kt
// que hace: tarjeta que muestra información de la meta de ahorro
// muestra: ingresos, meta, ahorrado, cuanto queda para gastar
// usado en: HomeScreen

package com.example.gestorgastos.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun MetaAhorroCard(
    ingresosTotales: Double,   // total de ingresos del mes
    metaAhorro: Double,        // objetivo de ahorro del usuario
    saldoActual: Double,       // saldo disponible (ingresos - gastos)
    isDarkTheme: Boolean,      // tema oscuro o claro
    moneda: String             // simbolo de moneda (€, $, £)
) {
    val primaryColor = MaterialTheme.colorScheme.primary

    // cuanto se ha ahorrado realmente (minimo entre la meta y el saldo)
    val ahorroActual = minOf(metaAhorro, saldoActual.coerceAtLeast(0.0))

    // cuanto queda disponible para gastar sin tocar el ahorro
    val dineroParaGastar = (saldoActual - ahorroActual).coerceAtLeast(0.0)

    // color de las etiquetas segun tema
    val labelColor = if (isDarkTheme) Color.White else Color.Gray

    // color del texto "ahorrado" (verde si alcanzo la meta, rojo si no)
    val colorAhorrado = if (ahorroActual >= metaAhorro && metaAhorro > 0) {
        Color(0xFF4CAF50)   // verde meta alcanzada
    } else if (metaAhorro > 0 && ahorroActual < metaAhorro) {
        Color(0xFFF44336)   // rojo meta no alcanzada
    } else {
        primaryColor
    }

    // color del texto "puedes gastar" (naranja normal, rojo si no hay dinero)
    val colorPuedesGastar = if (dineroParaGastar <= 0) {
        Color(0xFFF44336)   // rojo sin dinero
    } else {
        Color(0xFFFF9800)   // naranja normal
    }

    // texto de "puedes gastar" (muestra "SIN DINERO" si es 0)
    val textoPuedesGastar = if (dineroParaGastar <= 0) {
        "SIN DINERO"
    } else {
        String.format(Locale.getDefault(), "%.2f$moneda", dineroParaGastar)
    }

    // fondo semitransparente según tema
    val cardBackground = if (isDarkTheme) {
        Color.Black.copy(alpha = 0.4f)
    } else {
        Color.White.copy(alpha = 0.6f)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = primaryColor.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            // titulo de la tarjeta
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🎯", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "META DE AHORRO",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // fila de ingresos totales
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("💰 Ingresos del mes:", fontSize = 14.sp, color = labelColor)
                Text(
                    text = String.format(Locale.getDefault(), "%.2f$moneda", ingresosTotales),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // fila de meta de ahorro
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("🎯 Meta ahorro:", fontSize = 14.sp, color = labelColor)
                Text(
                    text = String.format(Locale.getDefault(), "%.2f$moneda", metaAhorro),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // linea separadora
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray.copy(alpha = 0.2f))
            )

            Spacer(modifier = Modifier.height(12.dp))

            // fila de ahorro actual
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("🏦 Ahorrado:", fontSize = 14.sp, color = labelColor)
                Text(
                    text = String.format(Locale.getDefault(), "%.2f$moneda", ahorroActual),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorAhorrado
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // fila de dinero disponible para gastar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("💳 Puedes gastar:", fontSize = 14.sp, color = labelColor)
                Text(
                    text = textoPuedesGastar,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorPuedesGastar
                )
            }
        }
    }
}