package com.example.gestorgastos.screens.lista

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BarraFiltros(
    textoBusqueda: String,
    onTextoBusquedaChange: (String) -> Unit,
    filtroSeleccionado: Filtro,
    onFiltroChange: (Filtro) -> Unit,
    categoriasGastos: List<String>,
    categoriasIngresos: List<String>,
    isDarkTheme: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    val primaryColor = if (isDarkTheme) Color(0xFF29B6F6) else Color(0xFFF57C00)
    val gastoColor = if (isDarkTheme) Color(0xFFFF6B6B) else Color(0xFFDC3545)
    val ingresoColor = if (isDarkTheme) Color(0xFF4ADE80) else Color(0xFF28A745)

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = onTextoBusquedaChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("🔍 Buscar...", fontSize = 14.sp) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    unfocusedBorderColor = primaryColor.copy(alpha = 0.5f)
                )
            )

            Box {
                Button(
                    onClick = { expanded = true },
                    modifier = Modifier.height(56.dp).width(80.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                ) {
                    Icon(Icons.Default.FilterList, null, modifier = Modifier.size(20.dp))
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(if (isDarkTheme) Color(0xFF1E1E2F) else Color.White)
                ) {
                    DropdownMenuItem(
                        text = {
                            Row {
                                Icon(Icons.AutoMirrored.Filled.List, null, tint = if (isDarkTheme) Color.White else Color.Black)
                                Spacer(Modifier.width(8.dp))
                                Text("Todos", color = if (isDarkTheme) Color.White else Color.Black)
                            }
                        },
                        onClick = { onFiltroChange(Filtro.TODOS); expanded = false }
                    )

                    HorizontalDivider()

                    DropdownMenuItem(
                        text = {
                            Row {
                                Icon(Icons.AutoMirrored.Filled.TrendingDown, null, tint = gastoColor)
                                Spacer(Modifier.width(8.dp))
                                Text("Solo Gastos", color = gastoColor)
                            }
                        },
                        onClick = { onFiltroChange(Filtro.SOLO_GASTOS); expanded = false }
                    )

                    categoriasGastos.forEach { categoria ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "  • $categoria",
                                    modifier = Modifier.padding(start = 16.dp),
                                    color = if (isDarkTheme) Color.White else Color.Black
                                )
                            },
                            onClick = { onFiltroChange(Filtro.CATEGORIA_GASTO(categoria)); expanded = false }
                        )
                    }

                    HorizontalDivider()

                    DropdownMenuItem(
                        text = {
                            Row {
                                Icon(Icons.AutoMirrored.Filled.TrendingUp, null, tint = ingresoColor)
                                Spacer(Modifier.width(8.dp))
                                Text("Solo Ingresos", color = ingresoColor)
                            }
                        },
                        onClick = { onFiltroChange(Filtro.SOLO_INGRESOS); expanded = false }
                    )

                    categoriasIngresos.forEach { categoria ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "  • $categoria",
                                    modifier = Modifier.padding(start = 16.dp),
                                    color = if (isDarkTheme) Color.White else Color.Black
                                )
                            },
                            onClick = { onFiltroChange(Filtro.CATEGORIA_INGRESO(categoria)); expanded = false }
                        )
                    }
                }
            }
        }

        if (filtroSeleccionado != Filtro.TODOS) {
            Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text("Filtro: ", fontSize = 12.sp, color = Color.Gray)
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = primaryColor.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = when (val current = filtroSeleccionado) {
                            is Filtro.TODOS -> "Todos"
                            is Filtro.SOLO_GASTOS -> "Solo Gastos"
                            is Filtro.SOLO_INGRESOS -> "Solo Ingresos"
                            is Filtro.CATEGORIA_GASTO -> current.nombre
                            is Filtro.CATEGORIA_INGRESO -> current.nombre
                        },
                        fontSize = 11.sp,
                        color = primaryColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}