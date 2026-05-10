// archivo: BarraInferior.kt
// que hace: barra de navegacion inferior con 5 botones
// botones: Lista, Stats, Inicio, PDF, Ajustes
// el boton Inicio es más grande y destacado

package com.example.gestorgastos.navigation.barraGlobal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BarraInferior(
    navController: NavController,
    isDarkTheme: Boolean
) {
    // ruta actual para saber qué icono está seleccionado
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // colores según el tema
    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = if (isDarkTheme) {
        MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
    } else {
        MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
    }
    val unselectedColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(112.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ),
        containerColor = backgroundColor,
        tonalElevation = 0.dp
    ) {
        // boton lista
        NavigationBarItem(
            icon = {
                Icon(Icons.AutoMirrored.Filled.List, null, modifier = Modifier.size(26.dp))
            },
            label = {
                Text("Lista", fontWeight = FontWeight.Medium, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp))
            },
            selected = currentRoute == "lista",
            onClick = {
                if (currentRoute != "lista") {
                    navController.navigate("lista") {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = primaryColor,
                selectedTextColor = primaryColor,
                unselectedIconColor = unselectedColor,
                unselectedTextColor = unselectedColor,
                indicatorColor = primaryColor.copy(alpha = 0.12f)
            )
        )

        // boton stats
        NavigationBarItem(
            icon = { Icon(Icons.Default.BarChart, null, modifier = Modifier.size(26.dp)) },
            label = { Text("Stats", fontWeight = FontWeight.Medium, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp)) },
            selected = currentRoute == "stats",
            onClick = {
                if (currentRoute != "stats") {
                    navController.navigate("stats") {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = primaryColor,
                selectedTextColor = primaryColor,
                unselectedIconColor = unselectedColor,
                unselectedTextColor = unselectedColor,
                indicatorColor = primaryColor.copy(alpha = 0.12f)
            )
        )

        // boton home (mas grande)
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, null, modifier = Modifier.size(32.dp)) },
            label = { Text("Inicio", fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.padding(top = 4.dp)) },
            selected = currentRoute == "home",
            onClick = {
                if (currentRoute != "home") {
                    navController.popBackStack("home", false)
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = primaryColor,
                selectedTextColor = primaryColor,
                unselectedIconColor = unselectedColor,
                unselectedTextColor = unselectedColor,
                indicatorColor = primaryColor.copy(alpha = 0.12f)
            )
        )

        // boton PDF
        NavigationBarItem(
            icon = { Icon(Icons.Default.PictureAsPdf, null, modifier = Modifier.size(26.dp)) },
            label = { Text("PDF", fontWeight = FontWeight.Medium, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp)) },
            selected = currentRoute == "pdf",
            onClick = {
                if (currentRoute != "pdf") {
                    navController.navigate("pdf") {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = primaryColor,
                selectedTextColor = primaryColor,
                unselectedIconColor = unselectedColor,
                unselectedTextColor = unselectedColor,
                indicatorColor = primaryColor.copy(alpha = 0.12f)
            )
        )

        // boton ajustes (config)
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, null, modifier = Modifier.size(26.dp)) },
            label = { Text("Ajustes", fontWeight = FontWeight.Medium, fontSize = 11.sp, modifier = Modifier.padding(top = 4.dp)) },
            selected = currentRoute == "config",
            onClick = {
                if (currentRoute != "config") {
                    navController.navigate("config") {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = primaryColor,
                selectedTextColor = primaryColor,
                unselectedIconColor = unselectedColor,
                unselectedTextColor = unselectedColor,
                indicatorColor = primaryColor.copy(alpha = 0.12f)
            )
        )
    }
}