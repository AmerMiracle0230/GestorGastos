// archivo: TarjetaResumen.kt
// que hace: tarjeta de resumen financiero (ingresos, gastos, saldo)
// usado en: ListaScreen

package com.example.gestorgastos.screens.lista.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun TarjetaResumen(
    totalGastos: Double,     // suma de todos los gastos
    totalIngresos: Double,   // suma de todos los ingresos
    saldo: Double,           // ingresos - gastos
    isDarkTheme: Boolean,    // tema oscuro o claro
    moneda: String = "€"     // simbolo de moneda (€, $, £)
) {
    // colores del tema
    val primaryColor = MaterialTheme.colorScheme.primary
    val accentColor = primaryColor.copy(alpha = 0.5f)

    // colores para gastos e ingresos segun tema
    val gastoColor = if (isDarkTheme) Color(0xFFFF6B6B) else Color(0xFFDC3545)
    val ingresoColor = if (isDarkTheme) Color(0xFF4ADE80) else Color(0xFF28A745)

    // colores de texto
    val textColor = MaterialTheme.colorScheme.onSurface
    val textSecondaryColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(
            width = 2.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    primaryColor.copy(alpha = 0.7f),
                    primaryColor,
                    accentColor,
                    primaryColor,
                    primaryColor.copy(alpha = 0.7f)
                )
            )
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
            // titulo con icono
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountBalance,
                    contentDescription = null,
                    tint = primaryColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "RESUMEN FINANCIERO",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    letterSpacing = 1.sp
                )
            }

            // linea decorativa
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                primaryColor.copy(alpha = 0.1f),
                                primaryColor,
                                accentColor,
                                primaryColor,
                                primaryColor.copy(alpha = 0.1f)
                            )
                        )
                    )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // fila de gastos
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.AutoMirrored.Filled.TrendingDown, null, tint = gastoColor, modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Gastos", fontWeight = FontWeight.Medium, fontSize = 15.sp, color = textSecondaryColor)
                }
                Text("${String.format(Locale.getDefault(), "%.2f", totalGastos)}$moneda", color = gastoColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // fila de ingresos
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.AutoMirrored.Filled.TrendingUp, null, tint = ingresoColor, modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Ingresos", fontWeight = FontWeight.Medium, fontSize = 15.sp, color = textSecondaryColor)
                }
                Text("${String.format(Locale.getDefault(), "%.2f", totalIngresos)}$moneda", color = ingresoColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            // separador
            Box(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp).height(1.dp).background(if (isDarkTheme) Color.White.copy(alpha = 0.1f) else Color.Black.copy(alpha = 0.08f)))

            // fila de saldo
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.ArrowUpward, null, tint = if (saldo >= 0) ingresoColor else gastoColor, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Saldo", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = textColor)
                }
                Text(
                    text = "${String.format(Locale.getDefault(), "%.2f", saldo)}$moneda",
                    color = if (saldo >= 0) ingresoColor else gastoColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    style = TextStyle(
                        shadow = Shadow(
                            color = (if (saldo >= 0) ingresoColor else gastoColor).copy(alpha = 0.5f),
                            offset = Offset(2f, 2f),
                            blurRadius = 8f
                        )
                    )
                )
            }
        }
    }
}