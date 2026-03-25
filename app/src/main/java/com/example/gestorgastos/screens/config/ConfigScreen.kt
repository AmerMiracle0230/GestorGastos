package com.example.gestorgastos.screens.config

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gestorgastos.components.TituloSecundario

@Composable
fun ConfigScreen(
    viewModel: ConfigViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val monedaActual by viewModel.monedaActual.collectAsState()
    val temaActual by viewModel.temaActual.collectAsState()
    val todasCategorias by viewModel.todasCategorias.collectAsState()
    val categoriasDestacadasIds by viewModel.categoriasDestacadasIds.collectAsState()

    var monedaSeleccionada by remember { mutableStateOf(monedaActual) }
    var temaSeleccionado by remember { mutableStateOf(temaActual) }

    var categoriasSeleccionadas by remember { mutableStateOf(categoriasDestacadasIds.toMutableSet()) }

    LaunchedEffect(categoriasDestacadasIds) {
        categoriasSeleccionadas = categoriasDestacadasIds.toMutableSet()
    }

    var objetivosEditados by remember { mutableStateOf(mutableMapOf<Int, String>()) }

    // 🆕 Estado para el diálogo de añadir categoría
    var showAddCategoriaDialog by remember { mutableStateOf(false) }
    var nuevaCategoriaNombre by remember { mutableStateOf("") }
    var nuevaCategoriaIcono by remember { mutableStateOf("📦") }
    var nuevaCategoriaColor by remember { mutableStateOf(0xFF9E9E9E) }
    var nuevaCategoriaTipo by remember { mutableStateOf("gasto") }
    var nuevaCategoriaObjetivo by remember { mutableStateOf("200") }

    // Iconos comunes para seleccionar
    val iconosComunes = listOf("🍔", "🚗", "🎮", "🏠", "💊", "🎓", "💰", "🎁", "🎀", "📦", "⚽", "📚", "✈️", "🎬", "🍕")

    // Colores comunes
    val coloresComunes = listOf(
        0xFF4CAF50, // Verde
        0xFF2196F3, // Azul
        0xFFFF9800, // Naranja
        0xFF795548, // Marrón
        0xFFF44336, // Rojo
        0xFF3F51B5, // Azul oscuro
        0xFF9C27B0, // Morado
        0xFFE91E63, // Rosa
        0xFF9E9E9E  // Gris
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TituloSecundario(
            texto = "CONFIGURACIÓN",
            onBackClick = onBack
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // ========== MONEDA ==========
            item {
                Text(
                    text = "💶 MONEDA",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    listOf("€", "$", "£").forEach { moneda ->
                        FilterChip(
                            selected = monedaSeleccionada == moneda,
                            onClick = { monedaSeleccionada = moneda },
                            label = { Text(moneda) },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // ========== TEMA ==========
            item {
                Text(
                    text = "🌙 TEMA",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    listOf(
                        "sistema" to "⚙️ Sistema",
                        "claro" to "☀️ Claro",
                        "oscuro" to "🌙 Oscuro"
                    ).forEach { (valor, nombre) ->
                        FilterChip(
                            selected = temaSeleccionado == valor,
                            onClick = { temaSeleccionado = valor },
                            label = { Text(nombre) },
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // ========== CATEGORÍAS DESTACADAS ==========
            item {
                Text(
                    text = "⭐ CATEGORÍAS DESTACADAS",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Selecciona hasta 4 categorías (las que no elijas se rellenan con las más gastadas)",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(todasCategorias.filter { it.tipo != "ingreso" }) { categoria ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = categoriasSeleccionadas.contains(categoria.id),
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                if (categoriasSeleccionadas.size < 4) {
                                    categoriasSeleccionadas.add(categoria.id)
                                }
                            } else {
                                categoriasSeleccionadas.remove(categoria.id)
                            }
                        }
                    )
                    Text(
                        text = "${categoria.icono} ${categoria.nombre}",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            // Botón para añadir categoría
            item {
                Button(
                    onClick = { showAddCategoriaDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("➕ AÑADIR CATEGORÍA")
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            // ========== OBJETIVOS MENSUALES ==========
            item {
                Text(
                    text = "🎯 OBJETIVOS MENSUALES",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Define cuánto quieres gastar en cada categoría este mes",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(todasCategorias.filter { it.tipo == "gasto" || it.tipo == "ambos" }) { categoria ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${categoria.icono} ${categoria.nombre}",
                        fontSize = 14.sp,
                        modifier = Modifier.weight(2f)
                    )
                    OutlinedTextField(
                        value = objetivosEditados[categoria.id] ?: categoria.objetivo.toString(),
                        onValueChange = {
                            objetivosEditados = objetivosEditados.toMutableMap().apply {
                                put(categoria.id, it)
                            }
                        },
                        label = { Text("€") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // ========== BOTÓN GUARDAR ==========
            item {
                Button(
                    onClick = {
                        viewModel.guardarMoneda(monedaSeleccionada)
                        viewModel.guardarTema(temaSeleccionado)
                        viewModel.guardarCategoriasDestacadas(categoriasSeleccionadas)
                        objetivosEditados.forEach { (id, valor) ->
                            val nuevoObjetivo = valor.toDoubleOrNull() ?: 0.0
                            viewModel.actualizarObjetivo(id, nuevoObjetivo)
                        }
                        onBack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("💾 GUARDAR CAMBIOS")
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }

            // ========== INFORMACIÓN ==========
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "ℹ️ INFORMACIÓN",
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Versión: 1.0.0")
                        Text("ARK - Gestor de Finanzas")
                        Text("© 2024 - DAM2")
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }

    // ========== DIÁLOGO PARA AÑADIR CATEGORÍA ==========
    if (showAddCategoriaDialog) {
        // 🆕 Variables con tipos correctos
        var nuevaCategoriaNombre by remember { mutableStateOf("") }
        var nuevaCategoriaIcono by remember { mutableStateOf("📦") }
        var nuevaCategoriaColor by remember { mutableStateOf(0xFF9E9E9E.toInt()) }  // ← convertido a Int
        var nuevaCategoriaTipo by remember { mutableStateOf("gasto") }
        var nuevaCategoriaObjetivo by remember { mutableStateOf("200") }

        val iconosComunes = listOf("🍔", "🚗", "🎮", "🏠", "💊", "🎓", "💰", "🎁", "🎀", "📦", "⚽", "📚", "✈️", "🎬", "🍕")

        val coloresComunes = listOf(
            0xFF4CAF50.toInt(),
            0xFF2196F3.toInt(),
            0xFFFF9800.toInt(),
            0xFF795548.toInt(),
            0xFFF44336.toInt(),
            0xFF3F51B5.toInt(),
            0xFF9C27B0.toInt(),
            0xFFE91E63.toInt(),
            0xFF9E9E9E.toInt()
        )

        AlertDialog(
            onDismissRequest = { showAddCategoriaDialog = false },
            title = { Text("➕ NUEVA CATEGORÍA") },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 500.dp)
                ) {
                    // Nombre
                    OutlinedTextField(
                        value = nuevaCategoriaNombre,
                        onValueChange = { nuevaCategoriaNombre = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Icono
                    Text("Icono:", fontSize = 12.sp)
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        items(iconosComunes) { icono ->
                            FilterChip(
                                selected = nuevaCategoriaIcono == icono,
                                onClick = { nuevaCategoriaIcono = icono },
                                label = { Text(icono, fontSize = 20.sp) },
                                modifier = Modifier.padding(end = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Color
                    Text("Color:", fontSize = 12.sp)
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        items(coloresComunes) { color ->
                            FilterChip(
                                selected = nuevaCategoriaColor == color,
                                onClick = { nuevaCategoriaColor = color },
                                label = {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .background(Color(color))
                                    )
                                },
                                modifier = Modifier.padding(end = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Tipo
                    Text("Tipo:", fontSize = 12.sp)
                    Row {
                        listOf("gasto", "ingreso", "ambos").forEach { tipo ->
                            FilterChip(
                                selected = nuevaCategoriaTipo == tipo,
                                onClick = { nuevaCategoriaTipo = tipo },
                                label = {
                                    Text(
                                        when (tipo) {
                                            "gasto" -> "💸 Gasto"
                                            "ingreso" -> "💰 Ingreso"
                                            else -> "🔄 Ambos"
                                        }
                                    )
                                },
                                modifier = Modifier.padding(end = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Objetivo (solo si no es ingreso)
                    if (nuevaCategoriaTipo != "ingreso") {
                        OutlinedTextField(
                            value = nuevaCategoriaObjetivo,
                            onValueChange = { nuevaCategoriaObjetivo = it },
                            label = { Text("Objetivo mensual (€)") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (nuevaCategoriaNombre.isNotBlank()) {
                            val objetivo = if (nuevaCategoriaTipo != "ingreso") {
                                nuevaCategoriaObjetivo.toDoubleOrNull() ?: 0.0
                            } else 0.0
                            viewModel.crearCategoria(
                                nombre = nuevaCategoriaNombre,
                                icono = nuevaCategoriaIcono,
                                color = nuevaCategoriaColor,  // ← ahora es Int
                                tipo = nuevaCategoriaTipo,
                                objetivo = objetivo
                            )
                            showAddCategoriaDialog = false
                            nuevaCategoriaNombre = ""
                            nuevaCategoriaIcono = "📦"
                            nuevaCategoriaColor = 0xFF9E9E9E.toInt()
                            nuevaCategoriaTipo = "gasto"
                            nuevaCategoriaObjetivo = "200"
                        }
                    }
                ) {
                    Text("CREAR")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddCategoriaDialog = false }) {
                    Text("CANCELAR")
                }
            }
        )
    }
}