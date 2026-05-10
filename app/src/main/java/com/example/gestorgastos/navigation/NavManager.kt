// archivo: NavManager.kt
// que hace: gestiona la navegacion entre pantallas
// rutas: splash, home, lista, add, stats, pdf, config

package com.example.gestorgastos.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gestorgastos.screens.AppViewModel
import com.example.gestorgastos.screens.add.AddScreen
import com.example.gestorgastos.screens.config.ConfigScreen
import com.example.gestorgastos.screens.home.HomeScreen
import com.example.gestorgastos.screens.lista.ListaScreen
import com.example.gestorgastos.screens.pdf.PdfScreen
import com.example.gestorgastos.screens.splash.SplashScreen
import com.example.gestorgastos.screens.stats.StatsScreen

@Composable
fun NavManager(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    appViewModel: AppViewModel
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {
        // splash screen (pantalla de carga)
        composable("splash") {
            SplashScreen(
                onTimeout = {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        // pantalla de inicio
        composable("home") {
            HomeScreen(
                onClose = { (context as? Activity)?.finish() },
                isDarkTheme = isDarkTheme
            )
        }

        // pantalla de lista de movimientos
        composable("lista") {
            ListaScreen(
                onBack = { navController.popBackStack() },
                onAddClick = { navController.navigate("add") },
                isDarkTheme = isDarkTheme
            )
        }

        // pantalla para añadir gastos/ingresos
        composable("add") {
            AddScreen(
                onBack = { navController.popBackStack() },
                isDarkTheme = isDarkTheme
            )
        }

        // pantalla de estadisticas
        composable("stats") {
            StatsScreen(
                onBack = { navController.popBackStack() },
                isDarkTheme = isDarkTheme
            )
        }

        // pantalla de generacion de pdf
        composable("pdf") {
            PdfScreen(
                onBack = { navController.popBackStack() },
                isDarkTheme = isDarkTheme
            )
        }

        // pantalla de configuracion
        composable("config") {
            ConfigScreen(
                onBack = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = false }
                        launchSingleTop = true
                    }
                },
                isDarkTheme = isDarkTheme,
                appViewModel = appViewModel
            )
        }
    }
}