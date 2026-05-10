// archivo: DefinirMetaCard.kt
// que hace: tarjeta para definir una nueva meta de ahorro
// permite: ingresar un monto y guardarlo
// usado en: HomeScreen

package com.example.gestorgastos.screens.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DefinirMetaCard(
    maximoPermitido: Double,   // saldo disponible maximo (no se puede ahorrar mas de esto)
    onMetaGuardada: (Double) -> Unit,  // callback cuando se guarda la meta
    isDarkTheme: Boolean,      // tema oscuro o claro
    moneda: String             // simbolo de moneda (€, $, £)
) {
    // estado del input y mensaje de error
    var inputMeta by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    val primaryColor = MaterialTheme.colorScheme.primary

    // fondo semitransparente según tema
    val cardBackground = if (isDarkTheme) {
        Color.Black.copy(alpha = 0.3f)
    } else {
        Color.White.copy(alpha = 0.5f)
    }

    // color del placeholder segun tema
    val placeholderColor = if (isDarkTheme) Color.White.copy(alpha = 0.5f) else Color.DarkGray

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = primaryColor.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp)
                ),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = cardBackground),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // texto a la izquierda
                Text(
                    text = "✏️ Nueva meta",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = primaryColor
                )

                // input + boton de guardar
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // campo de cantidad
                    OutlinedTextField(
                        value = inputMeta,
                        onValueChange = {
                            if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d{0,2}?$"))) {
                                inputMeta = it
                                mensajeError = ""
                            }
                        },
                        modifier = Modifier.width(100.dp),
                        placeholder = {
                            Text(
                                text = "0.00$moneda",
                                fontSize = 13.sp,
                                color = placeholderColor,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        isError = mensajeError.isNotEmpty(),
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.End
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = primaryColor.copy(alpha = 0.3f),
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            errorBorderColor = MaterialTheme.colorScheme.error
                        )
                    )

                    // boton de guardar (icono check)
                    IconButton(
                        onClick = {
                            val nuevaMeta = inputMeta.toDoubleOrNull()
                            when {
                                nuevaMeta == null || nuevaMeta <= 0 -> {
                                    mensajeError = "Cantidad invalida"
                                }
                                nuevaMeta > maximoPermitido -> {
                                    mensajeError = "Maximo ${maximoPermitido.toInt()}$moneda"
                                }
                                else -> {
                                    onMetaGuardada(nuevaMeta)
                                    inputMeta = ""
                                    mensajeError = ""
                                }
                            }
                        },
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                color = if (inputMeta.isNotEmpty()) primaryColor else primaryColor.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        enabled = inputMeta.isNotEmpty()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Guardar meta",
                            tint = if (inputMeta.isNotEmpty()) primaryColor else primaryColor.copy(alpha = 0.3f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // mensaje de error (si hay)
            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 8.dp)
                )
            }
        }
    }
}