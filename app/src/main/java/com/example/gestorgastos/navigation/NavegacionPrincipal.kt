// archivo: NavegacionPrincipal.kt
// que hace: estructura principal de la app con Scaffold
// contiene la barra inferior y el NavManager

package com.example.gestorgastos.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gestorgastos.navigation.barraGlobal.BarraInferior
import com.example.gestorgastos.screens.AppViewModel

@Composable
fun NavegacionPrincipal(
    isDarkTheme: Boolean,
    appViewModel: AppViewModel
) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != "splash") {
                BarraInferior(
                    navController = navController,
                    isDarkTheme = isDarkTheme
                )
            }
        }
    ) { innerPadding ->
        NavManager(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            isDarkTheme = isDarkTheme,
            appViewModel = appViewModel
        )
    }
}