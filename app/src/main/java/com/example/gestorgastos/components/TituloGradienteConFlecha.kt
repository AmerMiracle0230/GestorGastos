package com.example.gestorgastos.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TituloGradienteConFlecha(
    texto: String,
    isDarkTheme: Boolean,
    onBackClick: () -> Unit
) {
    val colores = if (isDarkTheme) listOf(
        Color(0xFF0277BD),
        Color(0xFF29B6F6),
        Color(0xFF81D4FA)
    ) else listOf(
        Color(0xFFE65100),
        Color(0xFFF57C00),
        Color(0xFFFFB74D)
    )

    val shadowColor = if (isDarkTheme)
        Color(0xFF29B6F6).copy(alpha = 0.6f)
    else
        Color(0xFFFFB74D).copy(alpha = 0.6f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Flecha atrás
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Atrás",
                tint = if (isDarkTheme) Color.White else Color(0xFFE65100)
            )
        }

        // Título centrado
        Text(
            text = texto,
            fontSize = 34.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 4.sp,
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle(
                brush = Brush.linearGradient(colors = colores),
                shadow = Shadow(
                    color = shadowColor,
                    offset = Offset(4f, 4f),
                    blurRadius = 10f
                )
            )
        )
    }
}