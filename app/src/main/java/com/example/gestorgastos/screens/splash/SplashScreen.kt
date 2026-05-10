// archivo: SplashScreen.kt
// que hace: pantalla de carga inicial
// muestra: logo y loader por 2 segundos, luego navega a home

package com.example.gestorgastos.screens.splash

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onTimeout: () -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(isLoading) {
        if (!isLoading) {
            onTimeout()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "💰",
            fontSize = 80.sp
        )
        Text(
            text = "GESTOR GASTOS",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        CircularProgressIndicator()
    }
}