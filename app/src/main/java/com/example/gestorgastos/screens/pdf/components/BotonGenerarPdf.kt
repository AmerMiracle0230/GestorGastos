// archivo: BotonGenerarPdf.kt
// que hace: boton para generar el PDF
// muestra: texto "GENERAR PDF" o un loader mientras se genera
// usado en: PreviewCard

package com.example.gestorgastos.screens.pdf.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BotonGenerarPdf(
    onClick: () -> Unit,
    generando: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        if (generando) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Text(
                text = "📄 GENERAR PDF",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}