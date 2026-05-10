// archivo: SaldoCard.kt
// que hace: tarjeta que muestra el saldo disponible del usuario
// usado en: HomeScreen

package com.example.gestorgastos.screens.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

@Composable
fun SaldoCard(
    saldo: Double,      // saldo actual (ingresos - gastos)
    moneda: String      // simbolo de moneda (€, $, £)
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // titulo
        Text(
            text = "SALDO DISPONIBLE",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // valor del saldo con moneda
        Text(
            text = String.format(Locale.getDefault(), "%.2f$moneda", saldo),
            fontSize = 36.sp,
            fontWeight = FontWeight.Black,
            color = if (saldo >= 0) Color(0xFF4ADE80) else MaterialTheme.colorScheme.error
        )
    }
}