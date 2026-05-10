// archivo: LiquidBubble.kt
// que hace: burbuja animada que muestra el porcentaje de gasto por categoria
// el color del agua cambia según el tema (más claro en oscuro)
// usado en: SeccionCategorias (HomeScreen)

package com.example.gestorgastos.screens.home.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sin

@Composable
fun LiquidBubble(
    porcentaje: Float,        // porcentaje gastado (0-100)
    excedido: Boolean,        // sí se pasó del objetivo
    color: Color,             // color de la categoria
    label: String,            // nombre de la categoria
    icono: String,            // emoji de la categoria
    isDarkTheme: Boolean      // tema oscuro o claro
) {
    // animacion del movimiento del agua
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(animation = tween(6000, easing = LinearEasing)),
        label = ""
    )

    // animacion de las burbujas subiendo
    val bubbleOffset by infiniteTransition.animateFloat(
        initialValue = 20f,
        targetValue = -40f,
        animationSpec = infiniteRepeatable(animation = tween(5000, easing = LinearEasing)),
        label = ""
    )

    // nivel del agua animado
    val progress by animateFloatAsState(
        targetValue = (porcentaje / 100f).coerceIn(0f, 1f),
        animationSpec = tween(1200, easing = FastOutSlowInEasing),
        label = ""
    )

    // color del agua según tema y si está excedido
    val aguaColor = if (excedido) {
        if (isDarkTheme) Color(0xFFFF8A8A) else MaterialTheme.colorScheme.error
    } else {
        if (isDarkTheme) Color(0xFF4FC3F7) else MaterialTheme.colorScheme.primary
    }

    // color del fondo del agua
    val aguaColorFondo = if (excedido) {
        if (isDarkTheme) Color(0xFFFF8A8A).copy(alpha = 0.5f) else MaterialTheme.colorScheme.error.copy(alpha = 0.3f)
    } else {
        if (isDarkTheme) Color(0xFF4FC3F7).copy(alpha = 0.5f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
    }

    // color del texto debajo de la burbuja
    val textColor = if (isDarkTheme) Color.White else Color(0xFF555555)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // dibujo del círculo con el agua animada
        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .drawBehind {
                    val radius = size.minDimension / 2
                    val center = this.center
                    val baseHeight = size.height * (1 - progress)
                    val waveHeight = 3f

                    // fondo suave (color de la categoria)
                    drawCircle(color = color.copy(alpha = 0.08f), radius = radius, center = center)

                    // trayectoria de la onda del agua
                    val path = Path()
                    for (x in 0..size.width.toInt()) {
                        val y = baseHeight + waveHeight * sin(x * 0.04f + phase)
                        if (x == 0) path.moveTo(x.toFloat(), y)
                        else path.lineTo(x.toFloat(), y)
                    }
                    path.lineTo(size.width, size.height)
                    path.lineTo(0f, size.height)
                    path.close()

                    // dibujo del agua con gradiente
                    drawPath(
                        path = path,
                        brush = Brush.verticalGradient(colors = listOf(aguaColor, aguaColorFondo))
                    )

                    // burbujas animadas
                    drawCircle(
                        color = Color.White.copy(alpha = 0.4f),
                        radius = radius * 0.05f,
                        center = Offset(center.x - radius * 0.2f, baseHeight + bubbleOffset)
                    )
                    drawCircle(
                        color = Color.White.copy(alpha = 0.3f),
                        radius = radius * 0.04f,
                        center = Offset(center.x + radius * 0.15f, baseHeight + bubbleOffset * 0.7f)
                    )

                    // borde suave
                    drawCircle(color = color.copy(alpha = 0.4f), radius = radius, style = Stroke(width = 1.2f))

                    // reflejo
                    drawCircle(
                        color = Color.White.copy(alpha = 0.3f),
                        radius = radius * 0.18f,
                        center = Offset(center.x - radius * 0.3f, center.y - radius * 0.3f)
                    )
                }
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = icono, fontSize = 34.sp)
            }
        }

        // texto con nombre y porcentaje
        Text(
            text = if (excedido) "$label 🔴" else "$label ${"%.0f".format(porcentaje)}%",
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = textColor,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}