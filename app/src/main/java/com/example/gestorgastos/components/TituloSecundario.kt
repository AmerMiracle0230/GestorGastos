package com.example.gestorgastos.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TituloSecundario(
    texto: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color(0xFF2196F3),
        tonalElevation = 0.dp
    ) {
        // Usamos Row con pesos para centrar correctamente
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Flecha a la izquierda (tamaño fijo)
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Atrás",
                    tint = Color.White
                )
            }

            // Título centrado (ocupa el espacio disponible)
            Text(
                text = texto,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            // Espacio vacío del mismo tamaño que la flecha para equilibrar
            Box(modifier = Modifier.size(24.dp))
        }
    }
}