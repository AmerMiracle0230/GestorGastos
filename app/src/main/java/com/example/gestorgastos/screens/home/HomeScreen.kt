package com.example.gestorgastos.screens.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gestorgastos.components.TituloPrincipal

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onClose: () -> Unit
) {
    val categorias = listOf(
        CategoriaData("Comida", 45, Color(0xFF4CAF50)),
        CategoriaData("Transporte", 30, Color(0xFF2196F3)),
        CategoriaData("Ocio", 15, Color(0xFFFF9800)),
        CategoriaData("Otros", 10, Color(0xFF9C27B0))
    )

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
            Text(
                text = "RESUMEN DEL MES",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                categorias.forEach { categoria ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        LiquidBubble(
                            percentage = categoria.porcentaje / 100f,
                            color = categoria.color,
                            modifier = Modifier.size(75.dp)
                        )

                        Text(
                            text = categoria.nombre,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

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
                        text = "📊 RESUMEN RÁPIDO",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("💰 Total gastado:")
                        Text("450€", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun LiquidBubble(
    percentage: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "wave")
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "offset"
    )

    val animatedProgress by animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(durationMillis = 1500),
        label = "progress"
    )

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color.copy(alpha = 0.1f))
            .border(2.dp, color.copy(alpha = 0.3f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val fillHeight = height * (1 - animatedProgress)

            val path = Path().apply {
                moveTo(0f, fillHeight)
                for (x in 0..width.toInt()) {
                    val relativeX = x / width
                    val sine = kotlin.math.sin(relativeX * 2 * Math.PI + waveOffset)
                    val y = fillHeight + (sine * 6f).toFloat()
                    lineTo(x.toFloat(), y)
                }
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            drawPath(path, color = color)
        }
        
        Text(
            text = "${(percentage * 100).toInt()}%",
            color = if (animatedProgress > 0.5f) Color.White else color,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

data class CategoriaData(
    val nombre: String,
    val porcentaje: Int,
    val color: Color
)
