// archivo: BarraFiltros.kt
// que hace: barra de filtros para la lista de movimientos
// permite: buscar por texto, filtrar por tipo (gastos/ingresos) o categoria

package com.example.gestorgastos.screens.lista.components

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
import com.example.gestorgastos.screens.lista.Filtro

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
    val primaryColor = MaterialTheme.colorScheme.primary
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
                    modifier = Modifier.background(
                        if (isDarkTheme) MaterialTheme.colorScheme.surface
                        else Color.White
                    )
                ) {
                    DropdownMenuItem(
                        text = {
                            Row {
                                Icon(Icons.AutoMirrored.Filled.List, null, tint = MaterialTheme.colorScheme.onSurface)
                                Spacer(Modifier.width(8.dp))
                                Text("Todos", color = MaterialTheme.colorScheme.onSurface)
                            }
                        },
                        onClick = { onFiltroChange(Filtro.Todos); expanded = false }
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
                        onClick = { onFiltroChange(Filtro.SoloGastos); expanded = false }
                    )

                    categoriasGastos.forEach { categoria ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "  • $categoria",
                                    modifier = Modifier.padding(start = 16.dp),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            },
                            onClick = { onFiltroChange(Filtro.CategoriaGasto(categoria)); expanded = false }
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
                        onClick = { onFiltroChange(Filtro.SoloIngresos); expanded = false }
                    )

                    categoriasIngresos.forEach { categoria ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "  • $categoria",
                                    modifier = Modifier.padding(start = 16.dp),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            },
                            onClick = { onFiltroChange(Filtro.CategoriaIngreso(categoria)); expanded = false }
                        )
                    }
                }
            }
        }

        if (filtroSeleccionado != Filtro.Todos) {
            Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                Text("Filtro: ", fontSize = 12.sp, color = Color.Gray)
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = primaryColor.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = when (filtroSeleccionado) {
                            is Filtro.Todos -> "Todos"
                            is Filtro.SoloGastos -> "Solo Gastos"
                            is Filtro.SoloIngresos -> "Solo Ingresos"
                            is Filtro.CategoriaGasto -> filtroSeleccionado.nombre
                            is Filtro.CategoriaIngreso -> filtroSeleccionado.nombre
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