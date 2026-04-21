package com.example.gestorgastos.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.gestorgastos.R

@Composable
fun FondoPantalla(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo según tema
        Image(
            painter = painterResource(
                id = if (isDarkTheme) R.drawable.img else R.drawable.claro
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Capa semitransparente para legibilidad
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (isDarkTheme) Color.Black.copy(alpha = 0.6f)
                    else Color.White.copy(alpha = 0.4f)
                )
        )

        // Contenido
        content()
    }
}