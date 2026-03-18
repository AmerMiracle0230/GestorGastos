package com.example.gestorgastos.screens.add


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gestorgastos.components.TituloSecundario

@Composable
fun AddScreen(
    viewModel: AddViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    var descripcion by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("Comida") }
    var errorMsg by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TituloSecundario(
            texto = "AÑADIR GASTO",
            onBackClick = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Cantidad (€)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                listOf("Comida", "Transporte", "Ocio").forEach { cat ->
                    FilterChip(
                        selected = categoria == cat,
                        onClick = { categoria = cat },
                        label = { Text(cat) },
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }

            if (errorMsg.isNotEmpty()) {
                Text(
                    text = errorMsg,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (descripcion.isBlank()) {
                        errorMsg = "La descripción no puede estar vacía"
                        return@Button
                    }
                    val cantidadNum = cantidad.toDoubleOrNull()
                    if (cantidadNum == null || cantidadNum <= 0) {
                        errorMsg = "Cantidad inválida"
                        return@Button
                    }

                    viewModel.guardarGasto(
                        descripcion = descripcion,
                        cantidad = cantidadNum,
                        categoria = categoria,
                        onSuccess = {
                            onBack()
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("GUARDAR")
            }
        }
    }
}