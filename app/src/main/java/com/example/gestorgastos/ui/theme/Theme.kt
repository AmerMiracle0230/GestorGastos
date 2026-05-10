// archivo: Theme.kt
// que hace: define el tema de la aplicacion (claro/oscuro)
// usa colores azules para ambos modos

package com.example.gestorgastos.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// esquema para modo claro
private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = Color.White,
    primaryContainer = PrimaryLightVariant,
    onPrimaryContainer = Color.Black,
    secondary = SecondaryLight,
    onSecondary = Color.White,
    background = BackgroundLight,
    onBackground = Color.Black,
    surface = SurfaceLight,
    onSurface = Color.Black,
    error = ErrorLight,
    onError = Color.White
)

// esquema para modo oscuro
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = Color.White,
    primaryContainer = PrimaryDarkVariant,
    onPrimaryContainer = Color.Black,
    secondary = SecondaryDark,
    onSecondary = Color.White,
    background = BackgroundDark,
    onBackground = Color.White,
    surface = SurfaceDark,
    onSurface = Color.White,
    error = ErrorDark,
    onError = Color.Black
)

@Composable
fun GestorGastosTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}