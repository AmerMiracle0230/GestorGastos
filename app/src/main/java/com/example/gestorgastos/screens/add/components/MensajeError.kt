// archivo: MensajeError.kt
// que hace: muestra un mensaje de error en rojo
// usado en: AddScreen (validaciones)

package com.example.gestorgastos.screens.add.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun MensajeError(
    mensaje: String,
    modifier: Modifier = Modifier
) {
    if (mensaje.isNotEmpty()) {
        Text(
            text = mensaje,
            color = MaterialTheme.colorScheme.error,  // rojo del tema
            fontSize = 14.sp,
            modifier = modifier
        )
    }
}