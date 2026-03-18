package com.example.gestorgastos.screens.config

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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

    var monedaSeleccionada by remember { mutableStateOf(monedaActual) }
    var temaSeleccionado by remember { mutableStateOf(temaActual) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TituloSecundario(
            texto = "CONFIGURACIÓN",
            onBackClick = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Moneda
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
                        onClick = {
                            monedaSeleccionada = moneda
                            viewModel.guardarMoneda(moneda)
                        },
                        label = { Text(moneda) },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tema
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
                        onClick = {
                            temaSeleccionado = valor
                            viewModel.guardarTema(valor)
                        },
                        label = { Text(nombre) },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Info
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
                    Text("ARK - Gestor de Gastos")
                }
            }
        }
    }
}