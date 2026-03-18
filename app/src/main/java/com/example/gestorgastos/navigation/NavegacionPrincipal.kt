package com.example.gestorgastos.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gestorgastos.navigation.barraGlobal.BarraInferior


@Composable
fun NavegacionPrincipal() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        // 👇 ELIMINA ESTE BLOQUE COMPLETO
        // topBar = { ... },

        bottomBar = {
            if (currentRoute != "splash") {
                BarraInferior(navController = navController)
            }
        }
    ) { innerPadding ->
        NavManager(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}