// archivo: HomeScreen.kt
// que hace: pantalla principal de la aplicacion
// muestra: saldo, categorias destacadas, meta de ahorro
// usado en: navegacion principal

package com.example.gestorgastos.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gestorgastos.components.FondoPantalla
import com.example.gestorgastos.components.TituloConX
import com.example.gestorgastos.screens.home.components.*

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onClose: () -> Unit,
    isDarkTheme: Boolean
) {
    val saldo by viewModel.saldo.collectAsState()
    val categorias by viewModel.categoriasHome.collectAsState()
    val ingresosTotales by viewModel.ingresosTotales.collectAsState()
    val metaAhorro by viewModel.metaAhorro.collectAsState()
    val moneda by viewModel.monedaActual.collectAsState()

    FondoPantalla(isDarkTheme = isDarkTheme) {
        Scaffold(
            containerColor = Color.Transparent
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                TituloConX(
                    texto = "ARK FINANZAS",
                    onCloseClick = onClose
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    SaldoCard(
                        saldo = saldo,
                        moneda = moneda
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    SeccionCategorias(
                        categorias = categorias,
                        isDarkTheme = isDarkTheme
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    MetaAhorroCard(
                        ingresosTotales = ingresosTotales,
                        metaAhorro = metaAhorro,
                        saldoActual = saldo,
                        isDarkTheme = isDarkTheme,
                        moneda = moneda
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    DefinirMetaCard(
                        maximoPermitido = saldo.coerceAtLeast(0.0),
                        onMetaGuardada = { nuevaMeta ->
                            viewModel.guardarMetaAhorro(nuevaMeta)
                        },
                        isDarkTheme = isDarkTheme,
                        moneda = moneda
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}