// archivo: FondoPantalla.kt
// que hace: fondo con imagen y capa semitransparente
// cambia la imagen y la opacidad según el tema (claro/oscuro)
// usado en: todas las pantallas

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
        // imagen de fondo según el tema
        Image(
            painter = painterResource(
                id = if (isDarkTheme) R.drawable.img else R.drawable.claro
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // capa semitransparente para que se vea mejor el texto
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (isDarkTheme) Color.Black.copy(alpha = 0.6f)
                    else Color.White.copy(alpha = 0.4f)
                )
        )

        // contenido que va dentro (la pantalla real)
        content()
    }
}