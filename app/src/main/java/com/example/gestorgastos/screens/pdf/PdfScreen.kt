package com.example.gestorgastos.screens.pdf

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

// Si luego quieres hacer PDFs más bonitos con Jetpack Compose, puedes usar compose-to-pdf . Te permite diseñar el PDF como si fuera una pantalla de Compose
import com.example.gestorgastos.components.TituloSecundario

@Composable
fun PdfScreen(
    viewModel: PdfViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val gastos by viewModel.gastos.collectAsState()
    val total by viewModel.total.collectAsState()
    var generando by remember { mutableStateOf(false) }
    var rutaPDF by remember { mutableStateOf("") }

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
                        text = "RESUMEN A EXPORTAR",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Total gastos: ${gastos.size}")
                    Text("Importe total: ${String.format("%.2f€", total)}")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón generar
            Button(
                onClick = {
                    generando = true
                    val file = viewModel.generarPDF(context)
                    generando = false
                    if (file != null) {
                        rutaPDF = file.absolutePath
                        Toast.makeText(context, "PDF guardado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Error al generar PDF", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !generando && gastos.isNotEmpty()
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
                        Text("Ruta: ${rutaPDF}", fontSize = 12.sp)
                    }
                }
            }

            if (gastos.isEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No hay gastos para exportar",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}