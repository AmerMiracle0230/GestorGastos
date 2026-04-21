package com.example.gestorgastos.screens.lista

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gestorgastos.components.FondoPantalla
import com.example.gestorgastos.components.TituloGradienteConFlecha
import com.example.gestorgastos.data.entity.Gasto
import com.example.gestorgastos.data.entity.Ingreso
import com.example.gestorgastos.screens.lista.components.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ListaScreen(
    viewModel: ListaViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onAddClick: () -> Unit
) {
    val gastos by viewModel.gastos.collectAsState()
    val ingresos by viewModel.ingresos.collectAsState()
    val categorias by viewModel.categorias.collectAsState()
    val mapaCategorias = categorias.associateBy { it.id }

    val isDarkTheme = isSystemInDarkTheme()
    var textoBusqueda by remember { mutableStateOf("") }
    var filtroSeleccionado by remember { mutableStateOf<Filtro>(Filtro.TODOS) }

    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    // Combinar movimientos
    val todosMovimientos = remember(gastos, ingresos) {
        val lista = mutableListOf<Pair<String, Any>>()
        gastos.forEach { lista.add("gasto" to it) }
        ingresos.forEach { lista.add("ingreso" to it) }
        lista.sortedByDescending {
            when (val item = it.second) {
                is Gasto -> item.fecha
                is Ingreso -> item.fecha
                else -> 0L
            }
        }
    }

    // Convertir a UI (incluyendo ID)
    val movimientosUI = remember(todosMovimientos, mapaCategorias) {
        todosMovimientos.map { (tipo, movimiento) ->
            val id = if (tipo == "gasto") (movimiento as Gasto).id else (movimiento as Ingreso).id
            val nombre = if (tipo == "gasto") (movimiento as Gasto).nombre else (movimiento as Ingreso).nombre
            val cantidad = if (tipo == "gasto") (movimiento as Gasto).cantidad else (movimiento as Ingreso).cantidad
            val categoriaId = if (tipo == "gasto") (movimiento as Gasto).categoriaId else (movimiento as Ingreso).categoriaId
            val fecha = if (tipo == "gasto") (movimiento as Gasto).fecha else (movimiento as Ingreso).fecha
            val detalle = if (tipo == "gasto") (movimiento as Gasto).detalle else (movimiento as Ingreso).detalle
            val categoria = mapaCategorias[categoriaId]
            MovimientoUI(
                id = id,
                tipo = tipo,
                nombre = nombre,
                cantidad = cantidad,
                categoria = categoria?.nombre ?: "Sin categoría",
                categoriaIcono = categoria?.icono ?: "📦",
                fecha = fecha,
                detalle = detalle
            )
        }
    }

    val totalGastos = gastos.sumOf { it.cantidad }
    val totalIngresos = ingresos.sumOf { it.cantidad }
    val saldo = totalIngresos - totalGastos

    // Filtrar
    val movimientosFiltrados = movimientosUI.filter { movimiento ->
        val matchesBusqueda = textoBusqueda.isEmpty() ||
                movimiento.nombre.contains(textoBusqueda, ignoreCase = true) ||
                movimiento.categoria.contains(textoBusqueda, ignoreCase = true)

        val matchesFiltro = when (val current = filtroSeleccionado) {
            is Filtro.TODOS -> true
            is Filtro.SOLO_GASTOS -> movimiento.tipo == "gasto"
            is Filtro.SOLO_INGRESOS -> movimiento.tipo == "ingreso"
            is Filtro.CATEGORIA_GASTO -> movimiento.tipo == "gasto" && movimiento.categoria == current.nombre
            is Filtro.CATEGORIA_INGRESO -> movimiento.tipo == "ingreso" && movimiento.categoria == current.nombre
        }
        matchesBusqueda && matchesFiltro
    }

    val gastosFiltrados = movimientosFiltrados.filter { it.tipo == "gasto" }
    val ingresosFiltrados = movimientosFiltrados.filter { it.tipo == "ingreso" }

    val categoriasGastos = movimientosUI.filter { it.tipo == "gasto" }.map { it.categoria }.distinct()
    val categoriasIngresos = movimientosUI.filter { it.tipo == "ingreso" }.map { it.categoria }.distinct()

    FondoPantalla(isDarkTheme = isDarkTheme) {
        Scaffold(
            containerColor = Color.Transparent,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onAddClick,
                    containerColor = if (isDarkTheme) Color(0xFF29B6F6) else Color(0xFFF57C00)
                ) {
                    Text("➕", fontSize = 24.sp)
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = innerPadding.calculateTopPadding() - 25.dp,
                        bottom = innerPadding.calculateBottomPadding(),
                        start = 16.dp,
                        end = 16.dp
                    )
            ){
                TituloGradienteConFlecha(
                    texto = "MOVIMIENTOS",
                    isDarkTheme = isDarkTheme,
                    onBackClick = onBack
                )

                Spacer(modifier = Modifier.height(10.dp))

                TarjetaResumen(
                    totalGastos = totalGastos,
                    totalIngresos = totalIngresos,
                    saldo = saldo,
                    isDarkTheme = isDarkTheme
                )

                Spacer(modifier = Modifier.height(16.dp))

                BarraFiltros(
                    textoBusqueda = textoBusqueda,
                    onTextoBusquedaChange = { textoBusqueda = it },
                    filtroSeleccionado = filtroSeleccionado,
                    onFiltroChange = { filtroSeleccionado = it },
                    categoriasGastos = categoriasGastos,
                    categoriasIngresos = categoriasIngresos,
                    isDarkTheme = isDarkTheme
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn {
                    // SECCIÓN GASTOS
                    if (gastosFiltrados.isNotEmpty() && (filtroSeleccionado == Filtro.TODOS || filtroSeleccionado == Filtro.SOLO_GASTOS || filtroSeleccionado is Filtro.CATEGORIA_GASTO)) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("💸 GASTOS", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.width(8.dp))
                                Box(Modifier.weight(1f).height(1.dp).background(Color.Gray.copy(alpha = 0.3f)))
                                Text("${gastosFiltrados.size}", fontSize = 14.sp, color = Color.Gray)
                            }
                        }
                        items(
                            items = gastosFiltrados,
                            key = { it.tipo + it.id } // Usamos tipo + id para asegurar unicidad
                        ) { movimiento ->
                            TarjetaMovimiento(
                                movimiento = movimiento,
                                isDarkTheme = isDarkTheme,
                                esGasto = true,
                                dateFormat = dateFormat,
                                onDelete = {
                                    val gasto = gastos.find { it.id == movimiento.id }
                                    gasto?.let { viewModel.eliminarGasto(it) }
                                }
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }

                    // SECCIÓN INGRESOS
                    if (ingresosFiltrados.isNotEmpty() && (filtroSeleccionado == Filtro.TODOS || filtroSeleccionado == Filtro.SOLO_INGRESOS || filtroSeleccionado is Filtro.CATEGORIA_INGRESO)) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("💰 INGRESOS", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.width(8.dp))
                                Box(Modifier.weight(1f).height(1.dp).background(Color.Gray.copy(alpha = 0.3f)))
                                Text("${ingresosFiltrados.size}", fontSize = 14.sp, color = Color.Gray)
                            }
                        }
                        items(
                            items = ingresosFiltrados,
                            key = { it.tipo + it.id } // Usamos tipo + id
                        ) { movimiento ->
                            TarjetaMovimiento(
                                movimiento = movimiento,
                                isDarkTheme = isDarkTheme,
                                esGasto = false,
                                dateFormat = dateFormat,
                                onDelete = {
                                    val ingreso = ingresos.find { it.id == movimiento.id }
                                    ingreso?.let { viewModel.eliminarIngreso(it) }
                                }
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }

                    if (movimientosFiltrados.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(32.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No hay movimientos", fontSize = 16.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}