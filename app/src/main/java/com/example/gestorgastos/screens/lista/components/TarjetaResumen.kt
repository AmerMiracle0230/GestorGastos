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

@Composable
fun TarjetaResumen(
    totalGastos: Double,
    totalIngresos: Double,
    saldo: Double,
    isDarkTheme: Boolean
) {
    val primaryColor = if (isDarkTheme) Color(0xFF29B6F6) else Color(0xFFF57C00)
    val accentColor = if (isDarkTheme) Color(0xFF81D4FA) else Color(0xFFFFB74D)
    val gastoColor = if (isDarkTheme) Color(0xFFFF6B6B) else Color(0xFFDC3545)
    val ingresoColor = if (isDarkTheme) Color(0xFF4ADE80) else Color(0xFF28A745)
    val textColor = if (isDarkTheme) Color.White else Color(0xFF1A1A2E)
    val textSecondaryColor = if (isDarkTheme) Color(0xFFE0E0E0) else Color(0xFF747272)

    // Eliminamos la transparencia (alpha) para evitar el efecto de "doble cuadro"
    val cardBackground = if (isDarkTheme) Color(0xFF151B54) else Color.White

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        border = BorderStroke(
            width = 2.dp, // Un poco más grueso para que destaque bien
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

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.AutoMirrored.Filled.TrendingDown, null, tint = gastoColor, modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Gastos", fontWeight = FontWeight.Medium, fontSize = 15.sp, color = textSecondaryColor)
                }
                Text("${String.format("%.2f", totalGastos)}€", color = gastoColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.AutoMirrored.Filled.TrendingUp, null, tint = ingresoColor, modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Ingresos", fontWeight = FontWeight.Medium, fontSize = 15.sp, color = textSecondaryColor)
                }
                Text("${String.format("%.2f", totalIngresos)}€", color = ingresoColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            Box(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp).height(1.dp).background(if (isDarkTheme) Color.White.copy(alpha = 0.1f) else Color.Black.copy(alpha = 0.08f)))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.ArrowUpward, null, tint = if (saldo >= 0) ingresoColor else gastoColor, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Saldo", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = textColor)
                }
                Text(
                    text = "${String.format("%.2f", saldo)}€",
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