// archivo: MainActivity.kt
// que hace: actividad principal de la app
// funcion: configura el tema (claro/oscuro) y la navegacion principal

package com.example.gestorgastos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gestorgastos.navigation.NavegacionPrincipal
import com.example.gestorgastos.screens.AppViewModel
import com.example.gestorgastos.ui.theme.GestorGastosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appViewModel: AppViewModel = hiltViewModel()
            val temaGuardado by appViewModel.temaActual.collectAsState()

            // determinar si el tema es oscuro segun lo guardado en preferencias
            val darkTheme = when (temaGuardado) {
                "oscuro" -> true
                "claro" -> false
                else -> isSystemInDarkTheme()
            }

            // aplicar el tema y la navegacion
            GestorGastosTheme(darkTheme = darkTheme) {
                NavegacionPrincipal(
                    isDarkTheme = darkTheme,
                    appViewModel = appViewModel
                )
            }
        }
    }
}