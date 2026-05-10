// archivo: InfoPiePagina.kt
// que hace: texto informativo al pie de la pantalla PDF
// usado en: PdfScreen

package com.example.gestorgastos.screens.pdf.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InfoPiePagina(
    isDarkTheme: Boolean,
    modifier: Modifier = Modifier
) {
    val textColor = if (isDarkTheme) Color.White.copy(alpha = 0.6f) else Color.Gray

    Text(
        text = "El PDF se guarda en la carpeta Descargas",
        fontSize = 12.sp,
        color = textColor,
        modifier = modifier.padding(bottom = 16.dp)
    )
}