// archivo: BotonGuardar.kt
// que hace: boton para guardar un movimiento (gasto o ingreso)
// color: rojo para gastos, verde para ingresos

package com.example.gestorgastos.screens.add.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestorgastos.ui.theme.Success

@Composable
fun BotonGuardar(
    tipo: String,          // "gasto" o "ingreso"
    onClick: () -> Unit,   // que hacer cuando se presiona
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (tipo == "gasto") {
                MaterialTheme.colorScheme.error   // rojo para gastos
            } else {
                Success                           // verde para ingresos
            }
        )
    ) {
        Text(
            text = if (tipo == "gasto") "💸 GUARDAR GASTO" else "💰 GUARDAR INGRESO",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}