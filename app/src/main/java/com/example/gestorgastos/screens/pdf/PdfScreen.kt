// archivo: PdfScreen.kt
// que hace: pantalla de generacion de informe PDF
// muestra: resumen de ingresos, gastos, saldo y boton para generar PDF
// usado en: navegacion principal

package com.example.gestorgastos.screens.pdf

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gestorgastos.components.FondoPantalla
import com.example.gestorgastos.components.TituloGradienteConFlecha
import com.example.gestorgastos.screens.pdf.components.InfoPiePagina
import com.example.gestorgastos.screens.pdf.components.PreviewCard
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PdfScreen(
    viewModel: PdfViewModel = hiltViewModel(),
    onBack: () -> Unit,
    isDarkTheme: Boolean
) {
    val context = LocalContext.current
    val gastos by viewModel.gastos.collectAsState()
    val ingresos by viewModel.ingresos.collectAsState()
    val categorias by viewModel.categorias.collectAsState()
    val moneda by viewModel.monedaActual.collectAsState()
    val mapaCategorias = remember(categorias) { categorias.associateBy { it.id } }

    var generando by remember { mutableStateOf(false) }

    val totalGastos = gastos.sumOf { it.cantidad }
    val totalIngresos = ingresos.sumOf { it.cantidad }
    val saldo = totalIngresos - totalGastos

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val fechaActual = dateFormat.format(Date())

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
                TituloGradienteConFlecha(
                    texto = "REPORTES",
                    onBackClick = onBack
                )

                Spacer(modifier = Modifier.height(24.dp))

                PreviewCard(
                    totalIngresos = totalIngresos,
                    totalGastos = totalGastos,
                    saldo = saldo,
                    fechaActual = fechaActual,
                    isDarkTheme = isDarkTheme,
                    moneda = moneda,
                    onGenerarPdf = {
                        generando = true
                        val file = viewModel.generarPDF(gastos, ingresos, mapaCategorias, moneda)
                        if (file != null) {
                            Toast.makeText(context, "✅ PDF guardado en Descargas", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "❌ Error al generar PDF", Toast.LENGTH_SHORT).show()
                        }
                        generando = false
                    },
                    generando = generando
                )

                Spacer(modifier = Modifier.weight(1f))

                InfoPiePagina(isDarkTheme = isDarkTheme)
            }
        }
    }
}