// archivo: FormularioAvanzado.kt
// que hace: formulario para modo avanzado (fecha y detalle)
// usado en: AddScreen (modo avanzado)

package com.example.gestorgastos.screens.add.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestorgastos.ui.theme.Success
import java.util.Date

@Composable
fun FormularioAvanzado(
    fecha: Long,
    onFechaClick: () -> Unit,
    detalle: String,
    onDetalleChange: (String) -> Unit,
    tipo: String,                    // "gasto" o "ingreso"
    dateFormat: java.text.SimpleDateFormat,
    modifier: Modifier = Modifier
) {
    // color del borde: rojo para gastos, verde para ingresos
    val borderColor = if (tipo == "gasto") {
        MaterialTheme.colorScheme.error
    } else {
        Success
    }

    Column(modifier = modifier) {
        // campo fecha (solo lectura)
        OutlinedTextField(
            value = dateFormat.format(Date(fecha)),
            onValueChange = {},
            readOnly = true,
            label = { Text("Fecha") },
            trailingIcon = {
                IconButton(onClick = onFechaClick) {
                    Text("📅", fontSize = 20.sp)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor.copy(alpha = 0.4f)
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // campo detalle (opcional, multilinea)
        OutlinedTextField(
            value = detalle,
            onValueChange = onDetalleChange,
            label = { Text("Detalle (opcional)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            maxLines = 3,
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor.copy(alpha = 0.4f)
            )
        )
    }
}