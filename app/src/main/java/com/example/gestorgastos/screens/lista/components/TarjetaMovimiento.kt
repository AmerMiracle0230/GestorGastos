package com.example.gestorgastos.screens.lista.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarjetaMovimiento(
    movimiento: MovimientoUI,
    isDarkTheme: Boolean,
    esGasto: Boolean,
    dateFormat: SimpleDateFormat,
    onDelete: () -> Unit
) {
    // Configuración profesional del Swipe
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            // Solo elimina si el deslizamiento se ha completado hacia la izquierda (EndToStart)
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                true
            } else {
                false
            }
        },
        // Establecemos que el umbral sea exactamente el 50% del ancho
        positionalThreshold = { totalDistance -> totalDistance * 0.5f }
    )

    val cardColor = if (isDarkTheme) Color(0xFF151B54) else Color.White
    val textColor = if (isDarkTheme) Color.White else Color(0xFF1A1A2E)
    val textSecondaryColor = if (isDarkTheme) Color(0xFFB0B0B0) else Color(0xFF666666)
    val gastoColor = if (isDarkTheme) Color(0xFFFF6B6B) else Color(0xFFDC3545)
    val ingresoColor = if (isDarkTheme) Color(0xFF4ADE80) else Color(0xFF28A745)

    val iconBackground = if (esGasto)
        if (isDarkTheme) Color(0xFFFF6B6B).copy(alpha = 0.15f) else Color(0xFFFFEBEE)
    else
        if (isDarkTheme) Color(0xFF4ADE80).copy(alpha = 0.15f) else Color(0xFFE8F5E9)

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false, // Solo permitir borrar deslizando a la izquierda
        backgroundContent = {
            // El color se vuelve rojo intenso solo cuando se supera el 50% (threshold reached)
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> Color(0xDDFA4D4D) // Rojo fuerte al pasar el 50%
                    else -> Color(0xFFFDA39D).copy(alpha = 0.7f) // Rojo suave inicial
                }, label = "dismissColor"
            )
            
            // El icono crece solo cuando se supera el 50%
            val iconScale by animateFloatAsState(
                if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) 1.3f else 1.0f,
                label = "iconScale"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Borrar",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(end = 32.dp)
                        .scale(iconScale)
                )
            }
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(44.dp),
                        shape = RoundedCornerShape(22.dp),
                        color = iconBackground
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = movimiento.categoriaIcono, fontSize = 22.sp)
                        }
                    }
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Column {
                        Text(
                            text = movimiento.nombre,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = textColor
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = movimiento.categoria,
                                fontSize = 12.sp,
                                color = textSecondaryColor
                            )
                            Text(
                                text = " • ",
                                fontSize = 12.sp,
                                color = textSecondaryColor.copy(alpha = 0.5f)
                            )
                            Text(
                                text = dateFormat.format(Date(movimiento.fecha)),
                                fontSize = 12.sp,
                                color = textSecondaryColor
                            )
                        }
                        if (movimiento.detalle.isNotBlank()) {
                            Text(
                                text = movimiento.detalle,
                                fontSize = 12.sp,
                                color = textSecondaryColor
                            )
                        }
                    }
                }
                
                Text(
                    text = "${if (esGasto) "-" else "+"}${String.format("%.2f", movimiento.cantidad)}€",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 17.sp,
                    color = if (esGasto) gastoColor else ingresoColor
                )
            }
        }
    }
}