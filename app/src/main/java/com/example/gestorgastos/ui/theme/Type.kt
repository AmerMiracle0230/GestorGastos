// archivo: Type.kt
// que hace: define la tipografia de la aplicacion
// tamaños: 34sp (titulos grandes), 20sp (titulos de pantalla), 16sp (subtitulos), 14sp (texto normal), 11sp (texto pequeño)

package com.example.gestorgastos.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    // titulos grandes (pantalla home "ARK")
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Black,
        fontSize = 34.sp,
        letterSpacing = 4.sp
    ),

    // titulos de pantalla (lista, stats, config)
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),

    // subtitulos
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),

    // texto normal
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),

    // texto pequeño (fechas, detalles)
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp
    )
)