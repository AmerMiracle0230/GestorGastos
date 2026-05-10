// archivo: DatePickerDialog.kt
// que hace: diálogo para seleccionar fecha (año, mes, día)
// usado en: AddScreen

package com.example.gestorgastos.screens.add.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.Calendar

@Composable
fun DatePickerDialog(
    onDismiss: () -> Unit,
    onDateSelected: (Long) -> Unit,
    calendar: Calendar,
    yearRange: IntRange
) {
    // usar mutableIntStateOf en lugar de mutableStateOf para Int
    var anioSeleccionado by remember { mutableIntStateOf(calendar.get(Calendar.YEAR)) }
    var mesSeleccionado by remember { mutableIntStateOf(calendar.get(Calendar.MONTH)) }
    var diaSeleccionado by remember { mutableIntStateOf(calendar.get(Calendar.DAY_OF_MONTH)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Seleccionar fecha") },
        text = {
            Column {
                // año
                Text("Año:", fontWeight = FontWeight.Bold)
                LazyRow {
                    val anios = yearRange.toList()
                    items(anios.size) { index ->
                        val anio = anios[index]
                        FilterChip(
                            selected = anioSeleccionado == anio,
                            onClick = { anioSeleccionado = anio },
                            label = { Text(anio.toString()) },
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // mes
                Text("Mes:", fontWeight = FontWeight.Bold)
                val meses = listOf("Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic")
                LazyRow {
                    items(meses.size) { index ->
                        FilterChip(
                            selected = mesSeleccionado == index,
                            onClick = { mesSeleccionado = index },
                            label = { Text(meses[index]) },
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // dia
                Text("Día:", fontWeight = FontWeight.Bold)
                val maxDias = getMaxDias(mesSeleccionado, anioSeleccionado)
                var dias = maxDias
                if (diaSeleccionado > maxDias) {
                    diaSeleccionado = maxDias
                    dias = maxDias
                }

                LazyRow {
                    items(dias) { index ->
                        val dia = index + 1
                        FilterChip(
                            selected = diaSeleccionado == dia,
                            onClick = { diaSeleccionado = dia },
                            label = { Text(dia.toString()) },
                            modifier = Modifier.padding(2.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val cal = Calendar.getInstance().apply {
                        set(Calendar.YEAR, anioSeleccionado)
                        set(Calendar.MONTH, mesSeleccionado)
                        set(Calendar.DAY_OF_MONTH, diaSeleccionado)
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                    onDateSelected(cal.timeInMillis)
                    onDismiss()
                }
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

// maximo de días según el mes y año
private fun getMaxDias(mes: Int, anio: Int): Int {
    return when (mes) {
        1 -> if (esBisiesto(anio)) 29 else 28  // febrero
        3, 5, 8, 10 -> 30  // abril, junio, septiembre, noviembre
        else -> 31
    }
}

// verifica si el año es bisiesto
private fun esBisiesto(anio: Int): Boolean {
    return anio % 4 == 0 && (anio % 100 != 0 || anio % 400 == 0)
}