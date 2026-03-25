package com.example.gestorgastos.screens.lista

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
import com.example.gestorgastos.components.TituloSecundario
import com.example.gestorgastos.data.entity.Gasto
import com.example.gestorgastos.data.entity.Ingreso
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

    // Combinar y ordenar
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

    val totalGastos = gastos.sumOf { it.cantidad }
    val totalIngresos = ingresos.sumOf { it.cantidad }
    val saldo = totalIngresos - totalGastos
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    Scaffold(
        topBar = {
            TituloSecundario(
                texto = "MOVIMIENTOS",
                onBackClick = onBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text("➕", fontSize = 24.sp)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Resumen
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("💸 GASTOS:", fontWeight = FontWeight.Bold)
                        Text("${String.format("%.2f", totalGastos)}€", color = MaterialTheme.colorScheme.error)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("💰 INGRESOS:", fontWeight = FontWeight.Bold)
                        Text("${String.format("%.2f", totalIngresos)}€", color = MaterialTheme.colorScheme.primary)
                    }
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("💵 SALDO:", fontWeight = FontWeight.Bold)
                        Text(
                            "${String.format("%.2f", saldo)}€",
                            color = if (saldo >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista
            LazyColumn {
                items(todosMovimientos) { item ->
                    val tipo = item.first
                    val movimiento = item.second
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (tipo == "gasto") "💸" else "💰",
                                    fontSize = 24.sp
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = if (tipo == "gasto") (movimiento as Gasto).nombre
                                        else (movimiento as Ingreso).nombre,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    val catId = if (tipo == "gasto") (movimiento as Gasto).categoriaId
                                    else (movimiento as Ingreso).categoriaId
                                    val categoria = mapaCategorias[catId]
                                    if (categoria != null) {
                                        Text(
                                            text = "${categoria.icono} ${categoria.nombre}",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    val detalle = if (tipo == "gasto") (movimiento as Gasto).detalle
                                    else (movimiento as Ingreso).detalle
                                    if (detalle.isNotBlank()) {
                                        Text(
                                            text = detalle,
                                            fontSize = 11.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    val fecha = if (tipo == "gasto") (movimiento as Gasto).fecha
                                    else (movimiento as Ingreso).fecha
                                    Text(
                                        text = dateFormat.format(Date(fecha)),
                                        fontSize = 10.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                            Text(
                                text = "${String.format("%.2f", if (tipo == "gasto") (movimiento as Gasto).cantidad else (movimiento as Ingreso).cantidad)}€",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = if (tipo == "gasto") Color(0xFFF44336) else Color(0xFF4CAF50)
                            )
                        }
                    }
                }
            }
        }
    }
}