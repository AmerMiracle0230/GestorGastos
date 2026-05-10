// archivo: SeccionCategorias.kt
// que hace: seccion que muestra las categorias destacadas con burbujas animadas
// usadas en: HomeScreen

package com.example.gestorgastos.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SeccionCategorias(
    categorias: List<com.example.gestorgastos.screens.home.CategoriaConEstado>,
    isDarkTheme: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // título decorado con líneas laterales
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // linea izquierda
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(
                        if (isDarkTheme) Color.White.copy(alpha = 0.2f)
                        else Color.Black.copy(alpha = 0.1f)
                    )
            )

            // texto con fondo suave
            Surface(
                modifier = Modifier.padding(horizontal = 12.dp),
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            ) {
                Text(
                    text = "📊 ESTADO POR CATEGORIAS",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            // linea derecha
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(
                        if (isDarkTheme) Color.White.copy(alpha = 0.2f)
                        else Color.Black.copy(alpha = 0.1f)
                    )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // burbujas en fila (sin tarjeta)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (categorias.isEmpty()) {
                Text(
                    "No hay categorias con gastos",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                categorias.forEach { item ->  // sin usar index
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        LiquidBubble(
                            porcentaje = item.porcentaje,
                            excedido = item.excedido,
                            color = Color(item.categoria.color),
                            label = item.categoria.nombre,
                            icono = item.categoria.icono,
                            isDarkTheme = isDarkTheme
                        )
                    }
                }
            }
        }
    }
}