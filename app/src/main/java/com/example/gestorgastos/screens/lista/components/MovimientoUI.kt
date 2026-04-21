package com.example.gestorgastos.screens.lista.components

data class MovimientoUI(
    val id: Int,
    val tipo: String,
    val nombre: String,
    val cantidad: Double,
    val categoria: String,
    val categoriaIcono: String,
    val fecha: Long,
    val detalle: String
)