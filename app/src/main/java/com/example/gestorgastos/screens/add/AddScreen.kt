// archivo: AddScreen.kt
// que hace: pantalla para añadir gastos o ingresos
// tiene modo basico y modo avanzado (fecha + detalle)

package com.example.gestorgastos.screens.add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gestorgastos.components.FondoPantalla
import com.example.gestorgastos.screens.add.components.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    viewModel: AddViewModel = hiltViewModel(),
    onBack: () -> Unit,
    isDarkTheme: Boolean
) {
    // estados
    var modoAvanzado by remember { mutableStateOf(false) }
    var tipo by remember { mutableStateOf("gasto") }
    var nombre by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var categoriaId by remember { mutableStateOf<Int?>(null) }
    var fecha by remember { mutableLongStateOf(System.currentTimeMillis()) }  // usar mutableLongStateOf
    var detalle by remember { mutableStateOf("") }
    var errorMsg by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val moneda by viewModel.monedaActual.collectAsState()

    val categorias by viewModel.obtenerCategoriasPorTipo(tipo).collectAsState(initial = emptyList())
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    val calendar = remember { Calendar.getInstance() }
    val currentDate = remember { Calendar.getInstance() }
    val yearRange = (currentDate.get(Calendar.YEAR) - 5)..currentDate.get(Calendar.YEAR)

    FondoPantalla(isDarkTheme = isDarkTheme) {
        Scaffold(containerColor = Color.Transparent) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = innerPadding.calculateTopPadding() - 25.dp,
                        bottom = innerPadding.calculateBottomPadding(),
                        start = 16.dp,
                        end = 16.dp
                    )
            ) {
                // header con titulo y switch
                AddHeader(
                    modoAvanzado = modoAvanzado,
                    onModoAvanzadoChange = { modoAvanzado = it },
                    onBackClick = onBack
                )

                Spacer(modifier = Modifier.height(8.dp))

                // pestañas gasto/ingreso
                TipoTabs(
                    tipo = tipo,
                    onTipoChange = { nuevoTipo ->
                        tipo = nuevoTipo
                        categoriaId = null
                    }
                )

                // formulario scrolleable
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    // formulario basico
                    FormularioBasico(
                        nombre = nombre,
                        onNombreChange = { nombre = it },
                        cantidad = cantidad,
                        onCantidadChange = { cantidad = it },
                        categorias = categorias,
                        categoriaId = categoriaId,
                        onCategoriaChange = { categoriaId = it },
                        tipo = tipo,
                        isDarkTheme = isDarkTheme,
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        moneda = moneda
                    )

                    // formulario avanzado (modo experto)
                    if (modoAvanzado) {
                        FormularioAvanzado(
                            fecha = fecha,
                            onFechaClick = { showDatePicker = true },
                            detalle = detalle,
                            onDetalleChange = { detalle = it },
                            tipo = tipo,
                            dateFormat = dateFormat  // sin isDarkTheme
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // mensaje de error
                    MensajeError(
                        mensaje = errorMsg,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }

                // boton guardar
                BotonGuardar(
                    tipo = tipo,
                    onClick = {
                        val cantidadNum = cantidad.toDoubleOrNull()
                        if (nombre.isBlank()) {
                            errorMsg = "❌ El nombre no puede estar vacio"
                            return@BotonGuardar
                        }
                        if (cantidadNum == null || cantidadNum <= 0) {
                            errorMsg = "❌ Introduce una cantidad valida"
                            return@BotonGuardar
                        }
                        if (categoriaId == null) {
                            errorMsg = "❌ Selecciona una categoria"
                            return@BotonGuardar
                        }
                        if (modoAvanzado && fecha > System.currentTimeMillis()) {
                            errorMsg = "❌ No puedes poner fechas futuras"
                            return@BotonGuardar
                        }

                        viewModel.guardarMovimiento(
                            tipo = tipo,
                            nombre = nombre,
                            cantidad = cantidadNum,
                            categoriaId = categoriaId!!,
                            fecha = if (modoAvanzado) fecha else System.currentTimeMillis(),
                            detalle = detalle
                        )
                        onBack()
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // date picker dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismiss = { showDatePicker = false },
            onDateSelected = { nuevaFecha ->
                showDatePicker = false
                fecha = nuevaFecha
            },
            calendar = calendar,
            yearRange = yearRange
        )
    }
}