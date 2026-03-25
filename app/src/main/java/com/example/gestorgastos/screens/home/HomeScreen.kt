package com.example.gestorgastos.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gestorgastos.components.TituloPrincipal
import com.example.gestorgastos.screens.home.components.LiquidBubble

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onClose: () -> Unit,
    onVerMasClick: () -> Unit
) {
    val saldo by viewModel.saldo.collectAsState()
    val categorias by viewModel.categoriasHome.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TituloPrincipal(
            texto = "ARK",
            onCloseClick = onClose
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // SALDO
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (saldo >= 0)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "💰 SALDO:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Text(
                        text = String.format("%.2f€", saldo),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = if (saldo >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // BURBUJAS (con objetivo y color dinámico)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                categorias.forEach { item ->
                    LiquidBubble(
                        porcentaje = item.porcentaje,
                        excedido = item.excedido,
                        color = Color(item.categoria.color),
                        label = item.categoria.nombre,
                        icono = item.categoria.icono
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // BOTÓN VER MÁS
            Button(
                onClick = onVerMasClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🔽 VER MÁS CATEGORÍAS")
            }
        }
    }
}