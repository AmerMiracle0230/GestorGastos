package com.example.gestorgastos.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gestorgastos.screens.add.AddScreen
import com.example.gestorgastos.screens.config.ConfigScreen
import com.example.gestorgastos.screens.home.HomeScreen
import com.example.gestorgastos.screens.lista.ListaScreen
import com.example.gestorgastos.screens.pdf.PdfScreen
import com.example.gestorgastos.screens.splash.SplashScreen
import com.example.gestorgastos.screens.stats.StatsScreen
import com.example.gestorgastos.screens.vermas.VerMasScreen

@Composable
fun NavManager(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {
        composable("splash") {
            SplashScreen(
                onTimeout = {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            HomeScreen(
                onClose = {
                    (context as? Activity)?.finish()
                },
                onVerMasClick = {
                    navController.navigate("vermas")
                }
            )
        }

        composable("vermas") {
            VerMasScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable("lista") {
            ListaScreen(
                onBack = { navController.popBackStack() },
                onAddClick = { navController.navigate("add") }  // ← NUEVO
            )
        }

        composable("add") {
            AddScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("stats") {
            StatsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("pdf") {
            PdfScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("config") {
            ConfigScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}