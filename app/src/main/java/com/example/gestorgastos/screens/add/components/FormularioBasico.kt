// archivo: FormularioBasico.kt
// que hace: formulario para añadir movimientos (nombre, cantidad, categoria)
// usado en: AddScreen

package com.example.gestorgastos.screens.add.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.gestorgastos.data.entity.Categoria

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioBasico(
    nombre: String,
    onNombreChange: (String) -> Unit,
    cantidad: String,
    onCantidadChange: (String) -> Unit,
    categorias: List<Categoria>,
    categoriaId: Int?,
    onCategoriaChange: (Int) -> Unit,
    tipo: String,                    // "gasto" o "ingreso"
    isDarkTheme: Boolean,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    moneda: String,                  // simbolo de moneda (€, $, £)
    modifier: Modifier = Modifier
) {
    // color del borde según el tipo
    val borderColor = if (tipo == "gasto") {
        MaterialTheme.colorScheme.error   // rojo para gastos
    } else {
        MaterialTheme.colorScheme.primary // azul para ingresos
    }

    Column(modifier = modifier) {
        // campo nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = onNombreChange,
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Ej: Café, Sueldo...") },
            singleLine = true,
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor.copy(alpha = 0.4f)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // campo cantidad con moneda dinamica
        OutlinedTextField(
            value = cantidad,
            onValueChange = onCantidadChange,
            label = { Text("Cantidad ($moneda)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor.copy(alpha = 0.4f)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // campo categoria (dropdown)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = onExpandedChange,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = categorias.find { it.id == categoriaId }?.let { "${it.icono} ${it.nombre}" }
                    ?: "Seleccionar categoría",
                onValueChange = {},
                readOnly = true,
                label = { Text("Categoría") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = borderColor.copy(alpha = 0.4f)
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) },
                modifier = Modifier.background(if (isDarkTheme) Color(0xFF1E1E2F) else Color.White)
            ) {
                if (categorias.isEmpty()) {
                    DropdownMenuItem(
                        text = { Text("Cargando categorías...") },
                        onClick = { }
                    )
                } else {
                    categorias.forEach { categoria ->
                        DropdownMenuItem(
                            text = { Text("${categoria.icono} ${categoria.nombre}") },
                            onClick = {
                                onCategoriaChange(categoria.id)
                                onExpandedChange(false)
                            }
                        )
                    }
                }
            }
        }
    }
}