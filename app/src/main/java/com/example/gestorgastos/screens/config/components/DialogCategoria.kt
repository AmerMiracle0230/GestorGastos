// archivo: DialogCategoria.kt
// que hace: dialogo para crear o editar una categoria
// usado en: ConfigScreen

package com.example.gestorgastos.screens.config.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestorgastos.data.entity.Categoria

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogCategoria(
    categoria: Categoria? = null,
    onDismiss: () -> Unit,
    onGuardar: (nombre: String, icono: String, color: Int, tipo: String, objetivo: Double) -> Unit
) {
    var nombre by remember { mutableStateOf(categoria?.nombre ?: "") }
    var icono by remember { mutableStateOf(categoria?.icono ?: "📦") }

    // color inicial sin conversion redundante
    val colorInicial = categoria?.color ?: 0xFF9E9E9E.toInt()
    var colorSeleccionado by remember { mutableIntStateOf(colorInicial) }

    var tipo by remember { mutableStateOf(categoria?.tipo ?: "gasto") }
    var objetivo by remember { mutableStateOf(categoria?.objetivo?.toString() ?: "0") }

    // colores
    val colores = listOf(
        0xFF4CAF50.toInt(),
        0xFF2196F3.toInt(),
        0xFFFF9800.toInt(),
        0xFFF44336.toInt(),
        0xFF9C27B0.toInt(),
        0xFF795548.toInt(),
        0xFF607D8B.toInt(),
        0xFFE91E63.toInt()
    )

    val iconos = listOf("🍔", "🚗", "🎮", "🏠", "💊", "🎓", "💰", "🎁", "📦", "⚽", "📚", "💼", "🛒", "🍿", "✈️", "🏥")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (categoria == null) "Nueva Categoria" else "Editar Categoria") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.heightIn(max = 450.dp)
            ) {
                // campo nombre
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                // tipo (gasto o ingreso)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = tipo == "gasto",
                        onClick = { tipo = "gasto" },
                        label = { Text("💸 Gasto") },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = tipo == "ingreso",
                        onClick = { tipo = "ingreso" },
                        label = { Text("💰 Ingreso") },
                        modifier = Modifier.weight(1f)
                    )
                }

                // selector de icono
                Text("Icono:", fontSize = 12.sp, color = Color.Gray)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(iconos) { icon ->
                        FilterChip(
                            selected = icono == icon,
                            onClick = { icono = icon },
                            label = { Text(icon, fontSize = 18.sp) }
                        )
                    }
                }

                // selector de color
                Text("Color:", fontSize = 12.sp, color = Color.Gray)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(colores) { colorValue ->
                        FilterChip(
                            selected = colorSeleccionado == colorValue,
                            onClick = { colorSeleccionado = colorValue },
                            label = {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(Color(colorValue), RoundedCornerShape(4.dp))
                                )
                            }
                        )
                    }
                }

                // objetivo (solo para gastos)
                if (tipo == "gasto") {
                    OutlinedTextField(
                        value = objetivo,
                        onValueChange = { objetivo = it },
                        label = { Text("Objetivo mensual (€)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val nombreValido = nombre.trim()
                    if (nombreValido.isNotEmpty()) {
                        onGuardar(
                            nombreValido,
                            icono,
                            colorSeleccionado,
                            tipo,
                            objetivo.toDoubleOrNull() ?: 0.0
                        )
                        onDismiss()
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}