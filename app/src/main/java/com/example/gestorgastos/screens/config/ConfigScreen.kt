// archivo: ConfigScreen.kt
// que hace: pantalla de configuracion de la aplicacion
// pestañas: general, categorias, metas

package com.example.gestorgastos.screens.config

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gestorgastos.components.FondoPantalla
import com.example.gestorgastos.components.TituloGradienteConFlecha
import com.example.gestorgastos.screens.AppViewModel
import com.example.gestorgastos.screens.config.components.*
import com.example.gestorgastos.data.entity.Categoria

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigScreen(
    viewModel: ConfigViewModel = hiltViewModel(),
    appViewModel: AppViewModel,
    onBack: () -> Unit,
    isDarkTheme: Boolean
) {
    val monedaActual by appViewModel.monedaActual.collectAsState()
    val todasCategorias by viewModel.todasCategorias.collectAsState()
    val categoriasSeleccionadas by viewModel.categoriasDestacadasIds.collectAsState()

    var monedaSeleccionada by remember { mutableStateOf(monedaActual) }
    var objetivosEditados by remember { mutableStateOf(mutableMapOf<Int, String>()) }
    var tabIndex by remember { mutableIntStateOf(0) }

    var mostrarDialogCategoria by remember { mutableStateOf(false) }
    var categoriaEditando by remember { mutableStateOf<Categoria?>(null) }
    var mostrarConfirmarEliminar by remember { mutableStateOf(false) }
    var categoriaAEliminar by remember { mutableStateOf<Categoria?>(null) }

    val primaryColor = MaterialTheme.colorScheme.primary

    // guardar moneda automaticamente cuando cambia
    LaunchedEffect(monedaSeleccionada) {
        appViewModel.guardarMoneda(monedaSeleccionada)
    }

    FondoPantalla(isDarkTheme = isDarkTheme) {
        Scaffold(
            containerColor = Color.Transparent,
            floatingActionButton = {
                if (tabIndex == 1) {
                    FloatingActionButton(
                        onClick = {
                            categoriaEditando = null
                            mostrarDialogCategoria = true
                        },
                        containerColor = primaryColor,
                        shape = androidx.compose.foundation.shape.CircleShape
                    ) {
                        Text("➕", fontSize = 24.sp)
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = innerPadding.calculateTopPadding() - 25.dp,
                        bottom = innerPadding.calculateBottomPadding(),
                        start = 16.dp,
                        end = 16.dp
                    )
            ) {
                TituloGradienteConFlecha(
                    texto = "CONFIGURACION",
                    onBackClick = onBack
                )

                Spacer(modifier = Modifier.height(16.dp))

                TabRow(
                    selectedTabIndex = tabIndex,
                    containerColor = Color.Transparent,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                            color = primaryColor,
                            height = 3.dp
                        )
                    }
                ) {
                    Tab(
                        selected = tabIndex == 0,
                        onClick = { tabIndex = 0 },
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text("⚙️", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("GENERAL", fontSize = 12.sp, maxLines = 1)
                            }
                        }
                    )
                    Tab(
                        selected = tabIndex == 1,
                        onClick = { tabIndex = 1 },
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text("📂", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("CATEGORIAS", fontSize = 12.sp, maxLines = 1)
                            }
                        }
                    )
                    Tab(
                        selected = tabIndex == 2,
                        onClick = { tabIndex = 2 },
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text("🎯", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("METAS", fontSize = 12.sp, maxLines = 1)
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                when (tabIndex) {
                    0 -> PestanaGeneral(
                        monedaSeleccionada = monedaSeleccionada,
                        onMonedaChange = { monedaSeleccionada = it },
                        temaActual = appViewModel.temaActual.collectAsState().value,
                        onTemaChange = { nuevoTema ->
                            appViewModel.guardarTema(nuevoTema)
                        },
                        isDarkTheme = isDarkTheme
                    )
                    1 -> PestanaCategorias(
                        categorias = todasCategorias,
                        categoriasSeleccionadas = categoriasSeleccionadas,
                        onCategoriaCheckedChange = { id, checked ->
                            if (checked) {
                                viewModel.guardarCategoriasDestacadas(categoriasSeleccionadas + id)
                            } else {
                                viewModel.guardarCategoriasDestacadas(categoriasSeleccionadas - id)
                            }
                        },
                        onEditarCategoria = { categoria ->
                            categoriaEditando = categoria
                            mostrarDialogCategoria = true
                        },
                        onEliminarCategoria = { categoria ->
                            categoriaAEliminar = categoria
                            mostrarConfirmarEliminar = true
                        },
                        isDarkTheme = isDarkTheme
                    )
                    2 -> PestanaMetas(
                        categorias = todasCategorias,
                        objetivosEditados = objetivosEditados,
                        onObjetivoChange = { id, valor ->
                            objetivosEditados = objetivosEditados.toMutableMap().apply { put(id, valor) }
                            viewModel.actualizarObjetivo(id, valor.toDoubleOrNull() ?: 0.0)
                        },
                        onGuardarObjetivos = {},
                        isDarkTheme = isDarkTheme,
                        primaryColor = MaterialTheme.colorScheme.primary,
                        moneda = monedaActual
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    if (mostrarDialogCategoria) {
        DialogCategoria(
            categoria = categoriaEditando,
            onDismiss = { mostrarDialogCategoria = false },
            onGuardar = { nombre, icono, color, tipo, objetivo ->
                if (categoriaEditando == null) {
                    viewModel.crearCategoria(nombre, icono, color, tipo, objetivo)
                } else {
                    categoriaEditando?.let {
                        viewModel.editarCategoria(it, nombre, icono, color, tipo, objetivo)
                    }
                }
                mostrarDialogCategoria = false
            }
        )
    }

    if (mostrarConfirmarEliminar && categoriaAEliminar != null) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmarEliminar = false },
            title = { Text("Eliminar categoria") },
            text = { Text("Estas seguro de que quieres eliminar \"${categoriaAEliminar?.nombre}\"?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        categoriaAEliminar?.let { viewModel.eliminarCategoria(it) }
                        mostrarConfirmarEliminar = false
                        categoriaAEliminar = null
                    }
                ) {
                    Text("Eliminar", color = Color(0xFFF44336))
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarConfirmarEliminar = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}