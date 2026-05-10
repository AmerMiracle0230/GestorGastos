// archivo: MovimientoUI.kt
// que hace: modelo de datos para mostrar movimientos en la UI
// usado en: ListaScreen y TarjetaMovimiento

package com.example.gestorgastos.screens.lista.components

data class MovimientoUI(
    val id: Int,                // identificador unico del movimiento
    val tipo: String,           // "gasto" o "ingreso"
    val nombre: String,         // descripcion del movimiento
    val cantidad: Double,       // monto del movimiento
    val categoria: String,      // nombre de la categoria
    val categoriaIcono: String, // emoji de la categoria
    val fecha: Long,            // fecha en milisegundos
    val detalle: String         // detalle opcional
)