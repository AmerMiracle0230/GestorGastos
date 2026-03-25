package com.example.gestorgastos.screens.pdf

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gestorgastos.components.TituloSecundario

@Composable
fun PdfScreen(
    viewModel: PdfViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val gastos by viewModel.gastos.collectAsState()
    val ingresos by viewModel.ingresos.collectAsState()
    val categorias by viewModel.categorias.collectAsState()
    var generando by remember { mutableStateOf(false) }
    var rutaPDF by remember { mutableStateOf("") }

    val totalGastos = gastos.sumOf { it.cantidad }
    val totalIngresos = ingresos.sumOf { it.cantidad }
    val saldo = totalIngresos - totalGastos

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TituloSecundario(
            texto = "GENERAR PDF",
            onBackClick = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
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
                    Text(
                        text = "📊 RESUMEN A EXPORTAR",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("💸 Total gastos:")
                        Text("${String.format("%.2f", totalGastos)}€")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("💰 Total ingresos:")
                        Text("${String.format("%.2f", totalIngresos)}€")
                    }
                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("💵 Saldo:")
                        Text(
                            "${String.format("%.2f", saldo)}€",
                            color = if (saldo >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("📝 Total movimientos: ${gastos.size + ingresos.size}")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón generar
            Button(
                onClick = {
                    generando = true
                    val file = viewModel.generarPDF(
                        context = context,
                        gastos = gastos,
                        ingresos = ingresos,
                        categorias = categorias
                    )
                    generando = false
                    if (file != null) {
                        rutaPDF = file.absolutePath
                        Toast.makeText(context, "PDF guardado en Descargas", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Error al generar PDF", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !generando && (gastos.isNotEmpty() || ingresos.isNotEmpty())
            ) {
                if (generando) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    Text("📄 GENERAR PDF")
                }
            }

            if (rutaPDF.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
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
                        Text("✅ PDF GENERADO", fontWeight = FontWeight.Bold)
                        Text("Guardado en: ${rutaPDF.substringAfterLast("/")}", fontSize = 12.sp)
                    }
                }
            }

            if (gastos.isEmpty() && ingresos.isEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No hay movimientos para exportar",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}