package com.example.gestorgastos.screens.lista



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gestorgastos.components.TituloSecundario

@Composable
fun ListaScreen(
    viewModel: ListaViewModel = hiltViewModel(),
    onBack: () -> Unit,
    onAddClick: () -> Unit  // ← NUEVO: Para navegar a añadir gasto
) {
    val gastos by viewModel.gastos.collectAsState()
    val total = remember(gastos) {
        gastos.sumOf { it.cantidad }
    }

    Scaffold(
        topBar = {
            TituloSecundario(
                texto = "LISTA DE GASTOS",
                onBackClick = onBack
            )
        },
        floatingActionButton = {
            // 🟢 BOTÓN FLOTANTE PARA AÑADIR
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text(
                    text = "➕",
                    fontSize = 24.sp
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End  // Abajo a la derecha
    ) { innerPadding ->
        // Contenido con el padding del Scaffold
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Total
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "TOTAL:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "${total}€",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de gastos
            LazyColumn {
                items(gastos) { gasto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = gasto.descripcion,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "🏷️ ${gasto.categoria}",
                                    fontSize = 12.sp
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "${gasto.cantidad}€",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                IconButton(
                                    onClick = { viewModel.eliminarGasto(gasto) }
                                ) {
                                    Text("🗑️", fontSize = 20.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}