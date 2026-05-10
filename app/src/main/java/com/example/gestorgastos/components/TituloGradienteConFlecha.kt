// archivo: TituloGradienteConFlecha.kt
// que hace: título centrado con gradiente y flecha atras a la izquierda
// usado en: ListaScreen, StatsScreen, PdfScreen, ConfigScreen

package com.example.gestorgastos.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TituloGradienteConFlecha(
    texto: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // color principal del tema
    val primary = MaterialTheme.colorScheme.primary
    val primaryVariant = primary.copy(alpha = 0.7f)
    val primaryLight = primary.copy(alpha = 0.5f)

    // colores para el gradiente
    val colores = listOf(primary, primaryVariant, primaryLight)

    // sombra del texto
    val shadowColor = primary.copy(alpha = 0.6f)

    Box(modifier = modifier.fillMaxWidth()) {
        // flecha atras a la izquierda
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Atras",
                tint = primary
            )
        }

        // titulo centrado con gradiente
        Text(
            text = texto,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
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