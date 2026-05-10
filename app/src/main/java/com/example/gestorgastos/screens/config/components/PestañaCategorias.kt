// archivo: PestañaCategorias.kt
// que hace: pestaña de configuracion de categorias destacadas
// permite: seleccionar hasta 4 categorias para mostrar en home
// usado en: ConfigScreen

package com.example.gestorgastos.screens.config.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.gestorgastos.data.entity.Categoria

@Composable
fun PestanaCategorias(
    categorias: List<Categoria>,
    categoriasSeleccionadas: Set<Int>,
    onCategoriaCheckedChange: (Int, Boolean) -> Unit,
    onEditarCategoria: (Categoria) -> Unit,
    onEliminarCategoria: (Categoria) -> Unit,
    isDarkTheme: Boolean
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val cardBackground = if (isDarkTheme) {
        Color.Black.copy(alpha = 0.4f)
    } else {
        Color.White.copy(alpha = 0.6f)
    }

    // solo categorias de tipo gasto
    val todasCategoriasGasto = categorias.filter { it.tipo == "gasto" }

    // categorias seleccionadas como destacadas
    val destacadas = todasCategoriasGasto.filter { it.id in categoriasSeleccionadas }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // seccion de categorias destacadas (arriba)
        item {
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
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text(
                        text = "⭐ CATEGORIAS DESTACADAS (${categoriasSeleccionadas.size}/4)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    )
                    Text(
                        text = "Estas categorias apareceran en la pantalla de inicio",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    if (destacadas.isNotEmpty()) {
                        androidx.compose.foundation.layout.FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            destacadas.forEach { categoria ->
                                CategoriaDestacadaItem(
                                    categoria = categoria,
                                    primaryColor = primaryColor
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "No hay categorias destacadas",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }

        // seccion de todas las categorias (abajo)
        items(todasCategoriasGasto) { categoria ->
            val isSelected = categoriasSeleccionadas.contains(categoria.id)
            val isMaxReached = categoriasSeleccionadas.size >= 4 && !isSelected

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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // checkbox + icono + nombre
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = { checked ->
                                if (!isMaxReached || !checked) {
                                    onCategoriaCheckedChange(categoria.id, checked)
                                }
                            },
                            colors = CheckboxDefaults.colors(checkedColor = primaryColor),
                            enabled = !isMaxReached
                        )
                        Text(categoria.icono, fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = categoria.nombre,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    // botones editar y eliminar
                    Row {
                        IconButton(onClick = { onEditarCategoria(categoria) }) {
                            Text("✏️", fontSize = 16.sp)
                        }
                        IconButton(onClick = { onEliminarCategoria(categoria) }) {
                            Text("🗑️", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoriaDestacadaItem(
    categoria: Categoria,
    primaryColor: Color
) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .border(width = 1.5.dp, color = primaryColor, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = primaryColor.copy(alpha = 0.15f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(categoria.icono, fontSize = 18.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = categoria.nombre,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor,
                maxLines = 1
            )
        }
    }
}