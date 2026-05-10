// archivo: AddHeader.kt
// que hace: cabecera de la pantalla Add con título y switch para modo avanzado

package com.example.gestorgastos.screens.add.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestorgastos.components.TituloGradienteConFlecha

@Composable
fun AddHeader(
    modoAvanzado: Boolean,
    onModoAvanzadoChange: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        TituloGradienteConFlecha(
            texto = "AÑADIR",
            onBackClick = onBackClick
        )

        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (modoAvanzado) "🔧" else "⚡",
                fontSize = 16.sp
            )
            Switch(
                checked = modoAvanzado,
                onCheckedChange = onModoAvanzadoChange,
                modifier = Modifier.scale(0.7f),
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}