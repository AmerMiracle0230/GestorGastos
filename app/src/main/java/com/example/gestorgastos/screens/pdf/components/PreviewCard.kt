// archivo: PreviewCard.kt
// que hace: tarjeta de vista previa del PDF
// muestra: resumen de ingresos, gastos, saldo y boton para generar
// usado en: PdfScreen

package com.example.gestorgastos.screens.pdf.components

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
fun PreviewCard(
    totalIngresos: Double,
    totalGastos: Double,
    saldo: Double,
    fechaActual: String,
    isDarkTheme: Boolean,
    moneda: String,
    onGenerarPdf: () -> Unit,
    generando: Boolean,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val errorColor = MaterialTheme.colorScheme.error

    val labelColor = if (isDarkTheme) Color.White else Color.Gray
    val fechaColor = if (isDarkTheme) Color.White.copy(alpha = 0.7f) else Color.Gray

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
                color = primaryColor,
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("📄", fontSize = 48.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "INFORME DE FINANZAS",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )

            Text(
                text = "Generado: $fechaActual",
                fontSize = 12.sp,
                color = fechaColor
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp),
                thickness = 1.dp,
                color = Color.Gray.copy(alpha = 0.2f)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("💰 Ingresos:", fontSize = 14.sp, color = labelColor)
                Text(
                    text = String.format(Locale.getDefault(), "%.2f$moneda", totalIngresos),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF4CAF50)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("💸 Gastos:", fontSize = 14.sp, color = labelColor)
                Text(
                    text = String.format(Locale.getDefault(), "%.2f$moneda", totalGastos),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = errorColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("💳 Saldo:", fontSize = 14.sp, color = labelColor)
                Text(
                    text = String.format(Locale.getDefault(), "%.2f$moneda", saldo),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = if (saldo >= 0) Color(0xFF4CAF50) else errorColor
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            BotonGenerarPdf(
                onClick = onGenerarPdf,
                generando = generando
            )
        }
    }
}