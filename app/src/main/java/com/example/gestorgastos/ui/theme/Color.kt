// archivo: Color.kt
// que hace: paleta de colores de la aplicacion
// colores: primario (azul), error (rojo suave), success (verde)

package com.example.gestorgastos.ui.theme

import androidx.compose.ui.graphics.Color

// modos claro y oscuro (ambos con azul)
val PrimaryLight = Color(0xFF29B6F6)       // azul principal claro
val PrimaryLightVariant = Color(0xFF4FC3F7) // azul mas claro
val SecondaryLight = Color(0xFF0277BD)     // azul oscuro
val BackgroundLight = Color(0xFFF5F5F5)    // gris fondo
val SurfaceLight = Color(0xFFFFFFFF)       // blanco
val ErrorLight = Color(0xFFFF6B6B)         // rojo suave

val PrimaryDark = Color(0xFF29B6F6)        // azul principal oscuro
val PrimaryDarkVariant = Color(0xFF4FC3F7) // azul mas claro
val SecondaryDark = Color(0xFF0277BD)      // azul oscuro
val BackgroundDark = Color(0xFF121212)     // negro
val SurfaceDark = Color(0xFF1E1E1E)        // gris oscuro
val ErrorDark = Color(0xFFFF8A8A)          // rojo suave oscuro

val Success = Color(0xFF4CAF50)            // verde (ingresos, saldo positivo)