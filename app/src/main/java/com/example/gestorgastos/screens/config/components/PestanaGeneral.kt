package com.example.gestorgastos.screens.config.components

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

@Composable
fun PestanaGeneral(
    monedaSeleccionada: String,
    onMonedaChange: (String) -> Unit,
    temaActual: String,
    onTemaChange: (String) -> Unit,
    isDarkTheme: Boolean
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val cardBackground = if (isDarkTheme) {
        Color.Black.copy(alpha = 0.4f)
    } else {
        Color.White.copy(alpha = 0.6f)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // MONEDA
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
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text("💰 MONEDA", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = primaryColor)
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    listOf("€", "$", "£").forEach { moneda ->
                        FilterChip(
                            selected = monedaSeleccionada == moneda,
                            onClick = { onMonedaChange(moneda) },
                            label = { Text(moneda, fontSize = 18.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = primaryColor,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
        }

        // TEMA
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
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text("🌙 TEMA", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = primaryColor)
                Spacer(modifier = Modifier.height(12.dp))
                Column {
                    listOf("sistema" to "⚙️ Sistema", "claro" to "☀️ Claro", "oscuro" to "🌙 Oscuro").forEach { (valor, nombre) ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = temaActual == valor,
                                onClick = { onTemaChange(valor) },
                                colors = RadioButtonDefaults.colors(selectedColor = primaryColor)
                            )
                            Text(nombre, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}