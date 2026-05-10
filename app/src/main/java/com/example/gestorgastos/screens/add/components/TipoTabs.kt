// archivo: TipoTabs.kt
// que hace: pestañas para cambiar entre gasto e ingreso
// usado en: AddScreen

package com.example.gestorgastos.screens.add.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestorgastos.ui.theme.Success

@Composable
fun TipoTabs(
    tipo: String,
    onTipoChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TabRow(
        selectedTabIndex = if (tipo == "gasto") 0 else 1,
        containerColor = Color.Transparent,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[if (tipo == "gasto") 0 else 1]),
                color = if (tipo == "gasto") MaterialTheme.colorScheme.error else Success
            )
        },
        modifier = modifier  // usar el modifier aunque solo sea para padding exterior si se necesita
    ) {
        Tab(
            selected = tipo == "gasto",
            onClick = { onTipoChange("gasto") },
            text = { Text("💸 GASTO", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
            selectedContentColor = MaterialTheme.colorScheme.error,
            unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Tab(
            selected = tipo == "ingreso",
            onClick = { onTipoChange("ingreso") },
            text = { Text("💰 INGRESO", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
            selectedContentColor = Success,
            unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
}