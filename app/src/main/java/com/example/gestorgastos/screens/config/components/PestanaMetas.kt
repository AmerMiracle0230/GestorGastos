package com.example.gestorgastos.screens.config.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestorgastos.data.entity.Categoria

@Composable
fun PestanaMetas(
    categorias: List<Categoria>,
    objetivosEditados: MutableMap<Int, String>,
    onObjetivoChange: (Int, String) -> Unit,
    onGuardarObjetivos: () -> Unit,
    isDarkTheme: Boolean,
    primaryColor: Color,
    moneda: String = "€"  // 🔥 SOLO AÑADIR ESTO
) {
    val cardBackground = if (isDarkTheme) {
        Color.Black.copy(alpha = 0.4f)
    } else {
        Color.White.copy(alpha = 0.6f)
    }

    val categoriasGasto = categorias.filter { it.tipo == "gasto" }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categoriasGasto) { categoria ->
            val valorActual = objetivosEditados[categoria.id] ?: categoria.objetivo.toString()

            MetaRow(
                categoria = categoria,
                valorActual = valorActual,
                onValorChange = { nuevoValor ->
                    onObjetivoChange(categoria.id, nuevoValor)
                    onGuardarObjetivos()
                },
                primaryColor = primaryColor,
                cardBackground = cardBackground,
                moneda = moneda  // 🔥 PASAR MONEDA
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun MetaRow(
    categoria: Categoria,
    valorActual: String,
    onValorChange: (String) -> Unit,
    primaryColor: Color,
    cardBackground: Color,
    moneda: String  // 🔥 AÑADIR
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = primaryColor.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackground),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 🔥 ICONO + NOMBRE (como estaba, sin cambios)
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Text(categoria.icono, fontSize = 20.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = categoria.nombre,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // 🔥 INPUT CON MONEDA DINÁMICA (solo cambio aquí)
            OutlinedTextField(
                value = valorActual,
                onValueChange = { newValue ->
                    if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d{0,2}?$"))) {
                        onValorChange(newValue)
                    }
                },
                modifier = Modifier.width(100.dp),
                label = { Text(moneda, fontSize = 11.sp) },  // 🔥 USAR MONEDA
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                textStyle = androidx.compose.ui.text.TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = androidx.compose.ui.text.style.TextAlign.End
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    unfocusedBorderColor = primaryColor.copy(alpha = 0.3f)
                )
            )
        }
    }
}