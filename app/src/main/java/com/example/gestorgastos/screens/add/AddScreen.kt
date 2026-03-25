package com.example.gestorgastos.screens.add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gestorgastos.components.TituloSecundario
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    viewModel: AddViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    // Estados principales
    var modoAvanzado by remember { mutableStateOf(false) }
    var tipo by remember { mutableStateOf("gasto") }
    var nombre by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var categoriaId by remember { mutableStateOf<Int?>(null) }
    var fecha by remember { mutableStateOf(System.currentTimeMillis()) }
    var detalle by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    // Estados auxiliares para el Date Picker
    val calendar = remember { Calendar.getInstance() }
    var añoSeleccionado by remember { mutableStateOf(calendar.get(Calendar.YEAR)) }
    var mesSeleccionado by remember { mutableStateOf(calendar.get(Calendar.MONTH)) }
    var diaSeleccionado by remember { mutableStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }

    // Obtener categorías del ViewModel según el tipo ("gasto", "ingreso" o "ambos")
    val categorias by viewModel.obtenerCategoriasPorTipo(tipo).collectAsState(initial = emptyList())
    
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    val yearRange = (calendar.get(Calendar.YEAR) - 5)..calendar.get(Calendar.YEAR)

    Column(modifier = Modifier.fillMaxSize()) {
        TituloSecundario(texto = "NUEVO MOVIMIENTO", onBackClick = onBack)

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            // Selectores de Modo y Tipo
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { modoAvanzado = false }, modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = if (!modoAvanzado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)) { Text("⚡ BÁSICO") }
                Button(onClick = { modoAvanzado = true }, modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = if (modoAvanzado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)) { Text("🔧 AVANZADO") }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { 
                    tipo = "gasto"
                    categoriaId = null // Reiniciar selección al cambiar tipo
                }, modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = if (tipo == "gasto") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)) { Text("💸 GASTO") }
                Button(onClick = { 
                    tipo = "ingreso"
                    categoriaId = null 
                }, modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = if (tipo == "ingreso") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)) { Text("💰 INGRESO") }
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth(), placeholder = { Text("Ej: Café, Sueldo...") })
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = cantidad, onValueChange = { cantidad = it }, label = { Text("Cantidad (€)") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal))
            
            Spacer(modifier = Modifier.height(8.dp))

            // CATEGORÍA (Corregido con menuAnchor)
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = categorias.find { it.id == categoriaId }?.let { "${it.icono} ${it.nombre}" } ?: "Seleccionar categoría",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoría") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, enabled = true).fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    if (categorias.isEmpty()) {
                        DropdownMenuItem(text = { Text("Cargando categorías...") }, onClick = {})
                    } else {
                        categorias.forEach { categoria ->
                            DropdownMenuItem(
                                text = { Text("${categoria.icono} ${categoria.nombre}") },
                                onClick = { categoriaId = categoria.id; expanded = false }
                            )
                        }
                    }
                }
            }

            if (modoAvanzado) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = dateFormat.format(Date(fecha)), onValueChange = {}, readOnly = true,
                    label = { Text("Fecha") },
                    trailingIcon = { IconButton(onClick = { showDatePicker = true }) { Text("📅") } },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = detalle, onValueChange = { detalle = it }, label = { Text("Detalle (opcional)") }, modifier = Modifier.fillMaxWidth())
            }

            if (errorMsg.isNotEmpty()) {
                Text(text = errorMsg, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val cantidadNum = cantidad.toDoubleOrNull()
                    if (nombre.isBlank() || cantidadNum == null || categoriaId == null) {
                        errorMsg = "❌ Rellena todos los campos obligatorios"
                    } else {
                        viewModel.guardarMovimiento(tipo, nombre, cantidadNum, categoriaId!!, if (modoAvanzado) fecha else System.currentTimeMillis(), detalle)
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("💾 GUARDAR MOVIMIENTO") }
        }
    }

    // Date Picker Dialog Corregido
    if (showDatePicker) {
        AlertDialog(
            onDismissRequest = { showDatePicker = false },
            title = { Text("Seleccionar fecha") },
            text = {
                Column {
                    Text("Año:", fontWeight = FontWeight.Bold)
                    LazyRow {
                        val yearList = yearRange.toList()
                        items(yearList.size) { index ->
                            val year = yearList[index]
                            FilterChip(selected = añoSeleccionado == year, onClick = { añoSeleccionado = year }, label = { Text(year.toString()) }, modifier = Modifier.padding(2.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Mes:", fontWeight = FontWeight.Bold)
                    val meses = listOf("Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic")
                    LazyRow {
                        items(meses.size) { index ->
                            FilterChip(selected = mesSeleccionado == index, onClick = { mesSeleccionado = index }, label = { Text(meses[index]) }, modifier = Modifier.padding(2.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Día:", fontWeight = FontWeight.Bold)
                    val maxDias = when (mesSeleccionado) {
                        1 -> if (añoSeleccionado % 4 == 0) 29 else 28
                        3, 5, 8, 10 -> 30
                        else -> 31
                    }
                    if (diaSeleccionado > maxDias) diaSeleccionado = maxDias
                    LazyRow {
                        items(maxDias) { index ->
                            val dia = index + 1
                            FilterChip(selected = diaSeleccionado == dia, onClick = { diaSeleccionado = dia }, label = { Text(dia.toString()) }, modifier = Modifier.padding(2.dp))
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val cal = Calendar.getInstance().apply { 
                        set(Calendar.YEAR, añoSeleccionado)
                        set(Calendar.MONTH, mesSeleccionado)
                        set(Calendar.DAY_OF_MONTH, diaSeleccionado)
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                    fecha = cal.timeInMillis
                    showDatePicker = false
                }) { Text("Aceptar") }
            },
            dismissButton = { TextButton(onClick = { showDatePicker = false }) { Text("Cancelar") } }
        )
    }
}
