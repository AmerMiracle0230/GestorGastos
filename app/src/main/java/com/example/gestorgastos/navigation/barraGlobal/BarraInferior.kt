package com.example.gestorgastos.navigation.barraGlobal

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BarraInferior(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        // LISTA (📋)
        NavigationBarItem(
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Lista") },
            label = { Text("Lista") },
            selected = currentRoute == "lista",
            onClick = {
                navController.navigate("lista") {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        // ESTADÍSTICAS (📊)
        NavigationBarItem(
            icon = { Icon(Icons.Default.BarChart, contentDescription = "Estadísticas") },
            label = { Text("Stats") },
            selected = currentRoute == "stats",
            onClick = {
                navController.navigate("stats") {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        // HOME (🏠)
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = currentRoute == "home",
            onClick = {
                // VUELVE A HOME AUNQUE YA ESTÉS ALLÍ
                navController.popBackStack("home", false)
            }
        )

        // PDF (📄)
        NavigationBarItem(
            icon = { Icon(Icons.Default.PictureAsPdf, contentDescription = "PDF") },
            label = { Text("PDF") },
            selected = currentRoute == "pdf",
            onClick = {
                navController.navigate("pdf") {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )

        // CONFIGURACIÓN (⚙️)
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Ajustes") },
            label = { Text("Ajustes") },
            selected = currentRoute == "config",
            onClick = {
                navController.navigate("config") {
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}
