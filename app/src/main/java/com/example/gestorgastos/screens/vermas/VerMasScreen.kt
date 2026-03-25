package com.example.gestorgastos.screens.vermas

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
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun VerMasScreen(
    viewModel: VerMasViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val gastos by viewModel.gastos.collectAsState()
    val ingresos by viewModel.ingresos.collectAsState()
    val categorias by viewModel.categorias.collectAsState()
    val mapaCategorias = categorias.associateBy { it.id }

    // Agrupar gastos por categoría
    val gastosPorCategoria = gastos.groupBy { it.categoriaId }
        .mapValues { (_, lista) -> lista.sumOf { it.cantidad } }

    // Agrupar ingresos por categoría
    val ingresosPorCategoria = ingresos.groupBy { it.categoriaId }
        .mapValues { (_, lista) -> lista.sumOf { it.cantidad } }

    val totalGastos = gastos.sumOf { it.cantidad }
    val totalIngresos = ingresos.sumOf { it.cantidad }

    // Formateador de fecha para el mes actual
    val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    val mesActual = dateFormat.format(Date())

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TituloSecundario(
            texto = "TODAS LAS CATEGORÍAS",
            onBackClick = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Mes actual
            Text(
                text = mesActual.uppercase(),
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Lista de categorías
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(categorias) { categoria ->
                    val totalGasto = gastosPorCategoria[categoria.id] ?: 0.0
                    val totalIngreso = ingresosPorCategoria[categoria.id] ?: 0.0
                    val total = totalGasto + totalIngreso

                    if (total > 0) {
                        CategoriaDetalleItem(
                            icono = categoria.icono,
                            nombre = categoria.nombre,
                            totalGasto = totalGasto,
                            totalIngreso = totalIngreso,
                            total = total,
                            totalGastosGlobal = totalGastos,
                            totalIngresosGlobal = totalIngresos,
                            color = Color(categoria.color)
                        )
                    } else {
                        CategoriaVaciaItem(
                            icono = categoria.icono,
                            nombre = categoria.nombre
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoriaDetalleItem(
    icono: String,
    nombre: String,
    totalGasto: Double,
    totalIngreso: Double,
    total: Double,
    totalGastosGlobal: Double,
    totalIngresosGlobal: Double,
    color: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Cabecera
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = icono, fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = nombre,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = String.format("%.2f€", total),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (totalGasto > totalIngreso) Color(0xFFF44336) else Color(0xFF4CAF50)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Detalle de gastos
            if (totalGasto > 0) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("💸 Gastos:", fontSize = 12.sp)
                    Text(
                        String.format("%.2f€", totalGasto),
                        fontSize = 12.sp,
                        color = Color(0xFFF44336)
                    )
                }
                // Barra de gastos
                val porcentajeGasto = if (totalGastosGlobal > 0) (totalGasto / totalGastosGlobal) else 0.0
                LinearProgressIndicator(
                    progress = porcentajeGasto.toFloat(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp),
                    color = Color(0xFFF44336),
                    trackColor = Color(0xFFF44336).copy(alpha = 0.2f)
                )
            }

            // Detalle de ingresos
            if (totalIngreso > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("💰 Ingresos:", fontSize = 12.sp)
                    Text(
                        String.format("%.2f€", totalIngreso),
                        fontSize = 12.sp,
                        color = Color(0xFF4CAF50)
                    )
                }
                // Barra de ingresos
                val porcentajeIngreso = if (totalIngresosGlobal > 0) (totalIngreso / totalIngresosGlobal) else 0.0
                LinearProgressIndicator(
                    progress = porcentajeIngreso.toFloat(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp),
                    color = Color(0xFF4CAF50),
                    trackColor = Color(0xFF4CAF50).copy(alpha = 0.2f)
                )
            }
        }
    }
}

@Composable
fun CategoriaVaciaItem(
    icono: String,
    nombre: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icono, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = nombre,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "0€",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}