package com.example.gestorgastos.screens.home.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
    porcentaje: Float,   // 0 - 100 (gastado / objetivo)
    excedido: Boolean,   // 🆕 si se pasó del objetivo
    color: Color,        // color de la categoría
    label: String,
    icono: String
) {

    val infiniteTransition = rememberInfiniteTransition(label = "")

    // Movimiento del agua (onda suave)
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing)
        ), label = ""
    )

    // Burbujas subiendo
    val bubbleOffset by infiniteTransition.animateFloat(
        initialValue = 20f,
        targetValue = -40f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing)
        ), label = ""
    )

    // Animación del nivel
    val progress by animateFloatAsState(
        targetValue = (porcentaje / 100f).coerceIn(0f, 1f),
        animationSpec = tween(1200, easing = FastOutSlowInEasing),
        label = ""
    )

    // Color del agua según si está excedido o no
    val aguaColor = if (excedido) {
        Color(0xFFF44336) // Rojo intenso si se pasó
    } else {
        Color(0xFF4FC3F7) // Azul cielo normal
    }

    val aguaColorFondo = if (excedido) {
        Color(0xFFFFCDD2) // Rojo claro de fondo
    } else {
        Color(0xFF81D4FA) // Azul claro de fondo
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Box(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .drawBehind {

                    val radius = size.minDimension / 2
                    val center = this.center

                    val baseHeight = size.height * (1 - progress)
                    val waveHeight = 3f

                    // Fondo suave (color de la categoría)
                    drawCircle(
                        color = color.copy(alpha = 0.08f),
                        radius = radius,
                        center = center
                    )

                    // Onda del agua
                    val path = Path()
                    for (x in 0..size.width.toInt()) {
                        val y = baseHeight +
                                waveHeight * sin(x * 0.04f + phase)

                        if (x == 0) path.moveTo(x.toFloat(), y)
                        else path.lineTo(x.toFloat(), y)
                    }

                    path.lineTo(size.width, size.height)
                    path.lineTo(0f, size.height)
                    path.close()

                    // Agua (gradiente pro)
                    drawPath(
                        path = path,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                aguaColor,
                                aguaColorFondo
                            )
                        )
                    )

                    // Burbujas animadas
                    drawCircle(
                        color = Color.White.copy(alpha = 0.4f),
                        radius = radius * 0.05f,
                        center = Offset(
                            center.x - radius * 0.2f,
                            baseHeight + bubbleOffset
                        )
                    )

                    drawCircle(
                        color = Color.White.copy(alpha = 0.3f),
                        radius = radius * 0.04f,
                        center = Offset(
                            center.x + radius * 0.15f,
                            baseHeight + bubbleOffset * 0.7f
                        )
                    )

                    // Borde suave (color de la categoría)
                    drawCircle(
                        color = color.copy(alpha = 0.4f),
                        radius = radius,
                        style = Stroke(width = 1.2f)
                    )

                    // Reflejo
                    drawCircle(
                        color = Color.White.copy(alpha = 0.3f),
                        radius = radius * 0.18f,
                        center = Offset(
                            center.x - radius * 0.3f,
                            center.y - radius * 0.3f
                        )
                    )
                }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = icono,
                    fontSize = 34.sp
                )
            }
        }

        // Etiqueta con porcentaje o estado
        Text(
            text = if (excedido) {
                "$label 🔴"
            } else {
                "$label ${"%.0f".format(porcentaje)}%"
            },
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF555555),
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}